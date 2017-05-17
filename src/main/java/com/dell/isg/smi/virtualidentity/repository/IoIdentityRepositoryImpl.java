/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.repository;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dell.isg.smi.virtualidentity.entity.IoIdentity;
import com.dell.isg.smi.virtualidentity.model.IoIdentityStates;

/**
 * The Class IoIdentityRepositoryImpl.
 */
public class IoIdentityRepositoryImpl implements IoIdentityRepositoryCustom {

    @Autowired
    private IoIdentityRepository ioIdentityRepository;

    private static final Logger logger = LoggerFactory.getLogger(IoIdentityRepositoryImpl.class.getName());


    /* (non-Javadoc)
     * @see com.dell.isg.smi.virtualidentity.repository.IoIdentityRepositoryCustom#assignIdentities(java.util.List, java.lang.String)
     */
    @Override
    @Transactional
    public void assignIdentities(List<String> identities, String usageGuid) {
        logger.trace("Entering in method IoIdentityDAO-assignIdentities");

        for (String identityString : identities) {
            IoIdentity identity = ioIdentityRepository.findByValue(identityString);
            if (null != identity) {
                identity.setUsageGuid(usageGuid);
                identity.setState("ASSIGNED");
                identity.setExpiryDate(null);
                ioIdentityRepository.save(identity);
            } else {
                throw new PersistenceException(String.format("Identity with id %s not found", identityString));
            }
        }
        logger.trace("Exiting method IoIdentityDAO-assignIdentities");
    }


    /* (non-Javadoc)
     * @see com.dell.isg.smi.virtualidentity.repository.IoIdentityRepositoryCustom#reserveIdentities(java.lang.String, java.lang.String, int, java.util.Date, long)
     */
    @Override
    @Transactional
    public List<IoIdentity> reserveIdentities(String type, String usageGuid, int numberOfReservations, Date expiryDate, long poolId) {
        logger.trace("Entering method IoIdentityDAO-reserveIdentities");
        List<IoIdentity> identityList = ioIdentityRepository.findByTypeAndStateAndPoolIdOrderByValueAsc(type, IoIdentityStates.AVAILABLE.value(), poolId, new PageRequest(0, numberOfReservations));
        for (IoIdentity ioIdentity : identityList) {
            ioIdentity.setState(IoIdentityStates.RESERVED.value());
            ioIdentity.setExpiryDate(expiryDate);
            ioIdentity.setUsageGuid(usageGuid);
            ioIdentityRepository.save(ioIdentity);
        }
        logger.trace("Exiting method IoIdentityDAO-reserveIdentities");
        return identityList;
    }


    /* (non-Javadoc)
     * @see com.dell.isg.smi.virtualidentity.repository.IoIdentityRepositoryCustom#saveOrUpdateIoIdentities(java.util.List, java.util.List)
     */
    @Override
    public List<IoIdentity> saveOrUpdateIoIdentities(List<IoIdentity> identityList, List<IoIdentity> updateIdentityList) {

        List<IoIdentity> ioIdentityList = new LinkedList<>();
        ioIdentityRepository.releaseExpired();
        if (!CollectionUtils.isEmpty(updateIdentityList)) {
            ioIdentityRepository.save(updateIdentityList);
            ioIdentityList.addAll(updateIdentityList);
        }
        if (!CollectionUtils.isEmpty(identityList)) {
            ioIdentityRepository.save(identityList);
            ioIdentityList.addAll(identityList);
        }
        return ioIdentityList;
    }
}
