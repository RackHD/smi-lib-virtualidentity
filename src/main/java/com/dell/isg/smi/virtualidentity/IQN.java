/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * IQN data type.
 */
public class IQN {

    private static final String IQN_BASE = "iqn";
    private static final String COM_DELL = "com.dell";
    private static final String SKYHAWK = "skyhawk";
    private static final String ENTERPRISE = "software";
    private static final String DATE = "1988-11";

    //
    // Member fields
    //
    private String iqnValue;
    private String iqnPrefix;
    private String iqnSuffix;
    private long uniqueId;
    private int systemId;


    /**
     * Constructor of IQN with systemId and uniqueId
     *
     * @param systemId
     * @param uniqueId
     */
    public IQN(int systemId, long uniqueId) {

        String systemIdStr = Integer.toHexString(systemId).toUpperCase();
        String uniqueIdStr = Long.toHexString(uniqueId).toUpperCase();

        // Adding leading "0"s for the systemId and uniqueId string
        systemIdStr = String.format("%2s", systemIdStr);
        uniqueIdStr = String.format("%10s", uniqueIdStr);

        systemIdStr = systemIdStr.replace(" ", "0");
        uniqueIdStr = uniqueIdStr.replace(" ", "0");

        // Set the members
        this.setIqnPrefix(IQN_BASE.concat(".").concat(DATE).concat(".").concat(COM_DELL));
        this.setIqnSuffix(ENTERPRISE.concat("-").concat(SKYHAWK).concat("-").concat(systemIdStr).concat("-").concat(uniqueIdStr));
        this.setUniqueId(uniqueId);
        this.setSystemId(systemId);

        this.setIqn(this.iqnPrefix.concat(":").concat(this.iqnSuffix));
    }


    /**
     * Constructor of IQN with IQN string
     *
     * @param iqn
     */
    public IQN(String iqn) {

        // Split the iqn string into prefix and suffix
        this.setIqn(iqn);
        String[] iqnArray = iqn.split(":");

        this.setIqnPrefix(iqnArray[0]);
        this.setIqnSuffix(iqnArray[1]);

        // Split the suffix to acquire the systemId and uniqueId
        String[] iqnSuffixArray = iqnArray[1].split("-");
        String uniqueStr = iqnSuffixArray[iqnSuffixArray.length - 1];
        String systemIdStr = iqnSuffixArray[iqnSuffixArray.length - 2];

        // Remove the padding "0"s and set members
        uniqueStr = uniqueStr.replaceFirst("^0+(?!$)", "");
        this.setUniqueId(Long.parseLong(uniqueStr, 16));

        systemIdStr = systemIdStr.replaceFirst("^0+(?!$)", "");
        this.setSystemId(Integer.parseInt(systemIdStr));
    }


    /**
     * Constructor of IQN with the user customized prefix and starting number
     * 
     * @param prefix
     * @param startingNum
     * @param isEui
     */
    public IQN(String prefix, long startingNum, boolean isEui) {

        // Generate the customized EUI identity
        if (isEui) {

            String euiStr = null;

            this.setUniqueId(startingNum);

            // Convert to Hex string for the starting number
            String uniqueIdStr = Long.toHexString(uniqueId).toUpperCase();

            // Split the EUI prefix to get the company id
            String[] prefixStrings = prefix.split("\\.");

            // Right padding of the company id with zeros to 16 characters
            String companyStr = rightPad(prefixStrings[1], 16);
            companyStr = companyStr.replace(" ", "0");

            // Adding the starting number to the company id to make the EUI identity
            Long eui = Long.parseLong(companyStr, 16) + Long.parseLong(uniqueIdStr, 16);
            euiStr = prefixStrings[0].concat(".").concat(Long.toHexString(eui).toUpperCase());

            // Set all the other fields of IQN instance
            this.setIqn(euiStr);
            this.setIqnPrefix(prefix);
            this.setIqnSuffix(uniqueIdStr);
            this.setSystemId(0);

        } else {

            // Generate the customized IQN identity
            String iqnStr = null;

            this.setUniqueId(startingNum);

            // Make sure the starting number to be a decimal number
            String uniqueIdStr = Long.toString(startingNum);
            Long longValue = Long.parseLong(uniqueIdStr, 10);
            uniqueIdStr = Long.toString(longValue);

            int length = prefix.length();

            if (prefix.charAt(length - 1) == '-' || prefix.charAt(length - 1) == ':') {
                // if the last character is '-' or ':',
                // concatenate the starting number directly
                iqnStr = prefix.concat(uniqueIdStr);
                this.setIqnPrefix(prefix);
            } else if (prefix.charAt(length - 1) == '.' || prefix.charAt(length - 1) == '_') {
                // if the last character is '.' or '_',
                // replace it with '-', then concatenate the starting number
                iqnStr = prefix.substring(0, length - 1).concat("-").concat(uniqueIdStr);
                this.setIqnPrefix(prefix);
            } else {
                // For other case, concatenate the starting number directly with '-'
                iqnStr = prefix.concat("-").concat(uniqueIdStr);
                this.setIqnPrefix(prefix);
            }

            // Set all the other fields of IQN instance
            this.setIqn(iqnStr);
            this.setSystemId(0);
            this.setIqnSuffix(uniqueIdStr);
        }
    }


    /**
     * getNext always returns the next available IQN string
     *
     * @return String
     */
    public String getNext() {

        // Unique Id need increment of one
        long uniqueIdNew = this.getUniqueId();
        uniqueIdNew++;

        // Format it with leading "0"s
        String uniqueIdStrNext = Long.toHexString(uniqueIdNew).toUpperCase();
        uniqueIdStrNext = String.format("%10s", uniqueIdStrNext);
        uniqueIdStrNext = uniqueIdStrNext.replace(" ", "0");

        // Set the members
        this.setUniqueId(uniqueIdNew);
        this.setIqnPrefix(this.getIqnPrefix());
        this.setSystemId(this.getSystemId());

        // Replace the unique Id with the next unique one
        StringBuilder iqnSuffixBuilder = new StringBuilder(this.getIqnSuffix());
        iqnSuffixBuilder.replace(iqnSuffixBuilder.lastIndexOf("-") + 1, iqnSuffixBuilder.length(), uniqueIdStrNext);
        this.setIqnSuffix(iqnSuffixBuilder.toString());

        String iqn = this.getIqnPrefix().concat(":").concat(this.getIqnSuffix());
        this.setIqn(iqn);

        return iqn;
    }


    /**
     * getNextCustomizedIqn always returns the next available IQN string based on user customized input
     *
     * @return String
     */
    public String getNextCustomizedIqn() {

        // Starting number needs increment of one
        long uniqueIdNew = this.getUniqueId();
        uniqueIdNew++;

        String uniqueIdStrNext = Long.toString(uniqueIdNew);

        // Set the members
        this.setUniqueId(uniqueIdNew);
        this.setIqnPrefix(this.getIqnPrefix());
        this.setSystemId(this.getSystemId());

        String prefix = this.getIqnPrefix();
        int length = prefix.length();

        if (prefix.charAt(length - 1) == '-' || prefix.charAt(length - 1) == ':') {
            iqnValue = prefix.concat(uniqueIdStrNext);
            this.setIqnPrefix(prefix);
        } else if (prefix.charAt(length - 1) == '.' || prefix.charAt(length - 1) == '_') {
            iqnValue = prefix.substring(0, length - 1).concat("-").concat(uniqueIdStrNext);
            this.setIqnPrefix(prefix);
        } else {
            iqnValue = prefix.concat("-").concat(uniqueIdStrNext);
            this.setIqnPrefix(prefix);
        }

        this.setIqn(iqnValue);

        return iqnValue;
    }


    /**
     * getNextCustomizedEui always returns the next available EUI string based on user customized input
     *
     * @return String
     */
    public String getNextCustomizedEui() {

        // Starting number needs increment of one
        long uniqueIdNew = this.getUniqueId();
        uniqueIdNew++;

        String uniqueIdStr = Long.toHexString(uniqueIdNew).toUpperCase();

        String prefix = this.getIqnPrefix();
        String[] prefixStrings = prefix.split("\\.");

        String companyStr = rightPad(prefixStrings[1], 16);
        companyStr = companyStr.replace(" ", "0");

        Long eui = Long.parseLong(companyStr, 16) + Long.parseLong(uniqueIdStr, 16);
        String euiStr = prefixStrings[0].concat(".").concat(Long.toHexString(eui).toUpperCase());

        this.setIqn(euiStr);
        this.setUniqueId(uniqueIdNew);
        this.setIqnPrefix(prefix);
        this.setIqnSuffix(uniqueIdStr);
        this.setSystemId(0);

        return iqnValue;
    }


    /**
     * get current date in "yyyy-MM" format
     *
     * @return String
     */
    public String getDate() {

        // Construct the date prefix
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        String dateStr = dateFormat.format(date);
        return dateStr.substring(0, dateStr.indexOf('-', dateStr.indexOf('-') + 1));
    }


    /**
     * Right padding of spaces for a string
     *
     * @param s
     * @param n
     * @return String
     */
    public String rightPad(String s, int n) {
        return String.format("%s$-%ds", s, n);
    }


    /**
     * @return the iqn
     */
    public String getIqn() {
        return iqnValue;
    }


    /**
     * @param iqn the iqn to set
     */
    public void setIqn(String iqn) {
        this.iqnValue = iqn;
    }


    /**
     * @return the iqnPrefix
     */
    public String getIqnPrefix() {
        return iqnPrefix;
    }


    /**
     * @param iqnPrefix the iqnPrefix to set
     */
    public void setIqnPrefix(String iqnPrefix) {
        this.iqnPrefix = iqnPrefix;
    }


    /**
     * @return the iqnSuffix
     */
    public String getIqnSuffix() {
        return iqnSuffix;
    }


    /**
     * @param iqnSuffix the iqnSuffix to set
     */
    public void setIqnSuffix(String iqnSuffix) {
        this.iqnSuffix = iqnSuffix;
    }


    /**
     * @return the uniqueId
     */
    public long getUniqueId() {
        return uniqueId;
    }


    /**
     * @param uniqueId the uniqueId to set
     */
    public void setUniqueId(long uniqueId) {
        this.uniqueId = uniqueId;
    }


    /**
     * @return the systemId
     */
    public int getSystemId() {
        return systemId;
    }


    /**
     * @param systemId the systemId to set
     */
    public void setSystemId(int systemId) {
        this.systemId = systemId;
    }
}
