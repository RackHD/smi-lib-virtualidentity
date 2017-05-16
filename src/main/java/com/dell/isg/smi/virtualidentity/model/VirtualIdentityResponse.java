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
 * The Class VirtualIdentityResponse.
 */
@ApiModel
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "id", "value", "state", "type", "usageId", "poolId" })
@XmlRootElement(name = "VirtualIdentityResponse")
public class VirtualIdentityResponse {

    @ApiModelProperty(position = 1)
    protected long id;

    @ApiModelProperty(position = 2)
    @XmlElement(required = true)
    protected String value;

    @ApiModelProperty(position = 3)
    @XmlElement(required = true)
    protected String state;

    @ApiModelProperty(position = 4)
    @XmlElement(required = true)
    protected String type;

    @ApiModelProperty(position = 5)
    protected String usageId;

    @ApiModelProperty(position = 6)
    protected long poolId;


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
     * @return possible object is {@link String }
     *
     */
    public String getState() {
        return state;
    }


    /**
     * Sets the value of the state property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setState(String value) {
        this.state = value;
    }


    /**
     * Gets the value of the type property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getType() {
        return type;
    }


    /**
     * Sets the value of the type property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setType(String value) {
        this.type = value;
    }


    /**
     * Gets the value of the usageId property.
     *
     * @return the usage id
     */
    public String getUsageId() {
        return usageId;
    }


    /**
     * Sets the value of the usageId property.
     *
     * @param value the new usage id
     */
    public void setUsageId(String value) {
        this.usageId = value;
    }


    /**
     * Gets the value of the poolId property.
     *
     * @return the pool id
     */
    public long getPoolId() {
        return poolId;
    }


    /**
     * Sets the value of the poolId property.
     *
     * @param value the new pool id
     */
    public void setPoolId(long value) {
        this.poolId = value;
    }

}
