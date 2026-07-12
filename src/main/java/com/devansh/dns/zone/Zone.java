package com.devansh.dns.zone;

import java.util.ArrayList;
import java.util.List;

public class Zone {
    private final List<ZoneRecord> records=new ArrayList<>();
    public List<ZoneRecord> getRecords(){
        return records;
    }

    public void addRecord(ZoneRecord zoneRecord){
        records.add(zoneRecord);
    }
}
