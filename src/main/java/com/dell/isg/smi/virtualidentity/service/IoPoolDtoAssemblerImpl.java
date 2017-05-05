/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Component;

import com.dell.isg.smi.commons.elm.utilities.DateTimeUtils;
import com.dell.isg.smi.virtualidentity.entity.IoIdentity;
import com.dell.isg.smi.virtualidentity.entity.IoPool;
import com.dell.isg.smi.virtualidentity.entity.IoPoolType;
import com.dell.isg.smi.virtualidentity.model.IoIdentityBrief;
import com.dell.isg.smi.virtualidentity.model.IoIdentityStates;
import com.dell.isg.smi.virtualidentity.model.IoIdentityType;
import com.dell.isg.smi.virtualidentity.model.Pool;
import com.dell.isg.smi.virtualidentity.model.VirtualIdentityPool;
import com.dell.isg.smi.virtualidentity.model.VirtualIdentityResponse;
import com.dell.isg.smi.virtualidentity.model.VirtualIdentityType;

/**
 * DTO Assembler for the IO Pool
 *
 */
@Component
public class IoPoolDtoAssemblerImpl implements IoPoolDtoAssembler {

    private static final Logger logger = LoggerFactory.getLogger(IoPoolDtoAssemblerImpl.class);


    @Override
    public IoPool transform(Pool pool) {
        if (pool == null) {
            return null;
        }
        IoPool ioPool = new IoPool();
        ioPool.setName(pool.getName());
        ioPool.setDescription(pool.getDescription());
        ioPool.setAvailableCount(pool.getAvailable());
        ioPool.setAssignedCount(pool.getAssigned());
        ioPool.setReservedCount(pool.getReserved());
        if (pool.getCreatedBy() != null) {
            ioPool.setCreatedBy(pool.getCreatedBy());
        }
        if (pool.getCreatedTime() != null) {
            try {
                ioPool.setCreatedTime(DateTimeUtils.stringToSqlDate(pool.getCreatedTime()));
            } catch (ParseException e) {
                logger.error("exception while parsing created time", e);
            }
        }
        if (pool.getUpdatedBy() != null) {
            ioPool.setUpdatedBy(pool.getUpdatedBy());
        }
        if (pool.getUpdatedTime() != null) {
            try {
                ioPool.setUpdatedTime(DateTimeUtils.stringToSqlDate(pool.getUpdatedTime()));
            } catch (ParseException e) {
                logger.error("exception while parsing updated time", e);
            }
        }
        return ioPool;
    }


    @Override
    public Pool transform(IoPool ioPool) {
        if (ioPool == null) {
            return null;
        }
        Pool pool = new Pool();
        pool.setName(ioPool.getName());
        pool.setId(ioPool.getId());
        pool.setDescription(ioPool.getDescription());

        pool.setAvailable(ioPool.getAvailableCount());
        pool.setAssigned(ioPool.getAssignedCount());
        pool.setReserved(ioPool.getReservedCount());

        pool.setCreatedBy(ioPool.getCreatedBy());
        pool.setUpdatedBy(ioPool.getUpdatedBy());
        pool.setCreatedTime(ioPool.getCreatedTime().toString());
        pool.setUpdatedTime(ioPool.getCreatedTime().toString());
        return pool;
    }


    @Override
    public VirtualIdentityPool transform(IoPoolType ioPoolType, List<IoIdentityBrief> ioIdentityBriefList) {
        if (ioPoolType == null) {
            return null;
        }
        VirtualIdentityPool virtualIdentityPool = new VirtualIdentityPool();
        virtualIdentityPool.setId(ioPoolType.getId());
        virtualIdentityPool.setType(VirtualIdentityType.fromValue(ioPoolType.getType()));
        virtualIdentityPool.setPrefix(getPrefix(ioPoolType));
        virtualIdentityPool.setAutoGenerateIdentities(ioPoolType.isAutoGenerate());

        if (ioIdentityBriefList != null && !ioIdentityBriefList.isEmpty()) {
            // update the reservation summary
            for (IoIdentityBrief ioIdentityBrief : ioIdentityBriefList) {
                if (ioIdentityBrief.getState().equals(IoIdentityStates.AVAILABLE.name())) {
                    virtualIdentityPool.setAvailable(ioIdentityBrief.getCount());
                } else if (ioIdentityBrief.getState().equals(IoIdentityStates.ASSIGNED.name())) {
                    virtualIdentityPool.setAssigned(ioIdentityBrief.getCount());
                } else if (ioIdentityBrief.getState().equals(IoIdentityStates.RESERVED.name())) {
                    virtualIdentityPool.setReserved(ioIdentityBrief.getCount());
                }
            }
        }
        return virtualIdentityPool;
    }


    @Override
    public IoPoolType transform(long poolId, VirtualIdentityPool virtualIdentityPool) {
        if (virtualIdentityPool == null) {
            return null;
        }
        IoPoolType ioPoolType = new IoPoolType();
        IoPool ioPool = new IoPool();
        ioPool.setId(poolId);
        ioPoolType.setIoPool(ioPool);
        ioPoolType.setPrefix(virtualIdentityPool.getPrefix());
        // check if type need is provided else set as null
        if (virtualIdentityPool.getType() != null && StringUtils.hasLength(virtualIdentityPool.getType().value())) {
            ioPoolType.setType(virtualIdentityPool.getType().value());
        } else {
            ioPoolType.setType(null);
        }
        ioPoolType.setAutoGenerate(virtualIdentityPool.isAutoGenerateIdentities());
        return ioPoolType;
    }


    @Override
    public VirtualIdentityResponse transform(IoIdentity ioIdentity) {
        if (ioIdentity == null) {
            return null;
        }
        VirtualIdentityResponse virtualIdentityResponse = new VirtualIdentityResponse();
        virtualIdentityResponse.setId(ioIdentity.getId());
        virtualIdentityResponse.setValue(ioIdentity.getValue());
        virtualIdentityResponse.setState(ioIdentity.getState());
        virtualIdentityResponse.setUsageId(ioIdentity.getUsageGuid());
        IoPoolType ioPoolType = ioIdentity.getIoPoolType();
        if (ioPoolType != null) {
            virtualIdentityResponse.setType(ioPoolType.getType());
        }
        IoPool ioPool = ioIdentity.getIoPool();
        if (ioPool != null) {
            virtualIdentityResponse.setPoolId(ioPool.getId());
        }
        return virtualIdentityResponse;
    }


    @Override
    public List<IoPoolType> transformToIoPoolTypeList(Pool pool) {
        List<IoPoolType> ioPoolTypes = new ArrayList<IoPoolType>();
        if (pool == null) {
            return ioPoolTypes;
        }

        if (pool.getMacPool() != null) {
            ioPoolTypes.add(transform(pool.getId(), pool.getMacPool()));
        }
        if (pool.getIqnPool() != null) {
            ioPoolTypes.add(transform(pool.getId(), pool.getIqnPool()));
        }
        if (pool.getWwpnPool() != null) {
            ioPoolTypes.add(transform(pool.getId(), pool.getWwpnPool()));
        }
        if (pool.getWwnnPool() != null) {
            ioPoolTypes.add(transform(pool.getId(), pool.getWwnnPool()));
        }
        return ioPoolTypes;
    }


    /**
     * Get the prefix("iqn.1988-11.com.dell" from "iqn.1988-11.com.dell:software-asm-01-0000000000") if the Pooltype is IQN else return original prefix
     *
     * @param ioPoolType
     * @return prefix
     */
    private static String getPrefix(IoPoolType ioPoolType) {
        String prefix = ioPoolType.getPrefix();
        if (ioPoolType.getType().equals(IoIdentityType.IQN.name())) {
            String pfs[] = prefix.split(":");
            prefix = pfs[0];
        }
        return prefix;
    }

}
