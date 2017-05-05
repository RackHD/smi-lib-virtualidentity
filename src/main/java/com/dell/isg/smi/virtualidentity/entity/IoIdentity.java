/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.entity;

import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.BASE_ENTITY_ID;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_IDENTITY_EXPIRY_DATE;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_IDENTITY_ID;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_IDENTITY_IS_CUSTOM;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_IDENTITY_MAC_FIP;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_IDENTITY_NAME;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_IDENTITY_SECONDARY_VALUE;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_IDENTITY_STATE;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_IDENTITY_USAGE_GUID;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_IDENTITY_VALUE;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_POOL_ID;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_POOL_TYPE_ID;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.T_IO_IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author Lakshmi.Lakkireddy
 *
 */
@Entity
@Table(name = T_IO_IDENTITY)
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = IO_IDENTITY_ID, referencedColumnName = BASE_ENTITY_ID)
public class IoIdentity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8062511108741710550L;

    @Column(name = IO_IDENTITY_NAME)
    private String name;

    @Column(name = IO_IDENTITY_VALUE)
    private String value;

    @Column(name = IO_IDENTITY_IS_CUSTOM)
    private boolean isCustom;

    @Column(name = IO_IDENTITY_SECONDARY_VALUE)
    private String secondaryValue;

    @Column(name = IO_IDENTITY_MAC_FIP)
    private String macAddress;

    @Column(name = IO_IDENTITY_STATE)
    private String state;

    @Column(name = IO_IDENTITY_USAGE_GUID)
    private String usageGuid;

    @Column(name = IO_IDENTITY_EXPIRY_DATE)
    private Date expiryDate;

    @ManyToOne
    @JoinColumn(name = IO_POOL_TYPE_ID)
    private IoPoolType ioPoolType;

    @ManyToOne
    @JoinColumn(name = IO_POOL_ID)
    private IoPool ioPool;


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }


    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }


    /**
     * @return the isCustom
     */
    public boolean isCustom() {
        return isCustom;
    }


    /**
     * @param isCustom the isCustom to set
     */
    public void setCustom(boolean isCustom) {
        this.isCustom = isCustom;
    }


    /**
     * @return the secondaryValue
     */
    public String getSecondaryValue() {
        return secondaryValue;
    }


    /**
     * @param secondaryValue the secondaryValue to set
     */
    public void setSecondaryValue(String secondaryValue) {
        this.secondaryValue = secondaryValue;
    }


    /**
     * @return the macAddress
     */
    public String getMacAddress() {
        return macAddress;
    }


    /**
     * @param macAddress the macAddress to set
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }


    /**
     * @return the state
     */
    public String getState() {
        return state;
    }


    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }


    /**
     * @return the usageGuid
     */
    public String getUsageGuid() {
        return usageGuid;
    }


    /**
     * @param usageId the usageGuid to set
     */
    public void setUsageGuid(String usageGuid) {
        this.usageGuid = usageGuid;
    }


    /**
     * @return the expiryDate
     */
    public Date getExpiryDate() {
        return expiryDate;
    }


    /**
     * @param expiryDate the expiryDate to set
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }


    /**
     * @return the ioPoolType
     */
    public IoPoolType getIoPoolType() {
        return ioPoolType;
    }


    /**
     * @param ioPoolType the ioPoolType to set
     */
    public void setIoPoolType(IoPoolType ioPoolType) {
        this.ioPoolType = ioPoolType;
    }


    /**
     * @return the ioPool
     */
    public IoPool getIoPool() {
        return ioPool;
    }


    /**
     * @param ioPool the ioPool to set
     */
    public void setIoPool(IoPool ioPool) {
        this.ioPool = ioPool;
    }

}
