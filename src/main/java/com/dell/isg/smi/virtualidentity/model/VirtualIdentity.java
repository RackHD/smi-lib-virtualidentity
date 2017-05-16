/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class VirtualIdentity.
 */
@ApiModel
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "id", "value", "state", "type", "pool", "link" })
@XmlRootElement(name = "VirtualIdentity")
public class VirtualIdentity {

    @ApiModelProperty(hidden = true)
    protected long id;

    @ApiModelProperty(position = 1)
    @XmlElement(required = true)
    protected String value;

    @ApiModelProperty(position = 2, example = "AVAILABLE", allowableValues = "AVAILABLE,RESERVED,ASSIGNED,DELETED")
    @XmlElement(required = true)
    protected VirtualIdentityStateType state;

    @ApiModelProperty(position = 3, example = "MAC", allowableValues = "MAC,IQN,WWPN,WWNN")
    @XmlElement(required = true)
    protected VirtualIdentityType type;

    @ApiModelProperty(position = 4)
    @XmlElement(required = true)
    protected String pool;

    @ApiModelProperty(hidden = true)
    protected Link link;


    /**
     * Gets the value of the id property.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the value of the id property.
     *
     * @param value the new id
     */
    public void setId(long value) {
        this.id = value;
    }


    /**
     * Gets the value of the value property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getValue() {
        return value;
    }


    /**
     * Sets the value of the value property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setValue(String value) {
        this.value = value;
    }


    /**
     * Gets the value of the state property.
     *
     * @return possible object is {@link VirtualIdentityStateType }
     *
     */
    public VirtualIdentityStateType getState() {
        return state;
    }


    /**
     * Sets the value of the state property.
     *
     * @param value allowed object is {@link VirtualIdentityStateType }
     *
     */
    public void setState(VirtualIdentityStateType value) {
        this.state = value;
    }


    /**
     * Gets the value of the type property.
     *
     * @return possible object is {@link VirtualIdentityType }
     *
     */
    public VirtualIdentityType getType() {
        return type;
    }


    /**
     * Sets the value of the type property.
     *
     * @param value allowed object is {@link VirtualIdentityType }
     *
     */
    public void setType(VirtualIdentityType value) {
        this.type = value;
    }


    /**
     * Gets the value of the pool property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPool() {
        return pool;
    }


    /**
     * Sets the value of the pool property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPool(String value) {
        this.pool = value;
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
