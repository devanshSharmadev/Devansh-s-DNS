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

        StringBuilder domain =
                new StringBuilder();

        while (true) {

            int length =
                    buffer.get() & 0xFF;

            if (length == 0) {

                break;

            }

            byte[] label =
                    new byte[length];

            buffer.get(label);

            if (!domain.isEmpty()) {

                domain.append('.');

            }

            domain.append(new String(label));

        }

        return domain.toString();

    }

    private void parseAnswers(ByteBuffer buffer,
                              DNSPacket packet) {

    }

    private void parseAuthorities(ByteBuffer buffer,
                                  DNSPacket packet) {

    }

    private void parseAdditionals(ByteBuffer buffer,
                                  DNSPacket packet) {

    }



}