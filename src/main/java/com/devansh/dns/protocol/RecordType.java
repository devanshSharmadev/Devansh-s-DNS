package com.devansh.dns.protocol;

public enum RecordType {

    A(1),
    NS(2),
    CNAME(5),
    SOA(6),
    PTR(12),
    MX(15),
    TXT(16),
    AAAA(28),

    // EDNS(0) OPT pseudo-record
    OPT(41);

    private final int value;

    RecordType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static RecordType fromValue(int value) {
        for (RecordType type : values()) {
            if (type.value == value) {
                return type;
            }
        }

        throw new IllegalArgumentException(
                "Unsupported Record type: " + value);
    }
}