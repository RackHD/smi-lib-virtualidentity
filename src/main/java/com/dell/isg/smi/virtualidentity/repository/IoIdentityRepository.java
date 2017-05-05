/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dell.isg.smi.virtualidentity.entity.IoIdentity;

@Repository
public interface IoIdentityRepository extends CrudRepository<IoIdentity, Long>, IoIdentityRepositoryCustom {

    @Query("select a from IoIdentity a where a.state=:state and a.ioPoolType.id = (select b.id from IoPoolType b where b.type = :type)")
    List<IoIdentity> findByTypeAndState(@Param("type") String type, @Param("state") String state);


    @Query("select a from IoIdentity a where a.state=:state and a.ioPoolType.id = (select b.id from IoPoolType b where b.type = :type and b.ioPool.id = (select c.id from IoPool c where c.id= :ioPoolId)) order by a.value asc")
    List<IoIdentity> findByTypeAndStateAndPoolIdOrderByValueAsc(@Param("type") String type, @Param("state") String state, @Param("ioPoolId") long ioPoolId, Pageable pageable);


    @Query("select count(distinct a.id) as count from IoIdentity a, IoPoolType b where a.ioPoolType.id = b.id and b.type = :type")
    long findPoolSizeByType(@Param("type") String type);


    @Query("select a from IoIdentity a where a.isCustom = false and a.ioPoolType.id = (select b.id from IoPoolType b where b.type = :type) order by a.value desc")
    IoIdentity findByTypeAndIsCustomFalseOrderByValueDesc(@Param("type") String type);


    @Query("select a from IoIdentity a where a.macAddress is not null and a.ioPoolType.id = (select b.id from IoPoolType b where b.type = :type) order by a.macAddress desc")
    IoIdentity findByMacNotNullAndTypeOrderByMacAddressDesc(@Param("type") String type);


    @Query("select count(distinct a.id) from IoIdentity a, IoPoolType b where a.ioPoolType.id = b.id and b.type = :type")
    long findCountByType(@Param("type") String type);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update IoIdentity set state='AVAILABLE', expiryDate=NULL, usageGuid=NULL where expiryDate < CURRENT_TIMESTAMP")
    void releaseExpired();


    Page<IoIdentity> findByUsageGuidOrderByValueAsc(String usageGuid, Pageable pageable);


    List<IoIdentity> findByUsageGuidAndState(String usageGuid, String state);


    @Query("select a from IoIdentity a where a.usageGuid=:usageGuid and a.ioPoolType.id = (select b.id from IoPoolType b where b.type = :type) order by a.value asc")
    Page<IoIdentity> findByUsageGuidAndTypeOrderByValueAsc(@Param("usageGuid") String usageGuid, @Param("type") String type, Pageable pageable);


    Page<IoIdentity> findByIoPoolTypeTypeOrderByValueAsc(@Param("type") String type, Pageable pageable);


    @Modifying(clearAutomatically = true)
    @Query("update IoIdentity set state='AVAILABLE', expiryDate=NULL, usageGuid=NULL where usageGuid = :usageGuid")
    void releaseByUsageGuid(@Param("usageGuid") String usageGuid);


    @Modifying(clearAutomatically = true)
    @Query("update IoIdentity set state='AVAILABLE', expiryDate=NULL, usageGuid=NULL where id = :identityId")
    void releaseById(@Param("identityId") long identityId);


    @Query("select count(a.id) from IoIdentity a where a.value in (:identityValueList)")
    int countMatching(@Param("identityValueList") List<String> identityValueList);


    IoIdentity findByValueIgnoreCaseLikeOrderByValueDesc(String value);


    IoIdentity findByMacAddressIn(List<String> macAddressList);


    @Query("select a from IoIdentity a where a.ioPoolType.id = (select b.id from IoPoolType b where b.type = :type and b.ioPool.id = (select c.id from IoPool c where c.id= :ioPoolId)) order by a.value desc")
    List<IoIdentity> findByPoolIdAndTypeOrderByValueDesc(@Param("ioPoolId") long ioPoolId, @Param("type") String type);


    List<IoIdentity> findByIoPoolIdInAndStateIn(List<String> poolIdList, List<String> stateList);


    IoIdentity findById(long Id);


    IoIdentity findByValue(String value);


    List<IoIdentity> findByValueIn(List<String> identityValueList);


    long countByUsageGuid(String usageGuid);


    Page<IoIdentity> findAll(Pageable pageable);

}
