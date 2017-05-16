/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity;

import java.util.Arrays;

/**
 * MAC address data type.
 */
public class MAC {

    private byte[] macBytes;
    private String macString;


    /**
     * Constructs a MAC address given an array of bytes. If the array is less than 6 bytes, an exception will be thrown.
     * 
     * NOTE! This does not copy the bytes in the array, it merely references it. Therefore you must make your own copy if you expect to modify this and want this object to work
     * correctly.
     *
     * @param addr array of bytes that is the MAC address
     * @throws IllegalArgumentException if the MAC address is not the appropriate length
     */
    public MAC(byte[] addr) {
        if (addr == null || addr.length < 6) {
            throw new IllegalArgumentException("The addr argument, which is a MAC address, must be at least 6 bytes in length.");
        }
        macBytes = addr.clone();
        macString = toString(addr);
    }


    /**
     * Constructs a MAC address given a string. If the string is not a valid MAC address, an exception will be thrown.
     *
     * @param addr string that is the MAC address
     * @throws NumberFormatException if the hex values cannot be parsed, or if the string is not the appropriate length
     */
    public MAC(String addr) {
        macBytes = toBytes(addr);

        // Set our string by using our toString method. This ensures
        // that it is in our cannonical format.
        //
        macString = toString(macBytes);
    }


    /**
     * Constructs a MAC address given a long numeric address.
     *
     * @param addr the addr
     */
    public MAC(long addr) {
        MAC mac = MAC.toMac(addr);
        macBytes = mac.macBytes;
        macString = mac.macString;
    }


    /**
     * Constructs a MAC address given a string OUI and a string prefix.
     *
     * @param oui the oui
     * @param prefix the prefix
     */
    public MAC(String oui, String prefix) {
        StringBuilder str = new StringBuilder(oui);
        str.append(":");
        str.append(prefix);
        str.append(":00:00");
        macBytes = toBytes(str.toString());
        macString = toString(macBytes);
    }


    /**
     * Returns the MAC address as an array of bytes.
     * 
     * NOTE! This is a reference to the internal array of bytes, so please don't modify it.
     *
     * @return array of bytes
     */
    public byte[] toBytes() {
        return (macBytes == null) ? null : macBytes.clone();
    }


    /**
     * Returns the MAC address as a string. The MAC address will be groups of two hex digits (uppercase) separated by a dash.
     * 
     * Example: 00-0A-0B-0C-0D-0F
     *
     * @return mac address string
     */
    @Override
    public String toString() {
        return macString;
    }


    /**
     * Returns the MAC address as a long numeric value.
     *
     * @param mac the MAC address as a MAC class value
     * @return the MAC address as a long numeric value
     */
    public static long toLong(MAC mac) {
        long macLong = 0;
        byte[] macBytes = mac.toBytes();
        for (int i = 0; i < macBytes.length; i++) {
            macLong = (macLong << 8) + (macBytes[i] & 0xff);
        }
        return macLong;
    }


    /**
     * Returns the MAC address by input a long numeric value.
     *
     * @param macLong the MAC address by input a long numeric value.
     * @return the MAC address as a MAC class value
     */
    public static MAC toMac(long macLong) {
        byte[] macBytes = new byte[6];
        for (int i = 0; i < 6; ++i) {
            macBytes[i] = (byte) (macLong >> (6 - i - 1 << 3));
        }
        return new MAC(macBytes);
    }


    /**
     * Takes an array of 6 bytes and converts it to a MAC address string. The MAC address will* be groups of two hex digits (uppercase) separated by a dash.
     * 
     * Example: 00-0A-0B-0C-0D-0F
     *
     * @param addr MAC address as an array of bytes
     * @return MAC address as a string
     * @throws IllegalArgumentException if the MAC address is not the appropriate length
     */
    public static String toString(byte[] addr) {
        if (addr.length < 6) {
            throw new IllegalArgumentException("The addr argument, which is a MAC address, must " + "be at least 6 bytes in length.");
        }

        // MAC address strings are 20 characters.
        StringBuilder buffer = new StringBuilder(20);
        for (int i = 0; i < 6; i++) {
            if (i > 0) {
                buffer.append(':');
            }

            if (addr[i] >= 0 && addr[i] < 0x10) {
                // prepend '0' if required...
                buffer.append('0');
            }
            buffer.append(Integer.toHexString(addr[i] & 0xFF).toUpperCase());
        }
        return buffer.toString();
    }


    /**
     * Takes a string in the form of a MAC address and turn it into an array of bytes.
     *
     * @param addr MAC address as a string
     * @return MAC address as an array of bytes
     * @throws NumberFormatException if the hex values cannot be parsed, or if the string is not the appropriate length
     */
    public static byte[] toBytes(String addr) {
        if (addr == null) {
            throw new NumberFormatException("MAC address string cannot be null");
        }

        String[] fields = addr.split("[\\:\\-\\s]");
        if (fields.length > 6) {
            throw new NumberFormatException("Cannot parse " + addr + ": MAC has more than 6 fields.");
        }

        byte[] retval = new byte[6];
        for (int i = 0; i < retval.length; i++) {
            retval[i] = 0;
        }

        for (int i = 0; i < fields.length; i++) {
            // just parse each part
            int value = Integer.parseInt(fields[i], 16);
            if (value < 0 || value > 0xFF) {
                throw new NumberFormatException("Cannot parse MAC address " + addr + ": Value out of range. Value:\"" + fields[i] + "\" Radix:16");
            }
            retval[i] = (byte) (value & 0x000000FF);
        }

        // all done. mac address parsed...
        return retval;
    }


    /**
     * Determine if this MAC is the broadcast address.
     *
     * @return true is broadcast.
     */
    public boolean isBroadcast() {
        for (int i = 0; i < macBytes.length; i++) {
            if ((macBytes[i] & 0xFF) != 0xFF) {
                return false;
            }
        }
        return true;
    }


    /**
     * Determine if this MAC is a multicast address.
     *
     * @return true is multicast.
     */
    public boolean isMulticast() {
        return (macBytes[0] & 0x01) != 0;
    }


    /**
     * Compare two MAC address. This compares the actual bytes of the MAC address. For example, the following two MAC addresses are equal even though they are created used
     * different strings:
     * 
     * MAC mac1 = new MAC("00-02-03-04-05-06"); MAC mac2 = new MAC("00:02:03:04:05:06");
     *
     * @param addr The MAC address to compare against.
     * @return false if the addr object is not a MAC object, true if the two MAC addresses are equal, false otherwise.
     */
    @Override
    public boolean equals(Object addr) {
        if (addr == null) {
            return false;
        }
        if (!(addr instanceof MAC)) {
            return false;
        }
        MAC otherAddr = (MAC) addr;
        byte[] bytes = otherAddr.toBytes();
        return Arrays.equals(macBytes, bytes);
    }


    /**
     * Since we provide a real equals method we must define a hash.
     *
     * @return a hash code for this object.
     */
    @Override
    public int hashCode() {
        // hash the actual bytes, not the string, as per equals
        return ((0xff & macBytes[2]) << 24) | ((0xff & macBytes[3]) << 16) | ((0xff & macBytes[4]) << 8) | (0xff & macBytes[5]);
    }
}
