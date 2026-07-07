package com.devansh.dns.protocol;

public class DNSQuestion {

    private String domain;

    private RecordType type;

    private DNSClass dnsClass;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
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

    @Override
    public String toString() {
        return "DNSQuestion{" +
                "domain='" + domain + '\'' +
                ", type=" + type +
                ", dnsClass=" + dnsClass +
                '}';
    }
}