package org.smoodi.physalus.exchange;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StandardPorts {

    // Well Known Port; 0 ~ 1023
    FTP(20),
    FTP_(21),
    SSH(22),
    TELNET(23),
    SMTP(25),
    DNS(53),
    DHCP(67),
    TFTP(69),
    HTTP(80),
    HTTP_(8080),
    POP2(109),
    POP3(110),
    RPC(111),
    NTP(123),
    NETBIOS(139),
    IMAP4(143),
    SNMP_AGENT(161),
    SNMP_MANAGER(162),
    IRC(194),
    HTTPS(443),
    SMB(445),
    IMAP4S(993),

    // Registered Port; 1024 ~ 49151
    INFOMIX(1526),
    DERBY(1527),
    MYSQL(3306),
    MARIADB(3306),
    SYBASE(5000),
    POSTGRESQL(5432),
    COUCHDB(5984),
    REDIS(6379),
    MONGODB(27017),
    MONGODB_SHARD(27018),
    MONGODB_CONFIG(27019),
    MONGODB_WEB(28017);

    public final int portNumber;
}