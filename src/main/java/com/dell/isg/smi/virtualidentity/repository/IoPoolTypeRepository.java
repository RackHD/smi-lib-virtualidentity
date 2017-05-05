/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dell.isg.smi.virtualidentity.entity.IoPoolType;

@Repository
public interface IoPoolTypeRepository extends CrudRepository<IoPoolType, Long> {

    @Query("select a from IoPoolType a where  a.ioPool.id = (select b.id from IoPool b where b.id = :ioPoolId)")
    List<IoPoolType> getByPoolId(@Param("ioPoolId") long ioPoolId);


    @Query("select a from IoPoolType a where a.type =:type and a.ioPool.id = (select b.id from IoPool b where b.id = :ioPoolId)")
    public IoPoolType findByIoPoolIdAndType(@Param("ioPoolId") long ioPoolId, @Param("type") String type);

}
