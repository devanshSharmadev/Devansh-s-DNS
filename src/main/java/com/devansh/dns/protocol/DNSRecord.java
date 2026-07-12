package com.devansh.dns.protocol;

public class DNSRecord {

    private String name;

    private RecordType type;

    private DNSClass dnsClass;

    private long ttl;

    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecordType getType() {
        return type;
    }

    public void setType(RecordType type) {
        this.type = type;
    }

    public DNSClass getDnsClass() {
        return dnsClass;
    }

    public void setDnsClass(DNSClass dnsClass) {
        this.dnsClass = dnsClass;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}