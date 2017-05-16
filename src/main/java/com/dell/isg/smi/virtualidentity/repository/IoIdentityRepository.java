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

/**
 * The Interface IoIdentityRepository.
 */
@Repository
public interface IoIdentityRepository extends CrudRepository<IoIdentity, Long>, IoIdentityRepositoryCustom {

    /**
     * Find by type and state.
     *
     * @param type the type
     * @param state the state
     * @return the list
     */
    @Query("select a from IoIdentity a where a.state=:state and a.ioPoolType.id = (select b.id from IoPoolType b where b.type = :type)")
    List<IoIdentity> findByTypeAndState(@Param("type") String type, @Param("state") String state);


    /**
     * Find by type and state and pool id order by value asc.
     *
     * @param type the type
     * @param state the state
     * @param ioPoolId the io pool id
     * @param pageable the pageable
     * @return the list
     */
    @Query("select a from IoIdentity a where a.state=:state and a.ioPoolType.id = (select b.id from IoPoolType b where b.type = :type and b.ioPool.id = (select c.id from IoPool c where c.id= :ioPoolId)) order by a.value asc")
    List<IoIdentity> findByTypeAndStateAndPoolIdOrderByValueAsc(@Param("type") String type, @Param("state") String state, @Param("ioPoolId") long ioPoolId, Pageable pageable);


    /**
     * Find pool size by type.
     *
     * @param type the type
     * @return the long
     */
    @Query("select count(distinct a.id) as count from IoIdentity a, IoPoolType b where a.ioPoolType.id = b.id and b.type = :type")
    long findPoolSizeByType(@Param("type") String type);


    /**
     * Find by type and is custom false order by value desc.
     *
     * @param type the type
     * @return the io identity
     */
    @Query("select a from IoIdentity a where a.isCustom = false and a.ioPoolType.id = (select b.id from IoPoolType b where b.type = :type) order by a.value desc")
    IoIdentity findByTypeAndIsCustomFalseOrderByValueDesc(@Param("type") String type);


    /**
     * Find by mac not null and type order by mac address desc.
     *
     * @param type the type
     * @return the io identity
     */
    @Query("select a from IoIdentity a where a.macAddress is not null and a.ioPoolType.id = (select b.id from IoPoolType b where b.type = :type) order by a.macAddress desc")
    IoIdentity findByMacNotNullAndTypeOrderByMacAddressDesc(@Param("type") String type);


    /**
     * Find count by type.
     *
     * @param type the type
     * @return the long
     */
    @Query("select count(distinct a.id) from IoIdentity a, IoPoolType b where a.ioPoolType.id = b.id and b.type = :type")
    long findCountByType(@Param("type") String type);


    /**
     * Release expired.
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update IoIdentity set state='AVAILABLE', expiryDate=NULL, usageGuid=NULL where expiryDate < CURRENT_TIMESTAMP")
    void releaseExpired();


    /**
     * Find by usage guid order by value asc.
     *
     * @param usageGuid the usage guid
     * @param pageable the pageable
     * @return the page
     */
    Page<IoIdentity> findByUsageGuidOrderByValueAsc(String usageGuid, Pageable pageable);


    /**
     * Find by usage guid and state.
     *
     * @param usageGuid the usage guid
     * @param state the state
     * @return the list
     */
    List<IoIdentity> findByUsageGuidAndState(String usageGuid, String state);


    /**
     * Find by usage guid and type order by value asc.
     *
     * @param usageGuid the usage guid
     * @param type the type
     * @param pageable the pageable
     * @return the page
     */
    @Query("select a from IoIdentity a where a.usageGuid=:usageGuid and a.ioPoolType.id = (select b.id from IoPoolType b where b.type = :type) order by a.value asc")
    Page<IoIdentity> findByUsageGuidAndTypeOrderByValueAsc(@Param("usageGuid") String usageGuid, @Param("type") String type, Pageable pageable);


    /**
     * Find by io pool type type order by value asc.
     *
     * @param type the type
     * @param pageable the pageable
     * @return the page
     */
    Page<IoIdentity> findByIoPoolTypeTypeOrderByValueAsc(@Param("type") String type, Pageable pageable);


    /**
     * Release by usage guid.
     *
     * @param usageGuid the usage guid
     */
    @Modifying(clearAutomatically = true)
    @Query("update IoIdentity set state='AVAILABLE', expiryDate=NULL, usageGuid=NULL where usageGuid = :usageGuid")
    void releaseByUsageGuid(@Param("usageGuid") String usageGuid);


    /**
     * Release by id.
     *
     * @param identityId the identity id
     */
    @Modifying(clearAutomatically = true)
    @Query("update IoIdentity set state='AVAILABLE', expiryDate=NULL, usageGuid=NULL where id = :identityId")
    void releaseById(@Param("identityId") long identityId);


    /**
     * Count matching.
     *
     * @param identityValueList the identity value list
     * @return the int
     */
    @Query("select count(a.id) from IoIdentity a where a.value in (:identityValueList)")
    int countMatching(@Param("identityValueList") List<String> identityValueList);


    /**
     * Find by value ignore case like order by value desc.
     *
     * @param value the value
     * @return the io identity
     */
    IoIdentity findByValueIgnoreCaseLikeOrderByValueDesc(String value);


    /**
     * Find by mac address in.
     *
     * @param macAddressList the mac address list
     * @return the io identity
     */
    IoIdentity findByMacAddressIn(List<String> macAddressList);


    /**
     * Find by pool id and type order by value desc.
     *
     * @param ioPoolId the io pool id
     * @param type the type
     * @return the list
     */
    @Query("select a from IoIdentity a where a.ioPoolType.id = (select b.id from IoPoolType b where b.type = :type and b.ioPool.id = (select c.id from IoPool c where c.id= :ioPoolId)) order by a.value desc")
    List<IoIdentity> findByPoolIdAndTypeOrderByValueDesc(@Param("ioPoolId") long ioPoolId, @Param("type") String type);


    /**
     * Find by io pool id in and state in.
     *
     * @param poolIdList the pool id list
     * @param stateList the state list
     * @return the list
     */
    List<IoIdentity> findByIoPoolIdInAndStateIn(List<String> poolIdList, List<String> stateList);


    /**
     * Find by id.
     *
     * @param Id the id
     * @return the io identity
     */
    IoIdentity findById(long Id);


    /**
     * Find by value.
     *
     * @param value the value
     * @return the io identity
     */
    IoIdentity findByValue(String value);


    /**
     * Find by value in.
     *
     * @param identityValueList the identity value list
     * @return the list
     */
    List<IoIdentity> findByValueIn(List<String> identityValueList);


    /**
     * Count by usage guid.
     *
     * @param usageGuid the usage guid
     * @return the long
     */
    long countByUsageGuid(String usageGuid);


    /**
     * Find all.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<IoIdentity> findAll(Pageable pageable);

}
