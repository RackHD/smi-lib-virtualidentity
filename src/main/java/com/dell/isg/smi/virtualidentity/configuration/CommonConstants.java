/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.virtualidentity.configuration;

/**
 * The Class CommonConstants.
 */
public class CommonConstants {

    String DEFAULT_CLIENT_LOCALE = "en_US";

    String UNKNOWN_USER = "UNKNOWN";

    String DEFAULT_USER = "root";

    String SYSTEM_USER = "system";

    Long DEFAULT_USER_ID = 1L;

    String TRUST_STORE_PROPERTY = "truststore";
    String SSL_PROPERTIES_FILE = "SSL.properties";
    String DIGEST_ALGORITHM = "SHA1";
    String APPLIANCE_EVENT_TRAP_CONFIG_FILE = "EventTrapConfiguration.properties";
    String CONTROLLER_DOMAIN = "domain";
    String SNMP_TRAP_FORMAT = "snmp_trap_format";

    long FIVE_SEC = 5000;
    long TEN_SEC = 10000;
    long FIFTEEN_SEC = 15000;
    long TWENTY_SEC = 20000;
    long THIRTY_SEC = 30000;
    long ONE_MIN = 60000;
    long TWO_MIN = 120000;
    long THREE_MIN = 180000;
    long FOUR_MIN = 240000;
    long FIVE_MIN = 300000;
    long TEN_MIN = 600000;
    long FIFTEEN_MIN = 900000;

    String DEVICE_ACTION_READ = "DEVICE_ACTION_READ";
    String DEVICE_ACTION_WRITE = "DEVICE_ACTION_WRITE";
    String INVENTORY = "INVENTORY";
    String READ_USER_SELF = "READ_USER_SELF";
    String MODIFY_USER_ANY = "MODIFY_USER_ANY";
    String MODIFY_USER_SELF = "MODIFY_USER_SELF";
    String READ_SYSTEM_INFO = "READ_SYSTEM_INFO";
    String WRITE_SYSTEM_INFO = "WRITE_SYSTEM_INFO";
    String WRITE_CONTROLLER_CONFIG = "WRITE_CONTROLLER_CONFIG";
    String READ_CONTROLLER_CONFIG = "READ_CONTROLLER_CONFIG";
    String READ_VOLUME = "READ_VOLUME";
    String WRITE_VOLUME = "WRITE_VOLUME";
    String CREATE_USER = "CREATE_USER";
    String DELETE_USER = "DELETE_USER";

}
