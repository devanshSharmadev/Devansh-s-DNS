package com.devansh.dns.zone;

import com.devansh.dns.protocol.RecordType;

public class ZoneRecord {
    private String domain;
    private RecordType recordType;
    private String value;
    private long ttl;

    public ZoneRecord(){

    }

    public ZoneRecord(String domain, RecordType recordType, String value, long ttl) {
        this.domain = domain;
        this.recordType = recordType;
        this.value = value;
        this.ttl = ttl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public RecordType getRecordType() {
        return recordType;
    }

    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }
}
