/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.repository;

import java.util.Date;
import java.util.List;

import com.dell.isg.smi.virtualidentity.entity.IoIdentity;

public interface IoIdentityRepositoryCustom {

    public abstract void assignIdentities(List<String> identities, String usageGuid);


    public abstract List<IoIdentity> reserveIdentities(String type, String usageGuid, int numberOfReservations, Date expiryDate, long poolId);


    public abstract List<IoIdentity> saveOrUpdateIoIdentities(List<IoIdentity> identityList, List<IoIdentity> updateIdentityList);

}