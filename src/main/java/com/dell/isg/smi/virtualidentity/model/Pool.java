/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.model;

import io.swagger.annotations.ApiModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ApiModel
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "id", "name", "description", "macPool", "iqnPool", "wwpnPool", "wwnnPool", "available", "assigned", "reserved", "createdTime", "createdBy", "updatedTime", "updatedBy", "link" })
@XmlRootElement(name = "Pool")
public class Pool {

    protected long id;
    @XmlElement(required = true)
    protected String name;
    protected String description;
    @XmlElement(required = true)
    protected VirtualIdentityPool macPool;
    @XmlElement(required = true)
    protected VirtualIdentityPool iqnPool;
    @XmlElement(required = true)
    protected VirtualIdentityPool wwpnPool;
    @XmlElement(required = true)
    protected VirtualIdentityPool wwnnPool;
    protected long available;
    protected long assigned;
    protected long reserved;
    protected String createdTime;
    protected String createdBy;
    protected String updatedTime;
    protected String updatedBy;
    protected Link link;


    /**
     * Gets the value of the id property.
     *
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the value of the id property.
     *
     */
    public void setId(long value) {
        this.id = value;
    }


    /**
     * Gets the value of the name property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getName() {
        return name;
    }


    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }


    /**
     * Gets the value of the description property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDescription() {
        return description;
    }


    /**
     * Sets the value of the description property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDescription(String value) {
        this.description = value;
    }


    /**
     * Gets the value of the macPool property.
     *
     * @return possible object is {@link VirtualIdentityPool }
     *
     */
    public VirtualIdentityPool getMacPool() {
        return macPool;
    }


    /**
     * Sets the value of the macPool property.
     *
     * @param value allowed object is {@link VirtualIdentityPool }
     *
     */
    public void setMacPool(VirtualIdentityPool value) {
        this.macPool = value;
    }


    /**
     * Gets the value of the iqnPool property.
     *
     * @return possible object is {@link VirtualIdentityPool }
     *
     */
    public VirtualIdentityPool getIqnPool() {
        return iqnPool;
    }


    /**
     * Sets the value of the iqnPool property.
     *
     * @param value allowed object is {@link VirtualIdentityPool }
     *
     */
    public void setIqnPool(VirtualIdentityPool value) {
        this.iqnPool = value;
    }


    /**
     * Gets the value of the wwpnPool property.
     *
     * @return possible object is {@link VirtualIdentityPool }
     *
     */
    public VirtualIdentityPool getWwpnPool() {
        return wwpnPool;
    }


    /**
     * Sets the value of the wwpnPool property.
     *
     * @param value allowed object is {@link VirtualIdentityPool }
     *
     */
    public void setWwpnPool(VirtualIdentityPool value) {
        this.wwpnPool = value;
    }


    /**
     * Gets the value of the wwnnPool property.
     *
     * @return possible object is {@link VirtualIdentityPool }
     *
     */
    public VirtualIdentityPool getWwnnPool() {
        return wwnnPool;
    }


    /**
     * Sets the value of the wwnnPool property.
     *
     * @param value allowed object is {@link VirtualIdentityPool }
     *
     */
    public void setWwnnPool(VirtualIdentityPool value) {
        this.wwnnPool = value;
    }


    /**
     * Gets the value of the available property.
     *
     */
    public long getAvailable() {
        return available;
    }


    /**
     * Sets the value of the available property.
     *
     */
    public void setAvailable(long value) {
        this.available = value;
    }


    /**
     * Gets the value of the assigned property.
     *
     */
    public long getAssigned() {
        return assigned;
    }


    /**
     * Sets the value of the assigned property.
     *
     */
    public void setAssigned(long value) {
        this.assigned = value;
    }


    /**
     * Gets the value of the reserved property.
     *
     */
    public long getReserved() {
        return reserved;
    }


    /**
     * Sets the value of the reserved property.
     *
     */
    public void setReserved(long value) {
        this.reserved = value;
    }


    /**
     * Gets the value of the createdTime property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCreatedTime() {
        return createdTime;
    }


    /**
     * Sets the value of the createdTime property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCreatedTime(String value) {
        this.createdTime = value;
    }


    /**
     * Gets the value of the createdBy property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCreatedBy() {
        return createdBy;
    }


    /**
     * Sets the value of the createdBy property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCreatedBy(String value) {
        this.createdBy = value;
    }


    /**
     * Gets the value of the updatedTime property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getUpdatedTime() {
        return updatedTime;
    }


    /**
     * Sets the value of the updatedTime property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setUpdatedTime(String value) {
        this.updatedTime = value;
    }


    /**
     * Gets the value of the updatedBy property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getUpdatedBy() {
        return updatedBy;
    }


    /**
     * Sets the value of the updatedBy property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setUpdatedBy(String value) {
        this.updatedBy = value;
    }


    /**
     * Gets the value of the link property.
     *
     * @return possible object is {@link Link }
     *
     */
    public Link getLink() {
        return link;
    }


    /**
     * Sets the value of the link property.
     *
     * @param value allowed object is {@link Link }
     *
     */
    public void setLink(Link value) {
        this.link = value;
    }

}
