/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.model;

import org.springframework.stereotype.Component;

@Component
public class IoIdentityBrief {

    private long poolId;

    private String poolType;

    private String state;

    private Long count;


    public long getPoolId() {
        return poolId;
    }


    public void setPoolId(long poolId) {
        this.poolId = poolId;
    }


    public String getPoolType() {
        return poolType;
    }


    public void setPoolType(String poolType) {
        this.poolType = poolType;
    }


    public String getState() {
        return state;
    }


    public void setState(String state) {
        this.state = state;
    }


    public Long getCount() {
        return count;
    }


    public void setCount(Long count) {
        this.count = count;
    }

}
