/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.dell.isg.smi.commons.elm.exception.RuntimeCoreException;
import com.dell.isg.smi.commons.utilities.model.PagedResult;
import com.dell.isg.smi.commons.utilities.PaginationUtils;
import com.dell.isg.smi.virtualidentity.configuration.IdentityPoolMgrConstants;
import com.dell.isg.smi.virtualidentity.entity.IoIdentity;
import com.dell.isg.smi.virtualidentity.entity.IoPool;
import com.dell.isg.smi.virtualidentity.entity.IoPoolType;
import com.dell.isg.smi.virtualidentity.exception.BadRequestException;
import com.dell.isg.smi.virtualidentity.exception.EnumErrorCode;
import com.dell.isg.smi.virtualidentity.model.AssignIdentities;
import com.dell.isg.smi.virtualidentity.model.IoIdentityStates;
import com.dell.isg.smi.virtualidentity.model.IoIdentityType;
import com.dell.isg.smi.virtualidentity.model.ReserveIdentities;
import com.dell.isg.smi.virtualidentity.model.VirtualIdentityResponse;
import com.dell.isg.smi.virtualidentity.repository.IoIdentityRepository;
import com.dell.isg.smi.virtualidentity.repository.IoPoolRepository;
import com.dell.isg.smi.virtualidentity.repository.IoPoolTypeRepository;

/**
 * Implementation of the IoIdentityManager, the primary business API for working with virtual identities.
 */
@Component
public class IoIdentityManagerImpl implements IoIdentityManager {

    private static final Logger logger = LoggerFactory.getLogger(IoIdentityManagerImpl.class);
    private static final long GLOBAL_POOL_ID = IdentityPoolMgrConstants.GLOBAL_POOL_ID;
    private static final String DEFAULT_IQN_PREFIX = "iqn.dell:software-didc-01-0000000000";
    private static final String DELL_OUI = "00:0E:AA";
    private static final int MAX_COUNT_MAC = 255 * 255;
    private static final int MAX_COUNT_IQN = 255 * 255 * 255;

    static final int OFFSET = 0;
    static final int LIMIT = 0;
    static final String SORT_FIELD = null;
    static final boolean IS_ASCENDING = true;
    static final int maxIqnPrefixLength = 101;
    static final int maxEuiSuffixLength = 16;
    static final int maxStartingNumber = 200000000;
    static final long GLOBAL_IDENTITY_POOL_SIZE = 0;

    @Autowired
    private IoIdentityRepository ioIdentityRepository;

    @Autowired
    private IoPoolRepository ioPoolRepository;

    @Autowired
    private IoPoolTypeRepository ioPoolTypeRepository;

    @Autowired
    private IoPoolDtoAssembler ioPoolDtoAssembler;

    static final String IQN = "IQN";


    /**
     * Called during IoIdentityManager initialization
     */
    @Transactional
    @Override
    public void initailizePools() {
        // create PoolTypes(MAC,IQN,WWPN,WWNN) In Global Pool
        logger.trace(" init entered");
        createPoolTypesInGlobalPool();
        logger.trace(" init exited");
    }


    /**
     * create PoolTypes(MAC,IQN,WWPN,WWNN) in Global Pool
     */
    private void createPoolTypesInGlobalPool() {

        logger.trace(" createPoolTypesInGlobalPool entered ");

        IoPool pool = ioPoolRepository.findOne(GLOBAL_POOL_ID);
        if (pool == null) {
            pool = new IoPool();
            pool.setId(GLOBAL_POOL_ID);
            pool.setDescription("Default Pool");
            ioPoolRepository.save(pool);

            // This request is to create or expand global Pool
            String globalPrefix = IoIdentityManagerUtility.randomizePrefixValues();

            logger.debug("Global Pool Prefix for poolTypes(MAC,WWPN,WWNN) {}  ", globalPrefix);

            // Set the default poolType for MAC
            IoPoolType macPoolType = new IoPoolType();
            macPoolType.setPrefix(globalPrefix);
            macPoolType.setOui(DELL_OUI);
            macPoolType.setAutoGenerate(true);
            macPoolType.setType(IoIdentityType.MAC.value());
            macPoolType.setIoPool(pool);
            macPoolType.setPoolSize(MAX_COUNT_MAC);
            ioPoolTypeRepository.save(macPoolType);

            // Set the default poolType for IQN
            IoPoolType iqnPoolType = new IoPoolType();
            iqnPoolType.setPrefix(DEFAULT_IQN_PREFIX);
            iqnPoolType.setOui(DELL_OUI);
            iqnPoolType.setAutoGenerate(true);
            iqnPoolType.setType(IoIdentityType.IQN.value());
            iqnPoolType.setIoPool(pool);
            iqnPoolType.setPoolSize(MAX_COUNT_IQN);
            ioPoolTypeRepository.save(iqnPoolType);

            // Set the default poolType WWNN
            IoPoolType wwnnPoolType = new IoPoolType();
            wwnnPoolType.setPrefix(globalPrefix);
            wwnnPoolType.setOui(DELL_OUI);
            wwnnPoolType.setAutoGenerate(true);
            wwnnPoolType.setType(IoIdentityType.WWNN.value());
            wwnnPoolType.setIoPool(pool);
            wwnnPoolType.setPoolSize(MAX_COUNT_MAC);
            ioPoolTypeRepository.save(wwnnPoolType);

            // Set the default poolType WWPN
            IoPoolType wwpnPoolType = new IoPoolType();
            wwpnPoolType.setPrefix(globalPrefix);
            wwpnPoolType.setOui(DELL_OUI);
            wwpnPoolType.setAutoGenerate(true);
            wwpnPoolType.setType(IoIdentityType.WWPN.value());
            wwpnPoolType.setIoPool(pool);
            wwpnPoolType.setPoolSize(MAX_COUNT_MAC);
            ioPoolTypeRepository.save(wwpnPoolType);

        }
    }


    @Transactional
    @Override
    public List<String> reserveIdentities(ReserveIdentities reserveIdentities) {

        // Expire Time is fixed for a Month
        int expiryTimeUnitsInCalendar = Calendar.MONTH;
        int expiryTimeValue = 1;
        long poolId = GLOBAL_POOL_ID;
        String identityType = reserveIdentities.getIdentityType();
        IoIdentityType ioIdentityType = IoIdentityType.valueOf(identityType);
        String usageGuid = reserveIdentities.getUsageId();
        int numberOfReservations = reserveIdentities.getQuantityRequested();
        if (numberOfReservations <= 0) {
            BadRequestException badRequestException = new BadRequestException();
            badRequestException.setErrorCode(EnumErrorCode.IOIDENTITY_INVALID_CREATE_REQUEST);
            throw badRequestException;
        }
        List<IoIdentity> ioIdentities = null;
        logger.info("Getting into IoIdentityManager-reserveIdentities.");
        try {
            ioIdentities = reserveIdentitiesTx(ioIdentityType, usageGuid, numberOfReservations, expiryTimeUnitsInCalendar, expiryTimeValue, poolId);
            ioPoolRepository.updatePoolReservation();
        } catch (RuntimeCoreException rce) {
            logger.error("Exception occurred in reserveIdentities", rce);
            throw rce;
        }

        List<String> ioIdentityValues = new ArrayList<>();
        for (IoIdentity ioIdentity : ioIdentities) {
            String ioIdentityValue = ioIdentity.getValue();
            ioIdentityValues.add(ioIdentityValue);
        }

        return ioIdentityValues;

    }


    /**
     * @param ioIdentityType
     * @param StringUtils.isEmpty(usageGuid
     * @param numberOfReservations
     * @param expiryTimeUnitsInCalendar
     * @param expiryTimeValue
     * @param poolId
     * @return IOIdentityList
     */
    private synchronized List<IoIdentity> reserveIdentitiesTx(IoIdentityType ioIdentityType, String usageGuid, int numberOfReservations, int expiryTimeUnitsInCalendar, int expiryTimeValue, long poolId) {
        logger.info("Getting into IoIdentityManager-reserveIdentitiesTx.");

        List<IoIdentity> reservedIdentityList = null;
        List<IoIdentity> insertIdentityList = null;
        List<IoIdentity> updateIdentityList = null;
        try {
            // if poolId is null then default to Global pool
            long tempPoolId = (0 == poolId) ? GLOBAL_POOL_ID : poolId;
            // check whether the pool already exist. If not, throw exception
            IoPool ioPool = ioPoolRepository.findOne(tempPoolId);
            if (ioPool == null) {
                logger.error("IOPool is not valid for IO Identity reserve request");
                BadRequestException badRequestException = new BadRequestException();
                badRequestException.setErrorCode(EnumErrorCode.IOIDENTITY_INVALID_CREATE_REQUEST);
                throw badRequestException;
            }

            IoPoolType poolType = ioPoolTypeRepository.findByIoPoolIdAndType(tempPoolId, ioIdentityType.value());
            if (poolType == null) {
                logger.error("IOPoolType does not exist for IO Identity reserve request");
                BadRequestException badRequestException = new BadRequestException();
                badRequestException.setErrorCode(EnumErrorCode.IOIDENTITY_INVALID_CREATE_REQUEST);
                throw badRequestException;
            }
            if (StringUtils.isEmpty(usageGuid)) {
                logger.error(" usageId is not valid for reserve request");
                BadRequestException badRequestException = new BadRequestException();
                badRequestException.setErrorCode(EnumErrorCode.IOIDENTITY_INVALID_INPUT);
                throw badRequestException;
            }
            ioIdentityRepository.releaseExpired();
            // Get all IO Identities of type specified, which are in AVAILABLE state and belong to the given pool
            PageRequest pageRequest = new PageRequest(0, numberOfReservations);
            updateIdentityList = ioIdentityRepository.findByTypeAndStateAndPoolIdOrderByValueAsc(ioIdentityType.value(), IoIdentityStates.AVAILABLE.value(), tempPoolId, pageRequest);

            int iCountOfAvailableIOIdentities = 0;
            if (null != updateIdentityList) {
                iCountOfAvailableIOIdentities = updateIdentityList.size();
            }

            if (!poolType.isAutoGenerate() && iCountOfAvailableIOIdentities < numberOfReservations) {
                // throw exception saying that IO Pool is not expandable
                logger.error("Number Of Reservations exceeded number of available identities for named pool: " + ioPool.getName());
                throw new RuntimeCoreException(EnumErrorCode.IOIDENTITY_RESERVATIONS_EXCEEDED);
            }
            // If number of available IO Identities are less than the number requested, then generate more IO Identities
            int iNumberOfIOIdentitiesToBeGenerated = numberOfReservations - iCountOfAvailableIOIdentities;
            if (iNumberOfIOIdentitiesToBeGenerated > 0) {
                long iMaxCount = poolType.getPoolSize();
                long iActualCount = ioIdentityRepository.findPoolSizeByType(ioIdentityType.value());
                // Check to see if IOPoolType size is not exceeded
                if (iActualCount + iNumberOfIOIdentitiesToBeGenerated > iMaxCount) {
                    // throw exception saying that IO Pool size exceeded
                    logger.error(String.format("Size of the IO Pool of type %s has exceeded", poolType.getType().toUpperCase()));
                    throw new RuntimeCoreException(EnumErrorCode.IOPOOL_MAX_SIZE_EXCEEDED);
                }

                IoIdentity lastIOIdentity = null;
                List<IoIdentity> ioIdentityList = ioIdentityRepository.findByPoolIdAndTypeOrderByValueDesc(tempPoolId, ioIdentityType.value());
                if (CollectionUtils.isEmpty(ioIdentityList)) {
                    logger.warn("collection did not return any results, expected at least one.");
                } else {
                    lastIOIdentity = ioIdentityList.get(0);
                }

                if (ioIdentityType == IoIdentityType.MAC) {
                    insertIdentityList = IoIdentityManagerUtility.generateMAC(iNumberOfIOIdentitiesToBeGenerated, poolType, lastIOIdentity);
                } else if (ioIdentityType == IoIdentityType.IQN) {
                    boolean isEUI = isEUI(poolType.getPrefix());
                    if (isEUI) {
                        insertIdentityList = IoIdentityManagerUtility.generateEUIByCustomizedInput(iNumberOfIOIdentitiesToBeGenerated, 0, poolType, lastIOIdentity);
                    } else {
                        // Non Eui Case
                        // Generate IQNs for the other cases: "iqn." or other prefix
                        logger.debug("In createIOIdentity, generate IQNs for the prefix is not EUI like identity");
                        insertIdentityList = IoIdentityManagerUtility.generateIQN(iNumberOfIOIdentitiesToBeGenerated, 1, poolType, lastIOIdentity, false);
                    }
                } else if (ioIdentityType == IoIdentityType.WWNN) {
                    insertIdentityList = IoIdentityManagerUtility.generateWWNN(iNumberOfIOIdentitiesToBeGenerated, poolType, lastIOIdentity);
                } else if (ioIdentityType == IoIdentityType.WWPN) {
                    insertIdentityList = IoIdentityManagerUtility.generateWWPN(iNumberOfIOIdentitiesToBeGenerated, poolType, lastIOIdentity);
                } else {
                    RuntimeCoreException runtimeCoreException = new RuntimeCoreException(EnumErrorCode.IOIDENTITY_INVALID_TYPE_IN_POOL);
                    runtimeCoreException.addAttribute(poolType.getType());
                    runtimeCoreException.addAttribute(String.valueOf(poolType.getIoPool().getId()));
                    throw runtimeCoreException;
                }
            }
            reservedIdentityList = saveOrUpdateReservedIdentities(insertIdentityList, updateIdentityList, poolType, usageGuid, expiryTimeUnitsInCalendar, expiryTimeValue);
        } catch (RuntimeCoreException rce) {
            logger.error("AsmException occured in reserveIdentitiesTx", rce);
            throw rce;
        } catch (Exception e) {
            logger.debug("Exception occured in reserveIdentitiesTx", e);
            RuntimeCoreException runtimeCoreException = new RuntimeCoreException(e);
            runtimeCoreException.setErrorCode(EnumErrorCode.IOIDENTITY_FAILED_TO_RESERVE_IDENTITIES);
            throw runtimeCoreException;
        }
        return reservedIdentityList;
    }


    /**
     * @param usageGuid
     * @param identityList
     * @param updateIdentityList
     * @param ioPoolType
     * @param expiryTimeUnitsInCalendar
     * @param expiryTimeValue
     * @return IOIdentityList
     */
    private synchronized List<IoIdentity> saveOrUpdateReservedIdentities(List<IoIdentity> identityList, List<IoIdentity> updateIdentityList, IoPoolType ioPoolType, String usageGuid, int expiryTimeUnitsInCalendar, int expiryTimeValue) {
        Calendar utcCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        utcCal.add(expiryTimeUnitsInCalendar, expiryTimeValue);

        if (!CollectionUtils.isEmpty(identityList)) {
            for (IoIdentity ioIdentity : identityList) {
                ioIdentity.setIoPool(ioPoolType.getIoPool());
                ioIdentity.setUsageGuid(usageGuid);
                ioIdentity.setState(IoIdentityStates.RESERVED.value());
                ioIdentity.setExpiryDate(utcCal.getTime());
            }
        }
        if (!CollectionUtils.isEmpty(updateIdentityList)) {
            for (IoIdentity ioIdentity : updateIdentityList) {
                ioIdentity.setUsageGuid(usageGuid);
                ioIdentity.setState(IoIdentityStates.RESERVED.value());
                ioIdentity.setExpiryDate(utcCal.getTime());
            }
        }
        return ioIdentityRepository.saveOrUpdateIoIdentities(identityList, updateIdentityList);
    }


    /**
     * @param prefix
     * @return true if valid EUI prefix else false
     */
    private boolean isEUI(String prefix) {
        boolean isEui = false;
        if (prefix.toLowerCase().startsWith("eui.")) {
            String[] euiStrings = prefix.split("\\.");
            // Check if it's an valid EUI string and has a valid hexadecimal prefix
            if (euiStrings.length == 2 && IoIdentityManagerUtility.isHex(euiStrings[1]) && euiStrings[1].length() <= maxEuiSuffixLength) {
                isEui = true;
            } else {
                // Generate EUI failed if prefix starts with "eui." but not a qualified EUI identity
                logger.error(String.format("Failed to create EUIs, because the prefix is not a qualified EUI identity: %s", prefix));
                RuntimeCoreException runtimeCoreException = new RuntimeCoreException(EnumErrorCode.IOIDENTITY_GENERATION_INVALID_EUI);
                runtimeCoreException.addAttribute(prefix);
                throw runtimeCoreException;
            }
        }
        return isEui;
    }


    /*
     * (non-Javadoc)
     *
     * @see com.dell.esg.icee.identitypoolmgr.ioidentity.IIOIdentityMgr#assignIdentities(java.util.List, java.lang.String)
     */
    @Transactional
    @Override
    public synchronized void assignIdentities(AssignIdentities assignIdentities) {
        logger.trace(" Entering assignIdentities");
        List<String> identities = assignIdentities.getVirtualIdentities();
        String usageGuid = assignIdentities.getUsageId();

        List<IoIdentity> ioIdentityList = ioIdentityRepository.findByValueIn(identities);
        for (IoIdentity ioIdentity : ioIdentityList) {
            if (!StringUtils.isEmpty(ioIdentity.getUsageGuid()) && !usageGuid.equals(ioIdentity.getUsageGuid())) {
                throw new RuntimeCoreException(EnumErrorCode.IOIDENTITY_INVALID_INPUT);
            }
        }
        try {
            ioIdentityRepository.assignIdentities(identities, usageGuid);
            ioPoolRepository.updatePoolReservation();
        } catch (Exception e) {
            logger.error("Exception occured in assignIdentities {}", e);
            RuntimeCoreException runtimeCoreException = new RuntimeCoreException(e);
            runtimeCoreException.setErrorCode(EnumErrorCode.IOIDENTITY_FAILED_TO_ASSIGN_IDENTITIES);
            throw runtimeCoreException;
        }
        logger.trace(" Exiting assignIdentities");
    }


    /*
     * (non-Javadoc)
     *
     * @see com.dell.esg.icee.identitypoolmgr.ioidentity.IIOIdentityMgr#releaseIdentitiesByAssociatedId(java.lang.String)
     */
    @Transactional
    @Override
    public synchronized void releaseIdentitiesByUsageGuid(String usageGuid) {
        logger.trace("Entering releaseIdentitiesByUsageId {}", usageGuid);
        long count = ioIdentityRepository.countByUsageGuid(usageGuid);
        if (count == 0) {
            RuntimeCoreException runtimeCoreException = new RuntimeCoreException(EnumErrorCode.IOIDENTITY_INVALID_USAGE_ID);
            runtimeCoreException.addAttribute(usageGuid);
            throw runtimeCoreException;
        }
        try {
            ioIdentityRepository.releaseByUsageGuid(usageGuid);
            ioPoolRepository.updatePoolReservation();
        } catch (Exception e) {
            logger.error("Exception occured in releaseIdentitiesByAssociatedId {}", e);
            RuntimeCoreException runtimeCoreException = new RuntimeCoreException(EnumErrorCode.IOIDENTITY_INVALID_USAGE_ID);
            runtimeCoreException.addAttribute(usageGuid);
            throw runtimeCoreException;
        }
    }


    /*
     * (non-Javadoc)
     *
     * @see com.dell.esg.icee.identitypoolmgr.ioidentity.IIOIdentityMgr#findIdentityById(java.lang.String)
     */
    @Override
    public IoIdentity findIdentityById(long identityId) {
        logger.trace("Entering in method findIdentityById() in Manager");
        IoIdentity ioIdentity = null;
        try {
            ioIdentity = ioIdentityRepository.findById(identityId);
        } catch (Exception e) {
            logger.error("Exception occured in findIdentityById {} ", e);
            RuntimeCoreException runtimeCoreException = new RuntimeCoreException(EnumErrorCode.ENUM_NOT_FOUND_ERROR);
            runtimeCoreException.addAttribute(String.valueOf(identityId));
            throw runtimeCoreException;
        }
        logger.trace("Exiting from method findIdentityById() in Manager");
        if (ioIdentity == null) {
            RuntimeCoreException runtimeCoreException = new RuntimeCoreException(EnumErrorCode.IOIDENTITY_INVALID_IDENTITY_ID);
            runtimeCoreException.addAttribute(String.valueOf(identityId));
            throw runtimeCoreException;
        }
        return ioIdentity;
    }


    @Override
    public VirtualIdentityResponse getVirtualIdentityById(long identityId) {
        logger.trace("Entering getVirtualIdentityById");
        IoIdentity ioIdentity = findIdentityById(identityId);
        VirtualIdentityResponse virtualIdentityResponse = ioPoolDtoAssembler.transform(ioIdentity);
        logger.trace("Exiting getVirtualIdentityById");
        return virtualIdentityResponse;

    }


    @Override
    public PagedResult getAllVirtualIdentitiesByFilter(String type, String usageGuid, int offset, int limit) {
        logger.trace(" Entering getAllVirtualIdentitiesByFilter type {} usageId {} , offset {} limit {}", type, usageGuid, offset, limit);
        Page<IoIdentity> ioIdentityPage = null;

        int page = offset / limit;
        PageRequest pageRequest = new PageRequest(page, limit);

        try {
            if (!StringUtils.isEmpty(type)) {
                if (!StringUtils.isEmpty(usageGuid)) {
                    // filtering by usageGuid and type
                    ioIdentityPage = ioIdentityRepository.findByUsageGuidAndTypeOrderByValueAsc(usageGuid, type, pageRequest);
                } else {
                    // filtering by type only
                    ioIdentityPage = ioIdentityRepository.findByIoPoolTypeTypeOrderByValueAsc(type, pageRequest);
                }
            } else {
                if (!StringUtils.isEmpty(usageGuid)) {
                    // filtering by usageGild
                    ioIdentityPage = ioIdentityRepository.findByUsageGuidOrderByValueAsc(usageGuid, pageRequest);
                } else {
                    // no filter
                    ioIdentityPage = ioIdentityRepository.findAll(pageRequest);
                }
            }
        } catch (Exception e) {
            RuntimeCoreException runtimeCoreException = new RuntimeCoreException(e);
            runtimeCoreException.setErrorCode(EnumErrorCode.ENUM_SERVER_ERROR);
            throw runtimeCoreException;
        }

        if (null == (ioIdentityPage)) {
            RuntimeCoreException runtimeCoreException = new RuntimeCoreException(EnumErrorCode.ENUM_SERVER_ERROR);
            logger.error("Paged object is null", runtimeCoreException);
            throw runtimeCoreException;
        }

        List<VirtualIdentityResponse> virtualIdentityResponseList = new ArrayList<>();
        List<IoIdentity> ioIdentityList = ioIdentityPage.getContent();
        for (IoIdentity ioIdentity : ioIdentityList) {
            if (null != ioIdentity) {
                VirtualIdentityResponse virtualIdentityResponse = ioPoolDtoAssembler.transform(ioIdentity);
                virtualIdentityResponseList.add(virtualIdentityResponse);
            }
        }

        // Populate Pagination
        int total = virtualIdentityResponseList.size();
        final PagedResult pagedResult = PaginationUtils.paginate(virtualIdentityResponseList, total, offset, limit);
        logger.trace(" Exiting getAllVirtualIdentitiesByFilter type {} usageId {} , offset {} limit {}", type, usageGuid, offset, limit);
        return pagedResult;
    }


    @Override
    @Transactional
    public void releaseIdentitiesByVirtualIdentityId(long virtualIdentityId) {
        logger.trace("Entering releaseIdentitiesByVirtualIdentityId {}", virtualIdentityId);
        try {
            // will throw custom exception if Identity is not valid Id
            findIdentityById(virtualIdentityId);
            ioIdentityRepository.releaseById(virtualIdentityId);
            ioPoolRepository.updatePoolReservation();
        } catch (Exception e) {
            logger.error("Exception occured in releaseIdentitiesByVirtualIdentityId {}", e);
            RuntimeCoreException runtimeCoreException = new RuntimeCoreException(EnumErrorCode.IOIDENTITY_INVALID_IDENTITY_ID);
            runtimeCoreException.addAttribute(String.valueOf(virtualIdentityId));
            throw runtimeCoreException;
        }
        logger.trace("Exiting releaseIdentitiesByVirtualIdentityId {}", virtualIdentityId);
    }

}
