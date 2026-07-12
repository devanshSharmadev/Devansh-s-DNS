package com.devansh.dns.zone;

import com.devansh.dns.protocol.RecordType;

public class ZoneRepository {
    private final Zone zone;
    public ZoneRepository(Zone zone){
        this.zone=zone;
    }

    public ZoneRecord find(String domain, RecordType type){
        for(ZoneRecord record:zone.getRecords()){
            if(record.getDomain().equalsIgnoreCase(domain) && record.getRecordType()==type)
                return record;
        }
        return null;
    }
}
