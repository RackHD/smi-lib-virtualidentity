/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.repository;

import java.util.Date;
import java.util.List;

import com.dell.isg.smi.virtualidentity.entity.IoIdentity;

/**
 * The Interface IoIdentityRepositoryCustom.
 */
public interface IoIdentityRepositoryCustom {

    /**
     * Assign identities.
     *
     * @param identities the identities
     * @param usageGuid the usage guid
     */
    public abstract void assignIdentities(List<String> identities, String usageGuid);


    /**
     * Reserve identities.
     *
     * @param type the type
     * @param usageGuid the usage guid
     * @param numberOfReservations the number of reservations
     * @param expiryDate the expiry date
     * @param poolId the pool id
     * @return the list
     */
    public abstract List<IoIdentity> reserveIdentities(String type, String usageGuid, int numberOfReservations, Date expiryDate, long poolId);


    /**
     * Save or update io identities.
     *
     * @param identityList the identity list
     * @param updateIdentityList the update identity list
     * @return the list
     */
    public abstract List<IoIdentity> saveOrUpdateIoIdentities(List<IoIdentity> identityList, List<IoIdentity> updateIdentityList);

}