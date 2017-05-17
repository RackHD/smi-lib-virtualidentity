/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dell.isg.smi.virtualidentity.entity.IoIdentity;
import com.dell.isg.smi.virtualidentity.entity.IoPool;
import com.dell.isg.smi.virtualidentity.entity.IoPoolType;
import com.dell.isg.smi.virtualidentity.model.IoIdentityBrief;
import com.dell.isg.smi.virtualidentity.model.Pool;
import com.dell.isg.smi.virtualidentity.model.VirtualIdentityPool;
import com.dell.isg.smi.virtualidentity.model.VirtualIdentityResponse;

/**
 * The Interface IoPoolDtoAssembler.
 */
@Service
public interface IoPoolDtoAssembler {

    /**
     * Transform.
     *
     * @param pool the pool
     * @return the io pool
     */
    IoPool transform(Pool pool);


    /**
     * Transform.
     *
     * @param ioPool the io pool
     * @return the pool
     */
    Pool transform(IoPool ioPool);


    /**
     * Transform.
     *
     * @param ioPoolType the io pool type
     * @param ioIdentityBriefList the io identity brief list
     * @return the virtual identity pool
     */
    VirtualIdentityPool transform(IoPoolType ioPoolType, List<IoIdentityBrief> ioIdentityBriefList);


    /**
     * Transform.
     *
     * @param poolId the pool id
     * @param virtualIdentityPool the virtual identity pool
     * @return the io pool type
     */
    IoPoolType transform(long poolId, VirtualIdentityPool virtualIdentityPool);


    /**
     * Transform to io pool type list.
     *
     * @param pool the pool
     * @return the list
     */
    List<IoPoolType> transformToIoPoolTypeList(Pool pool);


    /**
     * Transform.
     *
     * @param ioIdentity the io identity
     * @return the virtual identity response
     */
    VirtualIdentityResponse transform(IoIdentity ioIdentity);

}
