/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.model;

import org.springframework.stereotype.Component;

/**
 * The Class IoIdentityBrief.
 */
@Component
public class IoIdentityBrief {

    private long poolId;
    private String poolType;
    private String state;
    private Long count;


    /**
     * Gets the pool id.
     *
     * @return the pool id
     */
    public long getPoolId() {
        return poolId;
    }


    /**
     * Sets the pool id.
     *
     * @param poolId the new pool id
     */
    public void setPoolId(long poolId) {
        this.poolId = poolId;
    }


    /**
     * Gets the pool type.
     *
     * @return the pool type
     */
    public String getPoolType() {
        return poolType;
    }


    /**
     * Sets the pool type.
     *
     * @param poolType the new pool type
     */
    public void setPoolType(String poolType) {
        this.poolType = poolType;
    }


    /**
     * Gets the state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }


    /**
     * Sets the state.
     *
     * @param state the new state
     */
    public void setState(String state) {
        this.state = state;
    }


    /**
     * Gets the count.
     *
     * @return the count
     */
    public Long getCount() {
        return count;
    }


    /**
     * Sets the count.
     *
     * @param count the new count
     */
    public void setCount(Long count) {
        this.count = count;
    }

}
