package com.devansh.dns.parser;

import com.devansh.dns.protocol.*;

import java.nio.ByteBuffer;

public class DNSParser {

    public DNSPacket parse(ByteBuffer buffer) {

        DNSPacket packet = new DNSPacket();

        packet.setHeader(parseHeader(buffer));

        parseQuestions(buffer, packet);

        parseAnswers(buffer, packet);

        parseAuthorities(buffer, packet);

        parseAdditionals(buffer, packet);


        return packet;
    }

    private DNSHeader parseHeader(ByteBuffer buffer) {

        DNSHeader header = new DNSHeader();

        header.setTransactionId(buffer.getShort() & 0xFFFF);

        header.setFlags(buffer.getShort() & 0xFFFF);

        header.setQuestionCount(buffer.getShort() & 0xFFFF);

        header.setAnswerCount(buffer.getShort() & 0xFFFF);

        header.setAuthorityCount(buffer.getShort() & 0xFFFF);

        header.setAdditionalCount(buffer.getShort() & 0xFFFF);

        return header;
    }
    private void parseQuestions(ByteBuffer buffer,
                                DNSPacket packet) {

        int count =
                packet.getHeader()
                        .getQuestionCount();

        for (int i = 0; i < count; i++) {

            packet.getQuestions()
                    .add(parseQuestion(buffer));

        }

    }

    private DNSQuestion parseQuestion(ByteBuffer buffer) {

        DNSQuestion question = new DNSQuestion();

        question.setDomain(parseDomainName(buffer));

        question.setType(
                RecordType.fromValue(
                        buffer.getShort() & 0xFFFF));

        question.setDnsClass(
                DNSClass.fromValue(
                        buffer.getShort() & 0xFFFF));

        return question;

    }

    private String parseDomainName(ByteBuffer buffer) {

        StringBuilder domain = new StringBuilder();

        boolean jumped = false;
        int originalPosition = -1;

        while (true) {

            int length = buffer.get() & 0xFF;

            // End of name
            if (length == 0) {
                break;
            }

            // Compression pointer?
            if ((length & 0xC0) == 0xC0) {

                int nextByte = buffer.get() & 0xFF;

                int pointer = ((length & 0x3F) << 8) | nextByte;

                if (!jumped) {
                    originalPosition = buffer.position();
                    jumped = true;
                }

                buffer.position(pointer);

                continue;
            }

            byte[] label = new byte[length];

            buffer.get(label);

            if (!domain.isEmpty()) {
                domain.append('.');
            }

            domain.append(new String(label));
        }

        if (jumped) {
            buffer.position(originalPosition);
        }

        return domain.toString();
    }

    private void parseAnswers(ByteBuffer buffer,
                              DNSPacket packet) {

        int count = packet.getHeader().getAnswerCount();

        for (int i = 0; i < count; i++) {

            packet.getAnswers().add(parseRecord(buffer));

        }
    }

    private void parseAuthorities(ByteBuffer buffer,
                                  DNSPacket packet) {

        int count = packet.getHeader().getAuthorityCount();

        for (int i = 0; i < count; i++) {

            packet.getAuthorities().add(parseRecord(buffer));

        }
    }

    private void parseAdditionals(ByteBuffer buffer,
                                  DNSPacket packet) {

        int count = packet.getHeader().getAdditionalCount();

        for (int i = 0; i < count; i++) {

            packet.getAdditionals().add(parseRecord(buffer));

        }
    }

    private DNSRecord parseRecord(ByteBuffer buffer) {

        DNSRecord record = new DNSRecord();

        record.setName(parseDomainName(buffer));

        RecordType type =
                RecordType.fromValue(buffer.getShort() & 0xFFFF);

        record.setType(type);

        switch (type) {

            case OPT:
                parseOPTRecord(buffer, record);
                break;

            case A:
                parseARecord(buffer, record);
                break;

            default:
                skipUnknownRecord(buffer,record);
                break;
        }

        return record;
    }

    private void parseARecord(ByteBuffer buffer,
                              DNSRecord record) {

        record.setDnsClass(
                DNSClass.fromValue(buffer.getShort() & 0xFFFF));

        record.setTtl(buffer.getInt() & 0xFFFFFFFFL);

        int length = buffer.getShort() & 0xFFFF;

        if (length != 4) {
            throw new IllegalArgumentException(
                    "Invalid IPv4 record length: " + length);
        }

        record.setValue(parseIPv4(buffer));
    }

    private void parseOPTRecord(ByteBuffer buffer,
                                DNSRecord record) {

        // CLASS field = UDP payload size
        int udpPayloadSize = buffer.getShort() & 0xFFFF;

        // TTL contains:
        // Extended RCODE
        // EDNS Version
        // Flags
        buffer.getInt();

        int rdLength = buffer.getShort() & 0xFFFF;

        // Skip EDNS option data
        buffer.position(buffer.position() + rdLength);

        // Optional: keep the payload size as the value for debugging
        record.setValue(String.valueOf(udpPayloadSize));
    }

    private void skipUnknownRecord(ByteBuffer buffer,
                                   DNSRecord record) {

        /*
         * Unknown records still follow the standard layout:
         *
         * CLASS
         * TTL
         * RDLENGTH
         * RDATA
         */

        buffer.getShort();   // CLASS

        buffer.getInt();     // TTL

        int rdLength = buffer.getShort() & 0xFFFF;

        buffer.position(buffer.position() + rdLength);
    }

    private String parseIPv4(ByteBuffer buffer) {

        StringBuilder ip = new StringBuilder();

        for (int i = 0; i < 4; i++) {

            int value = buffer.get() & 0xFF;

            ip.append(value);

            if (i < 3) {
                ip.append(".");
            }

        }

        return ip.toString();

    }



}