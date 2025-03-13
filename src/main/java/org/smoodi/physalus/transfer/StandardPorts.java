package org.smoodi.physalus.transfer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StandardPorts {

    // Well Known Port; 0 ~ 1023
    FTP_TRANSPORT(20, "FTP"),
    FTP_DEFAULT(21, "FTP"),
    SSH(22, "SSH"),
    TELNET(23, "TELNET"),
    SMTP(25, "SMTP"),
    DNS(53, "DNS"),
    DHCP(67, "DHCP"),
    TFTP(69, "TFTP"),
    HTTP(80, "HTTP"),
    HTTP_(8080, "HTTP"),
    POP2(109, "POP2"),
    POP3(110, "POP3"),
    RPC(111, "RPC"),
    NTP(123, "NTP"),
    NETBIOS(139, "NETBIOS"),
    IMAP4(143, "IMAP4"),
    SNMP_AGENT(161, "SNMP_AGENT"),
    SNMP_MANAGER(162, "SNMP_MANAGER"),
    IRC(194, "IRC"),
    HTTPS(443, "HTTPS"),
    SMB(445, "SMB"),
    IMAP4S(993, "IMAP4S"),

    // Registered Port; 1024 ~ 49151
    INFOMIX(1526, "INFOMIX"),
    DERBY(1527, "DERBY"),
    MYSQL(3306, "MYSQL"),
    MARIADB(3306, "MARIADB"),
    SYBASE(5000, "SYBASE"),
    POSTGRESQL(5432, "POSTGRESQL"),
    COUCHDB(5984, "COUCHDB"),
    REDIS(6379, "REDIS"),
    MONGODB(27017, "MONGODB"),
    MONGODB_SHARD(27018, "MONGODB_SHARD"),
    MONGODB_CONFIG(27019, "MONGODB_CONFIG"),
    MONGODB_WEB(28017, "MONGODB_WEB");

    public final int portNumber;

    public final String name;
}