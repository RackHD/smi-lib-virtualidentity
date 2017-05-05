/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@ApiModel(value = "AssignIdentities", description = "A data transfer object for containing the usageid and a list of virtual identities to be assigned")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "usageId", "virtualIdentities" })
@XmlRootElement(name = "AssignIdentities")
public class AssignIdentities {

    @ApiModelProperty(example = "A1B2C3D")
    protected String usageId;

    @XmlElement(required = true)
    protected List<String> virtualIdentities;


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
     * Gets the value of the virtualIdentities property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the virtualIdentities property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getVirtualIdentities().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link String }
     *
     *
     */
    public List<String> getVirtualIdentities() {
        if (virtualIdentities == null) {
            virtualIdentities = new ArrayList<String>();
        }
        return this.virtualIdentities;
    }

}
