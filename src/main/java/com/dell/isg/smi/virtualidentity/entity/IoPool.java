/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.entity;

import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.BASE_ENTITY_ID;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_POOL_ASSIGNED_COUNT;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_POOL_AVAILABLE_COUNT;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_POOL_DESCRIPTION;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_POOL_ID;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_POOL_IS_DELETABLE;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_POOL_IS_EXPANDABLE;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_POOL_NAME;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_POOL_RESERVED_COUNT;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.IO_POOL_SERVER_TEMPLATE_COUNT;
import static com.dell.isg.smi.virtualidentity.configuration.IdentityTableConstants.T_IO_POOL;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author Lakshmi.Lakkireddy
 *
 */
@Entity
@Table(name = T_IO_POOL)
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = IO_POOL_ID, referencedColumnName = BASE_ENTITY_ID)
public class IoPool extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -5176622872420901066L;

    @Column(name = IO_POOL_NAME)
    private String name;

    @Column(name = IO_POOL_DESCRIPTION)
    private String description;

    @Column(name = IO_POOL_IS_EXPANDABLE)
    private boolean isExpandable = true;

    @Column(name = IO_POOL_IS_DELETABLE)
    private boolean isDeletable = true;

    @Column(name = IO_POOL_AVAILABLE_COUNT)
    private Long availableCount = 0L;

    @Column(name = IO_POOL_RESERVED_COUNT)
    private Long reservedCount = 0L;

    @Column(name = IO_POOL_ASSIGNED_COUNT)
    private Long assignedCount = 0L;

    @Column(name = IO_POOL_SERVER_TEMPLATE_COUNT)
    private Long serverTemplateCount = 0L;


    // @OneToMany(mappedBy="ioPool")
    // private Set<IoPoolType> ioPoolTypes;

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
     * @return the description
     */
    public String getDescription() {
        return description;
    }


    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * @return the isExpandable
     */
    public boolean isExpandable() {
        return isExpandable;
    }


    /**
     * @param isExpandable the isExpandable to set
     */
    public void setExpandable(boolean isExpandable) {
        this.isExpandable = isExpandable;
    }


    /**
     * @return the isDeletable
     */
    public boolean isDeletable() {
        return isDeletable;
    }


    /**
     * @param isDeletable the isDeletable to set
     */
    public void setDeletable(boolean isDeletable) {
        this.isDeletable = isDeletable;
    }


    /**
     * @return the availableCount
     */
    public Long getAvailableCount() {
        return availableCount;
    }


    /**
     * @param availableCount the availableCount to set
     */
    public void setAvailableCount(Long availableCount) {
        this.availableCount = availableCount;
    }


    /**
     * @return the reservedCount
     */
    public Long getReservedCount() {
        return reservedCount;
    }


    /**
     * @param reservedCount the reservedCount to set
     */
    public void setReservedCount(Long reservedCount) {
        this.reservedCount = reservedCount;
    }


    /**
     * @return the assignedCount
     */
    public Long getAssignedCount() {
        return assignedCount;
    }


    /**
     * @param assignedCount the assignedCount to set
     */
    public void setAssignedCount(Long assignedCount) {
        this.assignedCount = assignedCount;
    }


    /**
     * @return the serverTemplateCount
     */
    public Long getServerTemplateCount() {
        return serverTemplateCount;
    }


    /**
     * @param serverTemplateCount the serverTemplateCount to set
     */
    public void setServerTemplateCount(Long serverTemplateCount) {
        this.serverTemplateCount = serverTemplateCount;
    }

    // public Set<IoPoolType> getIoPoolTypes() {
    // return ioPoolTypes;
    // }

    // public void setIoPoolTypes(Set<IoPoolType> ioPoolTypes) {
    // this.ioPoolTypes = ioPoolTypes;
    // }

}
