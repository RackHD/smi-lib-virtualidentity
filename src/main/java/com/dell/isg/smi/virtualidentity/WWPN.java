/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity;

import java.util.Arrays;

import org.springframework.util.StringUtils;

public class WWPN {
    private byte[] wwpnBytes;
    private String wwpnString;


    public WWPN(String oui, String prefix, String startNum) {
        byte[] wwpnByte = new byte[8];
        wwpnByte[0] = 0x20;
        wwpnByte[1] = 0x01;
        int index = 2;
        String[] ouiString = oui.split(":");
        for (String o : ouiString) {
            int hexInt = Integer.parseInt(o, 16);
            wwpnByte[index++] = (byte) (hexInt & 0x000000FF);
        }
        int hexInt = Integer.parseInt(prefix, 16);
        wwpnByte[index++] = (byte) (hexInt & 0x000000FF);
        wwpnByte[index++] = 0x00;
        wwpnByte[index++] = 0x00;

        long wwpnLong = 0;
        for (int i = 0; i < wwpnByte.length; i++) {
            wwpnLong = (wwpnLong << 8) + (wwpnByte[i] & 0xff);
        }
        long startingNum = 0;
        if (!StringUtils.isEmpty(startNum)) {
            try {
                startingNum = Long.parseLong(startNum);
            } catch (NumberFormatException ex) {
                throw new NumberFormatException("startNum for the wwpn is not valid");
            }
        }
        wwpnLong = wwpnLong + startingNum;
        WWPN wwpn = WWPN.toWwnn(wwpnLong);

        wwpnBytes = wwpn.wwpnBytes;
        wwpnString = wwpn.wwpnString;

    }


    public static WWPN toWwnn(long wwpnLong) {

        byte[] wwpnBytes = new byte[8];

        for (int i = 0; i < 8; ++i) {
            wwpnBytes[i] = (byte) (wwpnLong >> (8 - i - 1 << 3));
        }

        return new WWPN(wwpnBytes);
    }


    public WWPN(long addr) {

        WWPN wwpn = WWPN.toWwnn(addr);

        wwpnBytes = wwpn.wwpnBytes;
        wwpnString = wwpn.wwpnString;

    }


    public WWPN(byte[] addr) {
        if (addr == null || addr.length != 8) {
            throw new IllegalArgumentException("The addr argument, which is a WWNN address, must be 8 bytes in length.");
        }

        wwpnBytes = addr.clone();
        wwpnString = toString(addr);

    }


    public static String toString(byte[] addr) {

        if (addr.length != 8) {
            throw new IllegalArgumentException("The addr argument, which is a WWPN address, must be 8 bytes in length.");
        }

        // WWPN address strings are 24 characters.
        StringBuilder buffer = new StringBuilder(24);

        for (int i = 0; i < 8; i++) {
            if (i > 0)
                buffer.append(':');

            if (addr[i] >= 0 && addr[i] < 0x10)
                // prepend '0' if required...
                buffer.append('0');

            buffer.append(Integer.toHexString(addr[i] & 0xFF).toUpperCase());
        }

        return buffer.toString();
    }


    /**
     * Takes a string in the form of a WWN address and turn it into an array of bytes.
     *
     * @param addr WWN address as a string
     * @return WWN address as an array of bytes
     * @throws NumberFormatException if the hex values cannot be parsed, or if the string is not the appropriate length
     */
    public static byte[] toBytes(String addr) {

        if (addr == null) {
            throw new NumberFormatException("WWN address string cannot be null");
        }

        String[] fields = addr.split("[\\:\\-\\s]");
        if (fields.length != 8)
            throw new NumberFormatException("Cannot parse " + addr + ": WWN must have 8 fields.");

        byte[] retval = new byte[8];
        for (int i = 0; i < retval.length; i++)
            retval[i] = 0;

        for (int i = 0; i < fields.length; i++) {

            // just parse each part
            int value = Integer.parseInt(fields[i], 16);
            if (value < 0 || value > 0xFF) {
                throw new NumberFormatException("Cannot parse WWN address " + addr + ": Value out of range. Value:\"" + fields[i] + "\" Radix:16");
            }

            retval[i] = (byte) (value & 0x000000FF);
        }

        return retval;
    }


    /**
     * Returns the WWN address as a long numeric value.
     *
     * @param wwpn the WWN address as a WWPN class value
     * @return the WWN address as a long numeric value
     */
    public static long toLong(WWPN wwpn) {

        long wwpnLong = 0;
        byte[] wwpnBytes = wwpn.toBytes();

        for (int i = 0; i < wwpnBytes.length; i++) {
            wwpnLong = (wwpnLong << 8) + (wwpnBytes[i] & 0xff);
        }

        return wwpnLong;
    }


    /**
     * Returns the WWN address as an array of bytes.
     * <p/>
     * NOTE! This is a reference to the internal array of bytes, so please don't modify it.
     *
     * @return array of bytes
     */
    public byte[] toBytes() {
        return (wwpnBytes == null) ? null : wwpnBytes.clone();
    }


    /**
     * Compare two WWPN address. This compares the actual bytes of the WWPN address. For example, the following two WWPN addresses are equal even though they are created used
     * different strings:
     * <p/>
     * WWPN wwpn1 = new WWPN("20-01-00-02-03-04-05-06"); WWPN wwpn2 = new WWPN("20:01:00:02:03:04:05:06");
     *
     * @param addr The WWPN address to compare against.
     * @return false if the addr object is not a WWPN object, true if the two WWPN addresses are equal, false otherwise.
     */
    @Override
    public boolean equals(Object addr) {
        if (addr == null)
            return false;
        if (getClass() != addr.getClass()) {
            return false;
        }
        WWPN otherAddr = (WWPN) addr;
        byte[] bytes = otherAddr.toBytes();
        return Arrays.equals(wwpnBytes, bytes);
    }


    /**
     * Since we provide a real equals method we must define a hash.
     *
     * @return a hash code for this object.
     */
    @Override
    public int hashCode() {
        // hash the actual bytes, not the string, as per equals
        return ((0xff & wwpnBytes[2]) << 24) | ((0xff & wwpnBytes[3]) << 16) | ((0xff & wwpnBytes[4]) << 8) | (0xff & wwpnBytes[5]);
    }

}
