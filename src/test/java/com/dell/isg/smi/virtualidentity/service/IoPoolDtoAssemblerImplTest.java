/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import mockit.Tested;

import org.junit.Before;
import org.junit.Test;

import com.dell.isg.smi.virtualidentity.entity.IoIdentity;
import com.dell.isg.smi.virtualidentity.entity.IoPool;
import com.dell.isg.smi.virtualidentity.entity.IoPoolType;
import com.dell.isg.smi.virtualidentity.model.IoIdentityBrief;
import com.dell.isg.smi.virtualidentity.model.IoIdentityType;
import com.dell.isg.smi.virtualidentity.model.Pool;
import com.dell.isg.smi.virtualidentity.model.VirtualIdentityPool;
import com.dell.isg.smi.virtualidentity.model.VirtualIdentityResponse;
import com.dell.isg.smi.virtualidentity.model.VirtualIdentityType;
import com.dell.isg.smi.virtualidentity.service.IoPoolDtoAssembler;
import com.dell.isg.smi.virtualidentity.service.IoPoolDtoAssemblerImpl;

/**
 * @author Lakshmi.Lakkireddy
 *
 */
public class IoPoolDtoAssemblerImplTest {

    @Tested
    IoPoolDtoAssembler ioPoolDtoAssembler;


    @Before
    public void setup() {
        ioPoolDtoAssembler = new IoPoolDtoAssemblerImpl();
    }


    @Test
    public void ioPoolDtoAssemblerImplShouldImplementIoPoolDtoAssembler() {
        final Class<?>[] interfaces = IoPoolDtoAssemblerImpl.class.getInterfaces();
        assertThat(interfaces, is(notNullValue()));

        boolean implementedIoPoolDtoAssembler = false;
        for (final Class<?> interfaceClass : interfaces) {
            if (interfaceClass == IoPoolDtoAssembler.class) {
                implementedIoPoolDtoAssembler = true;
                break;
            }
        }

        assertThat(implementedIoPoolDtoAssembler, is(true));
    }


    @Test
    public void transformPoolShouldReturnNullWhenEntityIsNull() {
        final IoPool entity = null;

        final Pool actual = ioPoolDtoAssembler.transform(entity);

        assertNull(actual);
    }


    @Test
    public void transformPoolShouldBePopulated() {
        final long currentTime = 1444080293; // 2015/10/05 04:24:53 CST
        final IoPool entity = new IoPool();
        entity.setCreatedBy("admin");
        entity.setDescription("Io Pool description");
        entity.setAssignedCount(100L);
        entity.setAvailableCount(200L);
        entity.setReservedCount(150L);
        entity.setId(100L);
        entity.setCreatedTime(new java.sql.Date(currentTime));
        entity.setDeletable(true);
        entity.setExpandable(false);
        entity.setServerTemplateCount(20L);

        final Pool actual = ioPoolDtoAssembler.transform(entity);

        assertThat(actual.getId(), is(equalTo(entity.getId())));
        assertThat(actual.getName(), is(equalTo(entity.getName())));
        assertThat(actual.getDescription(), is(equalTo(entity.getDescription())));
        assertThat(actual.getAssigned(), is(equalTo(entity.getAssignedCount())));
        assertThat(actual.getAvailable(), is(equalTo(entity.getAvailableCount())));
        assertThat(actual.getReserved(), is(equalTo(entity.getReservedCount())));
        assertThat(actual.getCreatedBy(), is(equalTo(entity.getCreatedBy())));
        assertThat(actual.getCreatedTime(), is(equalTo(entity.getCreatedTime().toString())));
    }


    @Test
    public void transformIoPoolShouldReturnNullWhenModelObjectIsNull() {
        final Pool modelObject = null;

        final IoPool actual = ioPoolDtoAssembler.transform(modelObject);

        assertNull(actual);
    }


    @Test
    public void transformIoPoolShouldBePopulated() {
        final long currentTime = 1444080293; // 2015/10/05 04:24:53 CST
        final Pool modelObject = new Pool();
        modelObject.setCreatedBy("admin");
        modelObject.setDescription("Io Pool description");
        modelObject.setAssigned(100L);
        modelObject.setAvailable(200L);
        modelObject.setReserved(150L);
        modelObject.setId(100L);
        modelObject.setCreatedTime(String.valueOf(currentTime));

        final IoPool actual = ioPoolDtoAssembler.transform(modelObject);

        assertThat(actual.getName(), is(equalTo(modelObject.getName())));
        assertThat(actual.getDescription(), is(equalTo(modelObject.getDescription())));
        assertThat(actual.getAssignedCount(), is(equalTo(modelObject.getAssigned())));
        assertThat(actual.getAvailableCount(), is(equalTo(modelObject.getAvailable())));
        assertThat(actual.getReservedCount(), is(equalTo(modelObject.getReserved())));
        assertThat(actual.getCreatedBy(), is(equalTo(modelObject.getCreatedBy())));
        assertThat(actual.getCreatedTime(), is(equalTo(actual.getCreatedTime())));
    }


    @Test
    public void transformVirtualIdentityPoolShouldReturnNullWhenEntityIsNull() {
        final IoPoolType entity = null;
        final List<IoIdentityBrief> ioIdentityBriefList = null;

        final VirtualIdentityPool actual = ioPoolDtoAssembler.transform(entity, ioIdentityBriefList);

        assertNull(actual);
    }


    @Test
    public void transformVirutalIdentityPoolShouldBePopulated() {

        final IoPoolType ioPoolType = new IoPoolType();
        ioPoolType.setId(100L);
        ioPoolType.setAutoGenerate(true);
        ioPoolType.setType("IQN");
        ioPoolType.setPrefix("iqn.1988-11.com.dell:software-asm-01-0000000000");

        List<IoIdentityBrief> identityBriefList = new ArrayList<IoIdentityBrief>();

        IoIdentityBrief ioIdentityBrief = new IoIdentityBrief();
        ioIdentityBrief.setState("AVAILABLE");
        ioIdentityBrief.setCount(125L);
        identityBriefList.add(ioIdentityBrief);

        IoIdentityBrief ioIdentityBrief1 = new IoIdentityBrief();
        ioIdentityBrief1.setState("RESERVED");
        ioIdentityBrief1.setCount(155L);
        identityBriefList.add(ioIdentityBrief1);

        IoIdentityBrief ioIdentityBrief2 = new IoIdentityBrief();
        ioIdentityBrief2.setState("ASSIGNED");
        ioIdentityBrief2.setCount(175L);
        identityBriefList.add(ioIdentityBrief2);

        final VirtualIdentityPool actual = ioPoolDtoAssembler.transform(ioPoolType, identityBriefList);

        assertThat(actual.getId(), is(equalTo(ioPoolType.getId())));
        assertThat(actual.getType(), is(equalTo(VirtualIdentityType.fromValue(ioPoolType.getType()))));
        assertThat(actual.getPrefix(), is(equalTo(getPrefix(ioPoolType))));
        assertThat(actual.isAutoGenerateIdentities(), is(equalTo(ioPoolType.isAutoGenerate())));
        assertThat(actual.getAvailable(), is(equalTo(ioIdentityBrief.getCount())));
        assertThat(actual.getAssigned(), is(equalTo(ioIdentityBrief2.getCount())));
        assertThat(actual.getReserved(), is(equalTo(ioIdentityBrief1.getCount())));
    }


    @Test
    public void transformVirtualIdentityShouldReturnNullWhenEntityIsNull() {
        final IoIdentity entity = null;

        final VirtualIdentityResponse actual = ioPoolDtoAssembler.transform(entity);

        assertNull(actual);
    }


    @Test
    public void transformVirtualIdentityShouldBePopulated() {
        final IoIdentity entity = new IoIdentity();
        entity.setValue("Value");
        entity.setState("AVAILABLE");

        final IoPoolType ioPoolType = new IoPoolType();
        ioPoolType.setType("IQN");

        IoPool ioPool = new IoPool();
        ioPool.setName("Io Pool Name");
        ioPoolType.setIoPool(ioPool);
        entity.setIoPoolType(ioPoolType);

        final VirtualIdentityResponse actual = ioPoolDtoAssembler.transform(entity);

        assertThat(actual.getValue(), is(equalTo(entity.getValue())));
        assertThat(actual.getState(), is(equalTo(entity.getState())));
        assertThat(actual.getType(), is(equalTo(entity.getIoPoolType().getType())));
        assertThat(actual.getPoolId(), is(equalTo(entity.getIoPoolType().getIoPool().getId())));
    }


    @Test
    public void transformToIoPoolTypeShouldReturnNullWhenEntityIsNull() {
        final VirtualIdentityPool entity = null;

        final IoPoolType actual = ioPoolDtoAssembler.transform(100L, entity);

        assertNull(actual);
    }


    @Test
    public void transformToIoPoolTypeShouldBePopulated() {
        final Pool entity = new Pool();
        VirtualIdentityPool virtualPool = new VirtualIdentityPool();
        virtualPool.setType(VirtualIdentityType.MAC);
        entity.setMacPool(virtualPool);
        VirtualIdentityPool virtualPool1 = new VirtualIdentityPool();
        virtualPool1.setType(VirtualIdentityType.IQN);
        entity.setIqnPool(virtualPool1);
        VirtualIdentityPool virtualPool2 = new VirtualIdentityPool();
        virtualPool2.setType(VirtualIdentityType.WWPN);
        entity.setWwpnPool(virtualPool2);
        VirtualIdentityPool virtualPool3 = new VirtualIdentityPool();
        virtualPool3.setType(VirtualIdentityType.WWNN);
        entity.setWwnnPool(virtualPool3);

        IoPoolType actual = ioPoolDtoAssembler.transform(entity.getId(), virtualPool);
        assertThat(actual.getType(), is(equalTo(virtualPool.getType().value())));

        actual = ioPoolDtoAssembler.transform(entity.getId(), virtualPool1);
        assertThat(actual.getType(), is(equalTo(virtualPool1.getType().value())));

        actual = ioPoolDtoAssembler.transform(entity.getId(), virtualPool2);
        assertThat(actual.getType(), is(equalTo(virtualPool2.getType().value())));

        actual = ioPoolDtoAssembler.transform(entity.getId(), virtualPool3);
        assertThat(actual.getType(), is(equalTo(virtualPool3.getType().value())));

    }


    @Test
    public void transformToIoPoolTypeListShouldReturnNullWhenEntityIsNull() {
        final Pool entity = null;

        final List<IoPoolType> actual = ioPoolDtoAssembler.transformToIoPoolTypeList(entity);

        assertThat(actual.size(), is(equalTo(0)));
    }


    @Test
    public void transformToIoPoolTypeListShouldBePopulated() {
        final Pool entity = new Pool();
        VirtualIdentityPool virtualPool = new VirtualIdentityPool();
        virtualPool.setType(VirtualIdentityType.MAC);
        entity.setMacPool(virtualPool);
        VirtualIdentityPool virtualPool1 = new VirtualIdentityPool();
        virtualPool1.setType(VirtualIdentityType.IQN);
        entity.setIqnPool(virtualPool1);
        VirtualIdentityPool virtualPool2 = new VirtualIdentityPool();
        virtualPool2.setType(VirtualIdentityType.WWPN);
        entity.setWwpnPool(virtualPool2);
        VirtualIdentityPool virtualPool3 = new VirtualIdentityPool();
        virtualPool3.setType(VirtualIdentityType.WWNN);
        entity.setWwnnPool(virtualPool3);

        final List<IoPoolType> actual = ioPoolDtoAssembler.transformToIoPoolTypeList(entity);

        assertThat(actual.size(), is(equalTo(4)));

        IoPoolType ioPoolType = actual.get(0);
        assertThat(ioPoolType.getType(), is(equalTo(virtualPool.getType().value())));

        IoPoolType ioPoolType1 = actual.get(1);
        assertThat(ioPoolType1.getType(), is(equalTo(virtualPool1.getType().value())));

        IoPoolType ioPoolType2 = actual.get(2);
        assertThat(ioPoolType2.getType(), is(equalTo(virtualPool2.getType().value())));

        IoPoolType ioPoolType3 = actual.get(3);
        assertThat(ioPoolType3.getType(), is(equalTo(virtualPool3.getType().value())));

    }


    private static String getPrefix(IoPoolType ioPoolType) {
        String prefix = ioPoolType.getPrefix();
        if (ioPoolType.getType().equals(IoIdentityType.IQN.name())) {
            String pfs[] = prefix.split(":");
            prefix = pfs[0];
        }
        return prefix;
    }
}
