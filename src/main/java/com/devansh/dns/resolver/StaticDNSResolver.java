package com.devansh.dns.resolver;

import com.devansh.dns.protocol.*;

public class StaticDNSResolver implements DNSResolver {

    private static final String DEFAULT_IP = "192.168.1.100";
    private static final long DEFAULT_TTL = 300;

    @Override
    public DNSPacket resolve(DNSPacket request) {

        DNSPacket response = new DNSPacket();

        response.setHeader(createResponseHeader(request.getHeader()));

        response.getQuestions().addAll(request.getQuestions());

        for (DNSQuestion question : request.getQuestions()) {

            if (question.getType() == RecordType.A) {
                response.getAnswers().add(createARecord(question));
            }
        }

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

    private DNSRecord createARecord(DNSQuestion question) {

        DNSRecord record = new DNSRecord();

        record.setName(question.getDomain());

        record.setType(RecordType.A);

        record.setDnsClass(DNSClass.IN);

        record.setTtl(DEFAULT_TTL);

        record.setValue(DEFAULT_IP);

        return record;
    }
}