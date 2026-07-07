package com.devansh.dns.protocol;

public enum DNSClass {

    IN(1);

    private final int value;

    DNSClass(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static DNSClass fromValue(int value) {

        for (DNSClass dnsClass : values()) {

            if (dnsClass.value == value) {
                return dnsClass;
            }

        }

        throw new IllegalArgumentException(
                "Unsupported DNS Class : " + value);
    }
}