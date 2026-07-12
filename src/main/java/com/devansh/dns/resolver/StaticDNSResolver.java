package com.devansh.dns.resolver;

import com.devansh.dns.protocol.*;
import com.devansh.dns.zone.Zone;
import com.devansh.dns.zone.ZoneLoader;
import com.devansh.dns.zone.ZoneRecord;
import com.devansh.dns.zone.ZoneRepository;

public class StaticDNSResolver implements DNSResolver {

    private static final String DEFAULT_IP = "192.168.1.100";
    private static final long DEFAULT_TTL = 300;
    private final ZoneRepository zoneRepository;

    public StaticDNSResolver(){
        Zone zone=new ZoneLoader().load();
        zoneRepository=new ZoneRepository(zone);
    }

    @Override
    public DNSPacket resolve(DNSPacket request) {

        DNSPacket response = new DNSPacket();

        DNSHeader header = createResponseHeader(request.getHeader());

        response.setHeader(header);

        response.getQuestions().addAll(request.getQuestions());

        for (DNSQuestion question : request.getQuestions()) {

            ZoneRecord zoneRecord =
                    zoneRepository.find(
                            question.getDomain(),
                            question.getType());

            if (zoneRecord != null) {

                response.getAnswers().add(
                        createRecord(zoneRecord));
            }
        }

        header.setAnswerCount(response.getAnswers().size());

        return response;
    }

    private DNSHeader createResponseHeader(DNSHeader requestHeader) {

        DNSHeader header = new DNSHeader();

        header.setTransactionId(requestHeader.getTransactionId());

        header.setFlags(0x8180);

        header.setQuestionCount(requestHeader.getQuestionCount());

        header.setAnswerCount(requestHeader.getQuestionCount());

        header.setAuthorityCount(0);

        header.setAdditionalCount(0);

        return header;
    }

    private DNSRecord createRecord(ZoneRecord zoneRecord) {

        DNSRecord record = new DNSRecord();

        record.setName(zoneRecord.getDomain());

        record.setType(zoneRecord.getRecordType());

        record.setDnsClass(DNSClass.IN);

        record.setTtl(zoneRecord.getTtl());

        record.setValue(zoneRecord.getValue());

        return record;
    }


}