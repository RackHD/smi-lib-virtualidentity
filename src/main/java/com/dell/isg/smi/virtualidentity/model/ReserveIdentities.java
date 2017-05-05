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

@ApiModel
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "usageId", "identityType", "quantityRequested" })
@XmlRootElement(name = "ReserveIdentities")
public class ReserveIdentities {

    @ApiModelProperty(position = 1, example = "A1B2C3D")
    protected String usageId;

    @ApiModelProperty(position = 2, example = "MAC", allowableValues = "MAC,IQN,WWPN,WWNN")
    @XmlElement(required = true)
    protected String identityType;

    @ApiModelProperty(position = 3, example = "1")
    protected int quantityRequested;


    /**
     * Gets the value of the usageId property.
     *
     */
    public String getUsageId() {
        return usageId;
    }


    /**
     * Sets the value of the usageId property.
     *
     */
    public void setUsageId(String value) {
        this.usageId = value;
    }


    /**
     * Gets the value of the identityType property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getIdentityType() {
        return identityType;
    }


    /**
     * Sets the value of the identityType property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setIdentityType(String value) {
        this.identityType = value;
    }


    /**
     * Gets the value of the quantityRequested property.
     *
     */
    public int getQuantityRequested() {
        return quantityRequested;
    }


    /**
     * Sets the value of the quantityRequested property.
     *
     */
    public void setQuantityRequested(int value) {
        this.quantityRequested = value;
    }

}
