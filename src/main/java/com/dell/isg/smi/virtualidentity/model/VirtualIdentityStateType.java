/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.model;

import io.swagger.annotations.ApiModel;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@ApiModel
@XmlType(name = "VirtualIdentityStateType")
@XmlEnum
public enum VirtualIdentityStateType {

    AVAILABLE, RESERVED, ASSIGNED, ALL;

    public String value() {
        return name();
    }


    public static VirtualIdentityStateType fromValue(String v) {
        return valueOf(v);
    }

}
