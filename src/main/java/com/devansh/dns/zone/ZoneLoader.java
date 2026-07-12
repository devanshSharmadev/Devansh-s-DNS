package com.devansh.dns.zone;

import com.devansh.dns.protocol.RecordType;

public class ZoneLoader {

    public Zone load() {

        Zone zone = new Zone();

        zone.addRecord(
                new ZoneRecord(
                        "google.com",
                        RecordType.A,
                        "142.250.183.206",
                        300));

        zone.addRecord(
                new ZoneRecord(
                        "facebook.com",
                        RecordType.A,
                        "157.240.22.35",
                        300));

        zone.addRecord(
                new ZoneRecord(
                        "github.com",
                        RecordType.A,
                        "140.82.121.4",
                        300));

        zone.addRecord(
                new ZoneRecord(
                        "openai.com",
                        RecordType.A,
                        "104.18.33.45",
                        300));

        zone.addRecord(
                new ZoneRecord(
                        "localhost",
                        RecordType.A,
                        "127.0.0.1",
                        300));

        return zone;
    }
}