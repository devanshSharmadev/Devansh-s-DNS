package com.devansh.dns.cache;

import com.devansh.dns.protocol.RecordType;

import java.util.Objects;

public class CacheKey {

    private final String domain;
    private final RecordType type;

    public CacheKey(String domain, RecordType type) {
        this.domain = domain.toLowerCase();
        this.type = type;
    }

    public String getDomain() {
        return domain;
    }

    public RecordType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof CacheKey)) {
            return false;
        }

        CacheKey key = (CacheKey) o;

        return Objects.equals(domain, key.domain)
                && type == key.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(domain, type);
    }

    @Override
    public String toString() {
        return domain + ":" + type;
    }
}