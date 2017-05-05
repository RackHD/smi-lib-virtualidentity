/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dell.isg.smi.commons.elm.model.PagedResult;
import com.dell.isg.smi.virtualidentity.entity.IoIdentity;
import com.dell.isg.smi.virtualidentity.model.AssignIdentities;
import com.dell.isg.smi.virtualidentity.model.ReserveIdentities;
import com.dell.isg.smi.virtualidentity.model.VirtualIdentityResponse;

@Service
public interface IoIdentityManager {

    public abstract void initailizePools();


    /**
     * @param ioIdentityType
     * @param usageId
     * @param numberOfReservations
     * @param expiryTimeUnitsInCalendar
     * @param expiryTimeValue
     * @param poolId
     * @return IOIdentities
     *
     * Reserves IOIdentities for a poolId and type. It will first free the expired Identities, then if required it will create new Identities.Else it will change the state of the
     * available identities. If the poolType is not expandable then it will throw exception.
     */
    public abstract List<String> reserveIdentities(ReserveIdentities reserveIdentities);


    /**
     * @param identities
     * @param usageId
     *
     * Changes the state of Identities to "ASSIGNED" for an usageId
     */
    public abstract void assignIdentities(AssignIdentities assignIdentities);


    /**
     * @param usageId
     *
     * Changes the state of Identities to "AVAILABLE" for an usageGuid
     */
    public abstract void releaseIdentitiesByUsageGuid(String usageGuid);


    /**
     * @param virtualIdentityId
     *
     * Changes the state of Identities to "AVAILABLE" for an virtualIdentityId
     */
    public abstract void releaseIdentitiesByVirtualIdentityId(long virtualIdentityId);


    /**
     * @param identityId
     * @return IOIdentity
     */
    public abstract IoIdentity findIdentityById(long identityId);


    /**
     * @param identityId
     * @return VirtualIdentityResponse
     */
    public abstract VirtualIdentityResponse getVirtualIdentityById(long identityId);


    /**
     * @param identityId
     * @return VirtualIdentityResponse
     */
    public abstract PagedResult getAllVirtualIdentitiesByFilter(String type, String usageGuid, int offset, int limit);

}
