package com.devansh.dns.cache;

import com.devansh.dns.protocol.DNSPacket;

public class CacheEntry {

    private final DNSPacket packet;
    private final long expiresAt;

    public CacheEntry(DNSPacket packet, long expiresAt) {
        this.packet = packet;
        this.expiresAt = expiresAt;
    }

    public DNSPacket getPacket() {
        return packet;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiresAt;
    }
}