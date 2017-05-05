/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.model;

import io.swagger.annotations.ApiModel;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@ApiModel
@XmlType(name = "IoIdentityType", namespace = "http://pg.dell.com/asm/identitypool/common")
@XmlEnum
public enum IoIdentityType {

    MAC, IQN, // ISCSI
    WWPN, WWNN;

    public String value() {
        return name();
    }


    public static IoIdentityType fromValue(String v) {
        return valueOf(v);
    }

}
