/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.model;

import io.swagger.annotations.ApiModel;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * The Enum VirtualIdentityStateType.
 */
@ApiModel
@XmlType(name = "VirtualIdentityStateType")
@XmlEnum
public enum VirtualIdentityStateType {

    AVAILABLE, RESERVED, ASSIGNED, ALL;

    /**
     * Value.
     *
     * @return the string
     */
    public String value() {
        return name();
    }


    /**
     * From value.
     *
     * @param v the v
     * @return the virtual identity state type
     */
    public static VirtualIdentityStateType fromValue(String v) {
        return valueOf(v);
    }

}
