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
 * @author Lakshmi.Lakkireddy
 *
 */
@Service
public interface IoPoolDtoAssembler {

    IoPool transform(Pool pool);


    Pool transform(IoPool ioPool);


    VirtualIdentityPool transform(IoPoolType ioPoolType, List<IoIdentityBrief> ioIdentityBriefList);


    IoPoolType transform(long poolId, VirtualIdentityPool virtualIdentityPool);


    List<IoPoolType> transformToIoPoolTypeList(Pool pool);


    VirtualIdentityResponse transform(IoIdentity ioIdentity);

}
