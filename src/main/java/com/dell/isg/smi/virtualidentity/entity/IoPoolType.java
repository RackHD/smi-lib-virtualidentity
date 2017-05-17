/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.entity;

import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.BASE_ENTITY_ID;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_POOL_ID;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_POOL_TYPE_AUTO_GENERATE;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_POOL_TYPE_ID;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_POOL_TYPE_NAME;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_POOL_TYPE_OUI;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_POOL_TYPE_POOL_SIZE;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_POOL_TYPE_PREFIX;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_POOL_TYPE_SEED_VALUE;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_POOL_TYPE_SYSTEM_ID;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_POOL_TYPE_TYPE;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.T_IO_POOL_TYPE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * The Class IoPoolType.
 */
@Entity
@Table(name = T_IO_POOL_TYPE)
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = IO_POOL_TYPE_ID, referencedColumnName = BASE_ENTITY_ID)
public class IoPoolType extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -9047582368484640254L;

    @Column(name = IO_POOL_TYPE_NAME)
    private String name;

    @Column(name = IO_POOL_TYPE_TYPE)
    private String type;

    @Column(name = IO_POOL_TYPE_OUI)
    private String oui;

    @Column(name = IO_POOL_TYPE_SEED_VALUE)
    private String seedValue;

    @Column(name = IO_POOL_TYPE_AUTO_GENERATE)
    private boolean autoGenerate;

    @Column(name = IO_POOL_TYPE_SYSTEM_ID)
    private int systemId;

    @Column(name = IO_POOL_TYPE_POOL_SIZE)
    private int poolSize;

    @Column(name = IO_POOL_TYPE_PREFIX)
    private String prefix;

    @ManyToOne
    @JoinColumn(name = IO_POOL_ID)
    private IoPool ioPool;


    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * Sets the name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }


    /**
     * Sets the type.
     *
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }


    /**
     * Gets the oui.
     *
     * @return the oui
     */
    public String getOui() {
        return oui;
    }


    /**
     * Sets the oui.
     *
     * @param oui the oui to set
     */
    public void setOui(String oui) {
        this.oui = oui;
    }


    /**
     * Gets the seed value.
     *
     * @return the seedValue
     */
    public String getSeedValue() {
        return seedValue;
    }


    /**
     * Sets the seed value.
     *
     * @param seedValue the seedValue to set
     */
    public void setSeedValue(String seedValue) {
        this.seedValue = seedValue;
    }


    /**
     * Checks if is auto generate.
     *
     * @return the autoGenerate
     */
    public boolean isAutoGenerate() {
        return autoGenerate;
    }


    /**
     * Sets the auto generate.
     *
     * @param autoGenerate the autoGenerate to set
     */
    public void setAutoGenerate(boolean autoGenerate) {
        this.autoGenerate = autoGenerate;
    }


    /**
     * Gets the system id.
     *
     * @return the systemId
     */
    public int getSystemId() {
        return systemId;
    }


    /**
     * Sets the system id.
     *
     * @param systemId the systemId to set
     */
    public void setSystemId(int systemId) {
        this.systemId = systemId;
    }


    /**
     * Gets the pool size.
     *
     * @return the poolSize
     */
    public int getPoolSize() {
        return poolSize;
    }


    /**
     * Sets the pool size.
     *
     * @param poolSize the poolSize to set
     */
    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }


    /**
     * Gets the prefix.
     *
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }


    /**
     * Sets the prefix.
     *
     * @param prefix the prefix to set
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


    /**
     * Gets the io pool.
     *
     * @return the ioPool
     */
    public IoPool getIoPool() {
        return ioPool;
    }


    /**
     * Sets the io pool.
     *
     * @param ioPool the ioPool to set
     */
    public void setIoPool(IoPool ioPool) {
        this.ioPool = ioPool;
    }

}
