/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.validation;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.dell.isg.smi.commons.elm.exception.BusinessValidationException;
import com.dell.isg.smi.virtualidentity.exception.EnumErrorCode;

/**
 * The Class Validator.
 */
public class Validator {

    // Name should be min 1 characters, and max 100 characters, alphanumeric and
    // can contain hyphen and underscore and space characters
    public static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9 _.()\\-]*$");

    public static final int NAME_MIN_SIZE = 1;
    public static final int NAME_MAX_SIZE = 100;
    public static final int DISPLAY_NAME_MIN_SIZE = 1;
    public static final int DISPLAY_NAME_MAX_SIZE = 255;
    public static final Pattern STORAGE_CRENTIAL_NAME_PATTERN = Pattern.compile("^[\\x20-\\x7E]{1,128}$");
    public static final Pattern STORAGE_CRENTIAL_PWD_PATTERN = Pattern.compile("^[\\x20-\\x7E]{12,16}$");
    public static final Pattern CHASSIS_ATTRIBUTE_PATTERN = Pattern.compile("^[\\x20-\\x7E]{0,127}$");
    public static final Pattern CHASSIS_NAME_PATTERN = Pattern.compile("^[\\x20-\\x7E]{0,64}$");
    public static final Pattern DNS_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9-.]{0,63}$");

    public static final Pattern HOST_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9-.]{0,32}$");
    public static final Pattern ROBUST_HOST_PATTERN = Pattern.compile("^(([a-zA-Z]|[a-zA-Z][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])$", Pattern.CASE_INSENSITIVE);
    public static final Pattern WWPN_PATTERN = Pattern.compile("([0-9a-zA-Z]{2}:){7}([0-9a-zA-Z]{2})");

    public static final String IQN_REGEX = "^(?:iqn\\.[0-9]{4}-[0-9]{2}(?:\\.[A-Za-z](?:[A-Za-z0-9\\-]*[A-Za-z0-9])?)+(?::.*)?|eui\\.[0-9A-Fa-f]{16})$";
    public static final String MAC_REGEX = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";
    public static final String WWNN_WWPN_REGEX = "^([0-9a-zA-Z]{2}:){7}([0-9a-zA-Z]{2})$";
    public static final String IP_REGEX = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String IP_REGEX_WC = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]|\\*)\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]|\\*)$";
    public static final Pattern IQN_PATTERN = Pattern.compile(IQN_REGEX);
    public static final Pattern MAC_PATTERN = Pattern.compile(MAC_REGEX);
    public static final Pattern WWNN_WWPN_PATTERN = Pattern.compile(WWNN_WWPN_REGEX);
    public static final Pattern IP_PATTERN = Pattern.compile(IP_REGEX);
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    public static final Pattern IP_PATTERN_WC = Pattern.compile(IP_REGEX_WC);

    public static final Pattern STORAGE_EQUALLOGIC_VOLUME_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9-]{1,63}$");
    public static final Pattern STORAGE_VOLUME_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9- ]{1,63}$");
    public static final Pattern STORAGE_NETAPP_VOLUME_NAME_PATTERN = Pattern.compile("^[a-zA-Z][A-Za-z0-9_]{1,150}$");
    public static final Pattern STORAGE_EQUALLOGIC_VOLUME_SIZE_PATTERN = Pattern.compile("^\\d+(MB|GB|TB)$");
    public static final Pattern STORAGE_COMPELLENT_VOLUME_SIZE_PATTERN = Pattern.compile("^\\d+(KB|MB|GB|TB)$");
    public static final Pattern STORAGE_PERCENTAGE_PATTERN = Pattern.compile("^\\d+[%]$");
    public static final Pattern STORAGE_CHAP_USERNAME = Pattern.compile("^[\\w-]{1,63}$");
    public static final Pattern STORAGE_CHAP_PASSWORD = Pattern.compile("[\\S]{0,254}$");
    public static final Pattern STORAGE_SERVER_FOLDER_NAME = Pattern.compile("^[\\w\\s\\-]*$");
    public static final Pattern STORAGE_SERVER_WWN = Pattern.compile("^[\\w,]*$");
    // Description could be up to 300 chars including null. Alphanumeric, hyphen, space and underscore characters allowed.
    public static final Pattern DESCRIPTION_PATTERN = Pattern.compile("^^[\\p{Print}]*$");
    public static final int DESCRIPTION_MAX_SIZE = 300;

    // user name length limitations in CMC
    public static final int USERNAME_MAX_SIZE = 16;

    // acsii printable characters NOT including < > ' @ <space> / \ .
    public static final Pattern IDRAC_USERNAME_PATTERN = Pattern.compile("^([\\x21-\\x26]|[\\x28-\\x2D]|[\\x30-\\x3B]|\\x3D|\\x3F|[\\x41-\\x5B]|[\\x5D-\\x7E])+$");
    public static final Pattern IDRAC_PASSWORD_PATTERN = Pattern.compile("^[\\x21-\\x7E]+$");

    // regex matches Unicode alpha-numeric characters and special characters "_()-."
    private static final Pattern I18N_NAME_PATTERN = Pattern.compile("^[\\p{L}\\p{Nd} \\._()-]+$");
    // regex matches Unicode alpha-numeric characters and special characters "_()-."
    private static final Pattern I18N_DISPLAY_NAME_PATTERN = Pattern.compile("^[\\p{L}\\p{Nd} \\._()-]+$");
    // regex matches all unicode visible characters and spaces (i.e. anything except control characters, etc.)
    private static final Pattern I18N_DESCRIPTION_PATTERN = Pattern.compile("^[\\P{C} \\r\\n]*$");

    // regex matches Unicode alpha-numeric characters and special characters "_()-."
    private static final Pattern I18N_TEMPLATE_NAME_PATTERN = Pattern.compile("^[\\p{L}\\p{Nd} \\._()-]+$");
    // regex matches all unicode visible characters and spaces (i.e. anything except control characters, etc.)
    private static final Pattern I18N_TEMPLATE_DESCR_PATTERN = Pattern.compile("^[\\P{C} \\r\\n]*$");

    private Validator() {
    }
    
    /**
     * Validate description.
     *
     * @param description the description
     */
    public static void validateDescription(String description) {
        boolean isDescriptionLengthValid = StringUtils.isEmpty(description) || (description != null && description.length() <= Validator.DESCRIPTION_MAX_SIZE);
        boolean isDescriptionValid = (description == null) || Validator.isLocalisedDescriptionValid(description);
        if (!isDescriptionLengthValid) {
            throw new BusinessValidationException(EnumErrorCode.INVALID_DESC_LENGTH_CODE);
        }
        if (!isDescriptionValid) {
            throw new BusinessValidationException(EnumErrorCode.INVALID_DESC_FORMAT_CODE);
        }
    }


    /**
     * Checks if is name valid.
     *
     * @param name the name
     * @return true, if is name valid
     */
    public static boolean isNameValid(String name) {
        return Validator.isMatch(name, NAME_PATTERN);
    }


    /**
     * Checks if is localised name valid.
     *
     * @param name the name
     * @return true, if is localised name valid
     */
    public static boolean isLocalisedNameValid(String name) {
        return Validator.isMatch(name, I18N_NAME_PATTERN);
    }


    /**
     * Checks if is localised display name valid.
     *
     * @param displayName the display name
     * @return true, if is localised display name valid
     */
    public static boolean isLocalisedDisplayNameValid(String displayName) {
        return Validator.isMatch(displayName, I18N_DISPLAY_NAME_PATTERN);
    }


    /**
     * Checks if is localised template name valid.
     *
     * @param name the name
     * @return true, if is localised template name valid
     */
    public static boolean isLocalisedTemplateNameValid(String name) {
        return Validator.isMatch(name, I18N_TEMPLATE_NAME_PATTERN);
    }


    /**
     * Checks if is localised template descr valid.
     *
     * @param description the description
     * @return true, if is localised template descr valid
     */
    public static boolean isLocalisedTemplateDescrValid(String description) {
        return Validator.isMatch(description, I18N_TEMPLATE_DESCR_PATTERN);
    }


    /**
     * Checks if is dns name valid.
     *
     * @param dnsName the dns name
     * @return true, if is dns name valid
     */
    public static boolean isDnsNameValid(String dnsName) {
        return Validator.isMatch(dnsName, DNS_NAME_PATTERN);
    }


    /**
     * Checks if is description valid.
     *
     * @param description the description
     * @return true, if is description valid
     */
    public static boolean isDescriptionValid(String description) {
        return Validator.isMatch(description, DESCRIPTION_PATTERN);
    }


    /**
     * Checks if is localised description valid.
     *
     * @param description the description
     * @return true, if is localised description valid
     */
    public static boolean isLocalisedDescriptionValid(String description) {
        return Validator.isMatch(description, I18N_DESCRIPTION_PATTERN);
    }


    /**
     * Checks if is match.
     *
     * @param toValidate the to validate
     * @param toMatchAgainst the to match against
     * @return true, if is match
     */
    public static boolean isMatch(String toValidate, Pattern toMatchAgainst) {
        return toValidate != null && toMatchAgainst.matcher(toValidate).matches();
    }


    /**
     * Checks if is storage credential name valid.
     *
     * @param name the name
     * @return true, if is storage credential name valid
     */
    public static boolean isStorageCredentialNameValid(String name) {
        return Validator.isMatch(name, STORAGE_CRENTIAL_NAME_PATTERN);
    }


    /**
     * Checks if is storage credential password valid.
     *
     * @param name the name
     * @return true, if is storage credential password valid
     */
    public static boolean isStorageCredentialPasswordValid(String name) {
        return Validator.isMatch(name, STORAGE_CRENTIAL_PWD_PATTERN);
    }


    /**
     * Checks if is storage volume name valid.
     *
     * @param name the name
     * @return true, if is storage volume name valid
     */
    public static boolean isStorageVolumeNameValid(String name) {
        return Validator.isMatch(name, STORAGE_VOLUME_NAME_PATTERN);
    }


    /**
     * Checks if is net app storage volume name valid.
     *
     * @param name the name
     * @return true, if is net app storage volume name valid
     */
    public static boolean isNetAppStorageVolumeNameValid(String name) {
        return Validator.isMatch(name, STORAGE_NETAPP_VOLUME_NAME_PATTERN);
    }


    /**
     * Checks if is equallogic storage volume name valid.
     *
     * @param name the name
     * @return true, if is equallogic storage volume name valid
     */
    public static boolean isEquallogicStorageVolumeNameValid(String name) {
        return Validator.isMatch(name, STORAGE_EQUALLOGIC_VOLUME_NAME_PATTERN);
    }


    /**
     * Checks if is equallogic volume size valid.
     *
     * @param size the size
     * @return true, if is equallogic volume size valid
     */
    public static boolean isEquallogicVolumeSizeValid(String size) {
        return Validator.isMatch(size, STORAGE_EQUALLOGIC_VOLUME_SIZE_PATTERN);
    }


    /**
     * Checks if is compellent volume size valid.
     *
     * @param size the size
     * @return true, if is compellent volume size valid
     */
    public static boolean isCompellentVolumeSizeValid(String size) {
        return Validator.isMatch(size, STORAGE_COMPELLENT_VOLUME_SIZE_PATTERN);
    }


    /**
     * Checks if is valid percentage.
     *
     * @param percent the percent
     * @return true, if is valid percentage
     */
    public static boolean isValidPercentage(String percent) {
        boolean result = Validator.isMatch(percent, STORAGE_PERCENTAGE_PATTERN);
        if (result) {
            int percentInt = Integer.parseInt(percent.substring(0, percent.length() - 1));
            if (percentInt < 0 || percentInt > 100) {
                result = false;
            }
        }
        return result;
    }


    /**
     * Checks if is valid CHAP username.
     *
     * @param userName the user name
     * @return true, if is valid CHAP username
     */
    public static boolean isValidCHAPUsername(String userName) {
        return Validator.isMatch(userName, STORAGE_CHAP_USERNAME);
    }


    /**
     * Checks if is valid CHAP password.
     *
     * @param password the password
     * @return true, if is valid CHAP password
     */
    public static boolean isValidCHAPPassword(String password) {
        return Validator.isMatch(password, STORAGE_CHAP_PASSWORD);
    }


    /**
     * Checks if is valid folder name.
     *
     * @param folderName the folder name
     * @return true, if is valid folder name
     */
    public static boolean isValidFolderName(String folderName) {
        return Validator.isMatch(folderName, STORAGE_SERVER_FOLDER_NAME);
    }


    /**
     * Checks if is valid server WWN.
     *
     * @param wwn the wwn
     * @return true, if is valid server WWN
     */
    public static boolean isValidServerWWN(String wwn) {
        return Validator.isMatch(wwn, STORAGE_SERVER_WWN);
    }


    /**
     * Checks if is valid IP address.
     *
     * @param ipAddress the ip address
     * @return true, if is valid IP address
     */
    public static boolean isValidIPAddress(String ipAddress) {
        return Validator.isMatch(ipAddress, IP_PATTERN);
    }


    /**
     * Checks if is valid IP address wildcard.
     *
     * @param ipAddress the ip address
     * @return true, if is valid IP address wildcard
     */
    public static boolean isValidIPAddressWildcard(String ipAddress) {
        return Validator.isMatch(ipAddress, IP_PATTERN_WC);
    }


    /**
     * Checks if is valid IQN.
     *
     * @param iqn the iqn
     * @return true, if is valid IQN
     */
    public static boolean isValidIQN(String iqn) {
        return Validator.isMatch(iqn, IQN_PATTERN);
    }


    /**
     * Validate name.
     *
     * @param name the name
     */
    public static void validateName(String name) {

        boolean isNameLengthValid = StringUtils.hasLength(name) && name.length() >= NAME_MIN_SIZE && name.length() <= NAME_MAX_SIZE;
        boolean isNameValid = StringUtils.hasLength(name) && isLocalisedNameValid(name);
        // Throw corresponding exceptions based on the error encountered.
        if (!isNameLengthValid) {
            BusinessValidationException businessValidationException = new BusinessValidationException(EnumErrorCode.INVALID_NAME_LENGTH_CODE);
            businessValidationException.addAttribute(name);
            throw businessValidationException;
        } else if (!isNameValid) {
            BusinessValidationException businessValidationException = new BusinessValidationException(EnumErrorCode.INVALID_NAME_FORMAT_CODE);
            businessValidationException.addAttribute(name);
            throw businessValidationException;
        }
    }


    /**
     * Validate display name.
     *
     * @param displayName the display name
     */
    public static void validateDisplayName(String displayName) {

        boolean isNameLengthValid = StringUtils.hasLength(displayName) && displayName.length() >= DISPLAY_NAME_MIN_SIZE && displayName.length() <= DISPLAY_NAME_MAX_SIZE;
        boolean isNameValid = StringUtils.hasLength(displayName) && isLocalisedDisplayNameValid(displayName);
        // Throw corresponding exceptions based on the error encountered.
        if (!isNameLengthValid) {
            BusinessValidationException businessValidationException = new BusinessValidationException(EnumErrorCode.INVALID_DISPLAY_NAME_LENGTH_CODE);
            businessValidationException.addAttribute(displayName);
            throw businessValidationException;
        } else if (!isNameValid) {
            BusinessValidationException businessValidationException = new BusinessValidationException(EnumErrorCode.INVALID_DISPLAY_NAME_FORMAT_CODE);
            businessValidationException.addAttribute(displayName);
            throw businessValidationException;
        }
    }


    /**
     * Validate localised name.
     *
     * @param name the name
     */
    public static void validateLocalisedName(String name) {
        boolean isNameLengthValid = !StringUtils.isEmpty(name) && name.length() >= NAME_MIN_SIZE && name.length() <= NAME_MAX_SIZE;
        boolean isNameValid = !StringUtils.isEmpty(name) && isLocalisedNameValid(name);

        if (!isNameLengthValid || !isNameValid) {
            throw new IllegalArgumentException();
        }
    }

}
