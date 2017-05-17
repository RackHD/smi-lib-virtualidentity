/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.dell.isg.smi.commons.elm.exception.RuntimeCoreException;
import com.dell.isg.smi.commons.elm.messaging.LocalizedMessage;
import com.dell.isg.smi.commons.elm.model.EnumErrorCode;

/**
 * The Class NotFoundException.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeCoreException {
    private static final long serialVersionUID = 1L;


    /**
     * Instantiates a new not found exception.
     */
    public NotFoundException() {
        super();
    }


    /**
     * Instantiates a new not found exception.
     *
     * @param e the e
     */
    public NotFoundException(Throwable e) {
        super(e);
    }


    /**
     * Instantiates a new not found exception.
     *
     * @param enumErrorCode the enum error code
     */
    public NotFoundException(EnumErrorCode enumErrorCode) {
        super(enumErrorCode);
    }


    /* (non-Javadoc)
     * @see com.dell.isg.smi.commons.elm.exception.RuntimeCoreException#getMessage()
     */
    @Override
    public String getMessage() {
        LocalizedMessage localizedMessage = generateLocalizedMessage();
        return localizedMessage.toString();
    }
}
