/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.model;

import io.swagger.annotations.ApiModel;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@ApiModel
@XmlType(name = "VirtualIdentityType")
@XmlEnum
public enum VirtualIdentityType {

    MAC, IQN, WWPN, WWNN;

    public String value() {
        return name();
    }


    public static VirtualIdentityType fromValue(String v) {
        return valueOf(v);
    }

}
