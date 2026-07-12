package com.devansh.dns.resolver;

import com.devansh.dns.client.UpstreamDNSClient;
import com.devansh.dns.protocol.*;
import com.devansh.dns.zone.Zone;
import com.devansh.dns.zone.ZoneLoader;
import com.devansh.dns.zone.ZoneRecord;
import com.devansh.dns.zone.ZoneRepository;

import java.io.IOException;

public class ForwardingDNSResolver implements DNSResolver{
    private final ZoneRepository repository;
    private final UpstreamDNSClient upstreamClient;

    public ForwardingDNSResolver() {

        Zone zone = new ZoneLoader().load();

        repository = new ZoneRepository(zone);

        upstreamClient = new UpstreamDNSClient();
    }

    @Override
    public DNSPacket resolve(DNSPacket request) throws IOException {

        if (canResolveLocally(request)) {

            System.out.println("Resolved from local zone.");

            return resolveLocally(request);
        }

        System.out.println("Forwarding request to upstream DNS...");

        return upstreamClient.resolve(request);
    }

    private boolean canResolveLocally(DNSPacket request) {

        for (DNSQuestion question : request.getQuestions()) {

            ZoneRecord record = repository.find(
                    question.getDomain(),
                    question.getType());

            if (record == null) {
                return false;
            }
        }

        return true;
    }

    private DNSPacket resolveLocally(DNSPacket request) {

        DNSPacket response = new DNSPacket();

        DNSHeader header = createResponseHeader(request.getHeader());

        response.setHeader(header);

        response.getQuestions().addAll(request.getQuestions());

        for (DNSQuestion question : request.getQuestions()) {

            ZoneRecord zoneRecord =
                    repository.find(
                            question.getDomain(),
                            question.getType());

            if (zoneRecord != null) {

                response.getAnswers().add(
                        createRecord(zoneRecord));
            }
        }

        header.setAnswerCount(response.getAnswers().size());

        if (response.getAnswers().isEmpty()) {
            return null;
        }

        return response;
    }

    private DNSHeader createResponseHeader(DNSHeader requestHeader) {

        DNSHeader header = new DNSHeader();

        header.setTransactionId(requestHeader.getTransactionId());

        header.setFlags(0x8180);

        header.setQuestionCount(requestHeader.getQuestionCount());

        header.setAnswerCount(0);

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
