/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dell.isg.smi.virtualidentity.IQN;
import com.dell.isg.smi.virtualidentity.MAC;
import com.dell.isg.smi.virtualidentity.WWNN;
import com.dell.isg.smi.virtualidentity.WWPN;
import com.dell.isg.smi.virtualidentity.entity.IoIdentity;
import com.dell.isg.smi.virtualidentity.entity.IoPoolType;
import com.dell.isg.smi.virtualidentity.model.IoIdentityStates;
import com.dell.isg.smi.virtualidentity.model.IoIdentityType;

/**
 * IO Identity Manager Utility.
 */
public class IoIdentityManagerUtility {

    static final Logger logger = LoggerFactory.getLogger(IoIdentityManagerUtility.class);

    static final String IQN_PATTERN = "^[0-9a-zA-Z-._]{0,101}$";
    static final String HEX_PATTERN = "^[\\da-fA-F]+$";
    static final int maxIqnPrefixLength = 212;


    /**
     * Generate MAC identity.
     *
     * @param ioIdentityType the io identity type
     * @param iCount the i count
     * @param latestIOIdentity the latest IO identity
     * @param ioIdentityOfLastMac the io identity of last mac
     * @param ioPoolType the io pool type
     * @return ioIdentities
     */
    public static List<IoIdentity> generateMACIdentity(IoIdentityType ioIdentityType, int iCount, IoIdentity latestIOIdentity, IoIdentity ioIdentityOfLastMac, IoPoolType ioPoolType) {
        logger.trace(" entered generateMACIdentity ");
        List<IoIdentity> lstDaoIOIdentity = new ArrayList<>();

        if (ioIdentityType.equals(IoIdentityType.WWPN) || ioIdentityType.equals(IoIdentityType.IQN) || ioIdentityType.equals(IoIdentityType.MAC) || ioIdentityType.equals(IoIdentityType.WWNN)) {
            MAC mac;
            if (latestIOIdentity != null && ioIdentityType.equals(IoIdentityType.MAC)) {
                mac = new MAC(latestIOIdentity.getValue());
            } else if (ioIdentityOfLastMac != null && (ioIdentityType.equals(IoIdentityType.WWNN) || ioIdentityType.equals(IoIdentityType.WWPN) || ioIdentityType.equals(IoIdentityType.IQN))) {
                mac = new MAC(ioIdentityOfLastMac.getMacAddress());
            } else {
                mac = new MAC(ioPoolType.getSeedValue());
            }

            // Generate MACs
            for (int i = 0; i < iCount; i++) {
                // Convert MAC to long and increment
                long lMac = MAC.toLong(mac);
                lMac = lMac + 1;
                mac = new MAC(lMac);

                // Create a IOIdentity business object
                IoIdentity newDaoIOIdentity = new IoIdentity();
                newDaoIOIdentity.setIoPoolType(ioPoolType);
                newDaoIOIdentity.setState(IoIdentityStates.AVAILABLE.value());

                if (ioIdentityType.equals(IoIdentityType.WWNN) || ioIdentityType.equals(IoIdentityType.WWPN) || ioIdentityType.equals(IoIdentityType.IQN)) {
                    newDaoIOIdentity.setMacAddress(mac.toString());
                } else {
                    newDaoIOIdentity.setValue(mac.toString());
                }

                // Add to list
                lstDaoIOIdentity.add(newDaoIOIdentity);
            }

        }
        logger.trace(" exited generateMACIdentity ");
        return lstDaoIOIdentity;
    }


    /**
     * Generate IQN identity for ISCSI.
     *
     * @param latestIOIdentity the latest IO identity
     * @param ioPoolType the io pool type
     * @param iSCSIIOIdentities the i SCSIIO identities
     * @return iSCSIIOIdentities
     */
    public static List<IoIdentity> generateIQNIdentityForISCSI(IoIdentity latestIOIdentity, IoPoolType ioPoolType, List<IoIdentity> iSCSIIOIdentities) {
        logger.trace(" entered generateIQNIdentityForISCSI ");
        IQN iqn;
        if (latestIOIdentity != null) {
            iqn = new IQN(latestIOIdentity.getValue());
        } else {
            iqn = new IQN(ioPoolType.getSeedValue());
        }

        // Generate IQNs
        for (IoIdentity IQNIdentity : iSCSIIOIdentities) {
            // Get the next IQN
            String nxtIQN = iqn.getNext();
            // update the ISCSI-Identity object with IQN info
            IQNIdentity.setValue(nxtIQN);
            IQNIdentity.setCustom(false);
        }
        logger.trace(" exited generateIQNIdentityForISCSI ");
        return iSCSIIOIdentities;
    }


    /**
     * Generate IQ ns by customized input.
     *
     * @param prefix the prefix
     * @param startValue the start value
     * @param iSCSIIOIdentities the i SCSIIO identities
     * @return iSCSIIOIdentities
     */
    public static List<IoIdentity> generateIQNsByCustomizedInput(String prefix, long startValue, List<IoIdentity> iSCSIIOIdentities) {
        logger.trace(" entered generateIQNsByCustomizedInput ");
        IQN iqn = null;
        if (StringUtils.hasLength(prefix) && startValue >= 0) {
            iqn = new IQN(prefix, startValue, false);
        }
        // Generate IQNs
        for (IoIdentity IQNIdentity : iSCSIIOIdentities) {
            if (iqn != null) {
                // Get the next IQN
                String nxtIQN = iqn.getNextCustomizedIqn();
                // update the ISCSI-Identity object with IQN info
                IQNIdentity.setValue(nxtIQN);
                IQNIdentity.setCustom(true);
            }
        }
        logger.trace(" exited generateIQNsByCustomizedInput ");
        return iSCSIIOIdentities;
    }


    /**
     * Generate EUI by customized input.
     *
     * @param count the count
     * @param startValue the start value
     * @param ioPoolType the io pool type
     * @param lastIOIdentity the last IO identity
     * @return ioIdentities
     */
    public static List<IoIdentity> generateEUIByCustomizedInput(int count, long startValue, IoPoolType ioPoolType, IoIdentity lastIOIdentity) {
        logger.trace(" entered generateEUIByCustomizedInput ");
        List<IoIdentity> generatedIOIdentity = new ArrayList<>();
        if (lastIOIdentity == null) {
            IQN iqn = new IQN(ioPoolType.getPrefix(), startValue, true);
            for (int i = 0; i < count; i++) {
                IQN lastIqn = new IQN(iqn.getNextCustomizedEui(), startValue, true);
                IoIdentity newDaoIOIdentity = new IoIdentity();
                newDaoIOIdentity.setIoPoolType(ioPoolType);
                newDaoIOIdentity.setValue(lastIqn.getIqn());
                newDaoIOIdentity.setCustom(true);
                newDaoIOIdentity.setIoPool(ioPoolType.getIoPool());
                newDaoIOIdentity.setState(IoIdentityStates.AVAILABLE.value());
                generatedIOIdentity.add(newDaoIOIdentity);
            }
        } else {
            IQN iqn = new IQN(lastIOIdentity.getValue(), startValue, true);
            for (int i = 0; i < count; i++) {
                IQN lastIqn = new IQN(iqn.getNextCustomizedEui(), startValue, true);
                IoIdentity newDaoIOIdentity = new IoIdentity();
                newDaoIOIdentity.setIoPoolType(ioPoolType);
                newDaoIOIdentity.setValue(lastIqn.getIqn());
                newDaoIOIdentity.setCustom(true);
                newDaoIOIdentity.setIoPool(ioPoolType.getIoPool());
                newDaoIOIdentity.setState(IoIdentityStates.AVAILABLE.value());
                generatedIOIdentity.add(newDaoIOIdentity);
            }
        }
        logger.trace(" exited generateEUIByCustomizedInput ");
        return generatedIOIdentity;
    }


    /**
     * Generate MAC.
     *
     * @param count the count
     * @param ioPoolType the io pool type
     * @param lastIOIdentity the last IO identity
     * @return ioIdentities
     */
    public static List<IoIdentity> generateMAC(int count, IoPoolType ioPoolType, IoIdentity lastIOIdentity) {

        logger.trace(" entered generateMAC ");
        // Assume prefix check is done
        // For the Global pool the random prefix would have been generated and will be passed on to this method.
        List<IoIdentity> generatedIOIdentity = new ArrayList<>();

        MAC mac;
        if (lastIOIdentity == null) {
            // We need to create MACs from oui(First 3 bytes) + prefix (4th byte)+ seedValue
            String oui = ioPoolType.getOui();
            mac = new MAC(oui, ioPoolType.getPrefix());
        } else {
            mac = new MAC(lastIOIdentity.getMacAddress());
        }
        // Generate MACs
        for (int i = 0; i < count; i++) {
            // Convert MAC to long and increment
            long lMac = MAC.toLong(mac);
            lMac = lMac + 1;
            mac = new MAC(lMac);
            IoIdentity newDaoIOIdentity = new IoIdentity();
            newDaoIOIdentity.setIoPoolType(ioPoolType);
            newDaoIOIdentity.setIoPool(ioPoolType.getIoPool());
            newDaoIOIdentity.setState(IoIdentityStates.AVAILABLE.value());
            newDaoIOIdentity.setMacAddress(mac.toString());
            newDaoIOIdentity.setValue(mac.toString());
            generatedIOIdentity.add(newDaoIOIdentity);
        }
        logger.trace(" exited generateMAC ");
        return generatedIOIdentity;
    }


    /**
     * Generate IQN.
     *
     * @param count the count
     * @param startValue the start value
     * @param ioPoolType the io pool type
     * @param lastIOIdentity the last IO identity
     * @param isEui the is eui
     * @return ioIdentities
     */
    public static List<IoIdentity> generateIQN(int count, long startValue, IoPoolType ioPoolType, IoIdentity lastIOIdentity, boolean isEui) {

        logger.trace(" entered generateIQN ");
        // Assume prefix check is done
        // For the Global pool the random prefix would have been generated and will be passed on to this method.
        List<IoIdentity> generatedIOIdentity = new ArrayList<IoIdentity>();

        IQN iqn;
        if (lastIOIdentity == null) {
            // We need to create IQN from prefix,
            startValue = startValue - 1;
            iqn = new IQN(ioPoolType.getPrefix(), startValue, isEui);
        } else {
            String lastIQNValue = lastIOIdentity.getValue();
            Long startNumber = 0L;
            if (!StringUtils.isEmpty(lastIQNValue)) {
                String prefixValues[] = lastIQNValue.split(":");
                String startNum = prefixValues[prefixValues.length - 1];
                startNum = startNum.replaceFirst("^0+(?!$)", "");
                startNumber = Long.parseLong(startNum, 16);
            }
            iqn = new IQN(ioPoolType.getPrefix(), startNumber, isEui);
        }
        for (int i = 0; i < count; i++) {
            IQN lastIqn = new IQN(iqn.getNext());
            IoIdentity newDaoIOIdentity = new IoIdentity();
            newDaoIOIdentity.setIoPoolType(ioPoolType);
            newDaoIOIdentity.setIoPool(ioPoolType.getIoPool());
            newDaoIOIdentity.setValue(lastIqn.getIqn());
            newDaoIOIdentity.setState(IoIdentityStates.AVAILABLE.value());
            generatedIOIdentity.add(newDaoIOIdentity);
        }
        logger.trace(" exited generateIQN ");
        return generatedIOIdentity;
    }


    /**
     * Generate WWNN.
     *
     * @param count the count
     * @param ioPoolType the io pool type
     * @param lastIOIdentity the last IO identity
     * @return ioIdentities
     */
    public static List<IoIdentity> generateWWNN(int count, IoPoolType ioPoolType, IoIdentity lastIOIdentity) {
        logger.trace(" entered generateWWNN ");
        List<IoIdentity> generatedIOIdentity = new ArrayList<IoIdentity>();
        WWNN wwnn;
        if (lastIOIdentity != null) {
            String wwnnString = lastIOIdentity.getValue();
            wwnn = new WWNN(WWNN.toBytes(wwnnString));
        } else {
            wwnn = new WWNN(ioPoolType.getOui(), ioPoolType.getPrefix(), "0");
        }
        for (int i = 0; i < count; i++) {
            long nextWWNN = WWNN.toLong(wwnn);
            nextWWNN = nextWWNN + 1;
            wwnn = new WWNN(nextWWNN);
            IoIdentity newIOIDIdentity = new IoIdentity();
            newIOIDIdentity.setIoPoolType(ioPoolType);
            newIOIDIdentity.setState(IoIdentityStates.AVAILABLE.value());
            newIOIDIdentity.setValue(WWNN.toString(wwnn.toBytes()));
            generatedIOIdentity.add(newIOIDIdentity);
        }
        logger.trace(" exited generateWWNN ");
        return generatedIOIdentity;
    }


    /**
     * Generate WWPN.
     *
     * @param count the count
     * @param ioPoolType the io pool type
     * @param lastIOIdentity the last IO identity
     * @return ioIdentities
     */
    public static List<IoIdentity> generateWWPN(int count, IoPoolType ioPoolType, IoIdentity lastIOIdentity) {
        logger.trace(" entered generateWWPN ");
        List<IoIdentity> generatedIOIdentity = new ArrayList<>();
        WWPN wwpn;
        if (lastIOIdentity != null) {
            String wwnnString = lastIOIdentity.getValue();
            wwpn = new WWPN(WWPN.toBytes(wwnnString));
        } else {
            wwpn = new WWPN(ioPoolType.getOui(), ioPoolType.getPrefix(), "0");
        }
        for (int i = 0; i < count; i++) {
            long nextWWPN = WWPN.toLong(wwpn);
            nextWWPN = nextWWPN + 1;
            wwpn = new WWPN(nextWWPN);
            IoIdentity newIOIdentity = new IoIdentity();
            newIOIdentity.setIoPoolType(ioPoolType);
            newIOIdentity.setIoPool(ioPoolType.getIoPool());
            newIOIdentity.setState(IoIdentityStates.AVAILABLE.value());
            newIOIdentity.setValue(WWPN.toString(wwpn.toBytes()));
            generatedIOIdentity.add(newIOIdentity);
        }
        logger.trace(" exited generateWWPN ");
        return generatedIOIdentity;
    }


    /**
     * Validate iqn pattern.
     *
     * @param input the input
     * @return true if valid iqn pattern else false
     */
    public static boolean validateIqnPattern(final String input) {
        logger.trace(" entered validateIqnPattern ");
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(IQN_PATTERN);
        matcher = pattern.matcher(input);
        logger.trace(" exited validateIqnPattern ");
        return matcher.matches();
    }


    /**
     * Check the input string is an Hexadecimal number.
     *
     * @param input the input
     * @return true if match else false
     */
    public static boolean isHex(String input) {
        logger.trace(" entered isHex ");
        boolean isHex;
        if (input.matches(HEX_PATTERN)) {
            // Valid Hexadecimal number
            isHex = true;
        } else {
            // Invalid
            isHex = false;
        }
        logger.trace(" exited isHex ");
        return isHex;
    }


    /**
     * Randomize prefix values.
     *
     * @return random prefix value
     */
    public static String randomizePrefixValues() {
        logger.trace(" entered randomizePrefixValues ");
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(255);
        logger.trace(" exited randomizePrefixValues ");
        return String.format("%02X", randomInt);
    }

}
