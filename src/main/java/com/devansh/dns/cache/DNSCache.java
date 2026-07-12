package com.devansh.dns.cache;

import com.devansh.dns.protocol.DNSPacket;

import java.util.concurrent.ConcurrentHashMap;

public class DNSCache {

    private final ConcurrentHashMap<CacheKey, CacheEntry> cache =
            new ConcurrentHashMap<>();

    public DNSPacket get(CacheKey key) {

        CacheEntry entry = cache.get(key);

        if (entry == null) {
            return null;
        }

        if (entry.isExpired()) {

            cache.remove(key);

            System.out.println("Cache expired: " + key);

            return null;
        }

        System.out.println("Cache HIT: " + key);

        return entry.getPacket();
    }

    public void put(CacheKey key,
                    DNSPacket packet,
                    long ttl) {

        long expiresAt =
                System.currentTimeMillis() + (ttl * 1000);

        cache.put(
                key,
                new CacheEntry(packet, expiresAt));

        System.out.println("Cached: " + key);
    }

    public void clear() {
        cache.clear();
    }

    public int size() {
        return cache.size();
    }
}