/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.model;

import io.swagger.annotations.ApiModel;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@ApiModel
@XmlType(name = "IoIdentityStates")
@XmlEnum
public enum IoIdentityStates {

    AVAILABLE, RESERVED, ASSIGNED, DELETED;

    public String value() {
        return name();
    }


    public static IoIdentityStates fromValue(String v) {
        return valueOf(v);
    }

}
