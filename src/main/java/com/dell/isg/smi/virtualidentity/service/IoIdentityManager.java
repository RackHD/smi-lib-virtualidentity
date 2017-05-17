/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dell.isg.smi.commons.utilities.model.PagedResult;
import com.dell.isg.smi.virtualidentity.entity.IoIdentity;
import com.dell.isg.smi.virtualidentity.model.AssignIdentities;
import com.dell.isg.smi.virtualidentity.model.ReserveIdentities;
import com.dell.isg.smi.virtualidentity.model.VirtualIdentityResponse;

/**
 * The Interface IoIdentityManager.
 */
@Service
public interface IoIdentityManager {

    /**
     * Initailize pools.
     */
    public abstract void initailizePools();


    /**
     * Reserve identities.
     *
     * @param reserveIdentities the reserve identities
     * @return IOIdentities
     * 
     * Reserves IOIdentities for a poolId and type. It will first free the expired Identities, then if required it will create new Identities.Else it will change the state of the
     * available identities. If the poolType is not expandable then it will throw exception.
     */
    public abstract List<String> reserveIdentities(ReserveIdentities reserveIdentities);


    /**
     * Assign identities.
     *
     * @param assignIdentities the assign identities
     */
    public abstract void assignIdentities(AssignIdentities assignIdentities);


    /**
     * Release identities by usage guid.
     *
     * @param usageGuid the usage guid
     */
    public abstract void releaseIdentitiesByUsageGuid(String usageGuid);


    /**
     * Release identities by virtual identity id.
     *
     * @param virtualIdentityId Changes the state of Identities to "AVAILABLE" for an virtualIdentityId
     */
    public abstract void releaseIdentitiesByVirtualIdentityId(long virtualIdentityId);


    /**
     * Find identity by id.
     *
     * @param identityId the identity id
     * @return IOIdentity
     */
    public abstract IoIdentity findIdentityById(long identityId);


    /**
     * Gets the virtual identity by id.
     *
     * @param identityId the identity id
     * @return VirtualIdentityResponse
     */
    public abstract VirtualIdentityResponse getVirtualIdentityById(long identityId);


    /**
     * Gets the all virtual identities by filter.
     *
     * @param type the type
     * @param usageGuid the usage guid
     * @param offset the offset
     * @param limit the limit
     * @return VirtualIdentityResponse
     */
    public abstract PagedResult getAllVirtualIdentitiesByFilter(String type, String usageGuid, int offset, int limit);

}
