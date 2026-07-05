package com.devansh.dns.parser;

import com.devansh.dns.packet.DNSHeader;

import java.nio.ByteBuffer;

public class DNSParser {
    public DNSHeader parseHeader(byte[] data) {

        ByteBuffer buffer = ByteBuffer.wrap(data);

        DNSHeader header = new DNSHeader();

        header.setTransactionId(buffer.getShort());
        header.setFlags(buffer.getShort());
        header.setQuestionCount(buffer.getShort());
        header.setAnswerCount(buffer.getShort());
        header.setAuthorityCount(buffer.getShort());
        header.setAdditionalCount(buffer.getShort());

        return header;
    }
}
