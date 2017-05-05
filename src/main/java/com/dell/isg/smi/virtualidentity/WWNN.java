/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity;

import java.util.Arrays;

import org.springframework.util.StringUtils;

public class WWNN {
    private byte[] wwnnBytes;
    private String wwnnString;


    public WWNN(String oui, String prefix, String startNum) {
        byte[] wwnnByte = new byte[8];
        wwnnByte[0] = 0x20;
        wwnnByte[1] = 0x00;
        int index = 2;
        String[] ouiString = oui.split(":");
        for (String o : ouiString) {
            int hexInt = Integer.parseInt(o, 16);
            wwnnByte[index++] = (byte) (hexInt & 0x000000FF);
        }
        int hexInt = Integer.parseInt(prefix, 16);
        wwnnByte[index++] = (byte) (hexInt & 0x000000FF);
        wwnnByte[index++] = 0x00;
        wwnnByte[index++] = 0x00;

        long wwnnLong = 0;
        for (int i = 0; i < wwnnByte.length; i++) {
            wwnnLong = (wwnnLong << 8) + (wwnnByte[i] & 0xff);
        }
        long startingNum = 0;
        if (!StringUtils.isEmpty(startNum)) {
            try {
                startingNum = Long.parseLong(startNum);
            } catch (NumberFormatException ex) {
                throw new NumberFormatException("startNum for the wwnn is not valid");
            }
        }
        wwnnLong = wwnnLong + startingNum;
        WWNN wwnn = WWNN.toWwnn(wwnnLong);

        wwnnBytes = wwnn.wwnnBytes;
        wwnnString = wwnn.wwnnString;

    }


    public static WWNN toWwnn(long wwnnLong) {

        byte[] wwnnBytes = new byte[8];

        for (int i = 0; i < 8; ++i) {
            wwnnBytes[i] = (byte) (wwnnLong >> (8 - i - 1 << 3));
        }

        return new WWNN(wwnnBytes);
    }


    public WWNN(long addr) {

        WWNN wwnn = WWNN.toWwnn(addr);

        wwnnBytes = wwnn.wwnnBytes;
        wwnnString = wwnn.wwnnString;

    }


    public WWNN(byte[] addr) {
        if (addr.length != 8) {
            throw new IllegalArgumentException("The addr argument, which is a WWNN address, must be 8 bytes in length.");
        }

        wwnnBytes = addr.clone();
        wwnnString = toString(addr);

    }


    public static String toString(byte[] addr) {

        if (addr.length != 8) {
            throw new IllegalArgumentException("The addr argument, which is a WWNN address, must be 8 bytes in length.");
        }

        // WWNN address strings are 24 characters.
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
     * @param wwnn the WWN address as a WWNN Class value
     * @return the WWN address as a long numeric value
     */
    public static long toLong(WWNN wwnn) {

        long wwnnLong = 0;
        byte[] wwnnBytes = wwnn.toBytes();

        for (int i = 0; i < wwnnBytes.length; i++) {
            wwnnLong = (wwnnLong << 8) + (wwnnBytes[i] & 0xff);
        }

        return wwnnLong;
    }


    /**
     * Returns the WWN address as an array of bytes.
     * <p/>
     * NOTE! This is a reference to the internal array of bytes, so please don't modify it.
     *
     * @return array of bytes
     */
    public byte[] toBytes() {
        return (wwnnBytes == null) ? null : wwnnBytes.clone();
    }


    /**
     * Compare two WWNN address. This compares the actual bytes of the WWNN address. For example, the following two WWNN addresses are equal even though they are created used
     * different strings:
     * <p/>
     * WWNN wwnn1 = new WWNN("20-01-00-02-03-04-05-06"); WWNN wwnn2 = new WWNN("20:01:00:02:03:04:05:06");
     *
     * @param addr The WWNN address to compare against.
     * @return false if the addr object is not a WWNN object, true if the two WWNN addresses are equal, false otherwise.
     */
    @Override
    public boolean equals(Object addr) {
        if (addr == null)
            return false;
        if (getClass() != addr.getClass())
            return false;
        WWNN otherAddr = (WWNN) addr;
        byte[] bytes = otherAddr.toBytes();
        return Arrays.equals(wwnnBytes, bytes);
    }


    /**
     * Since we provide a real equals method we must define a hash.
     *
     * @return a hash code for this object.
     */
    @Override
    public int hashCode() {
        // hash the actual bytes, not the string, as per equals
        return ((0xff & wwnnBytes[2]) << 24) | ((0xff & wwnnBytes[3]) << 16) | ((0xff & wwnnBytes[4]) << 8) | (0xff & wwnnBytes[5]);
    }

}
