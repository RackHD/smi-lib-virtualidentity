/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.model;

import io.swagger.annotations.ApiModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@ApiModel
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Link")
public class Link {

    @XmlAttribute(name = "title", required = true)
    protected String title;
    @XmlAttribute(name = "href", required = true)
    protected String href;
    @XmlAttribute(name = "rel", required = true)
    protected String rel;
    @XmlAttribute(name = "type")
    protected String type;


    /**
     * Gets the value of the title property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTitle() {
        return title;
    }


    /**
     * Sets the value of the title property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTitle(String value) {
        this.title = value;
    }


    /**
     * Gets the value of the href property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getHref() {
        return href;
    }


    /**
     * Sets the value of the href property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setHref(String value) {
        this.href = value;
    }


    /**
     * Gets the value of the rel property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getRel() {
        return rel;
    }


    /**
     * Sets the value of the rel property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setRel(String value) {
        this.rel = value;
    }


    /**
     * Gets the value of the type property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getType() {
        return type;
    }


    /**
     * Sets the value of the type property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setType(String value) {
        this.type = value;
    }

}
