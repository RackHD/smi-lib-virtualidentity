/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dell.isg.smi.virtualidentity.entity.IoPool;

/**
 * The Interface IoPoolRepository.
 */
@Repository
public interface IoPoolRepository extends CrudRepository<IoPool, Long> {

    /**
     * Update pool reservation.
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update IoPool as pool set availableCount = (select count(*) from IoIdentity where ioPool = pool and state ='AVAILABLE'), assignedCount = (select count(*) from IoIdentity where ioPool = pool and state ='ASSIGNED'), reservedCount = (select count(*) from IoIdentity where ioPool = pool and state ='RESERVED') ")
    void updatePoolReservation();


    /**
     * Gets the by id in.
     *
     * @param poolIdList the pool id list
     * @return the by id in
     */
    List<IoPool> getByIdIn(List<String> poolIdList);
}
