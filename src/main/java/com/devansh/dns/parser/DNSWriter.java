package com.devansh.dns.parser;

import com.devansh.dns.protocol.*;

import java.nio.ByteBuffer;

public class DNSWriter {

    public byte[] write(DNSPacket packet) {

        ByteBuffer buffer = ByteBuffer.allocate(512);

        writeHeader(buffer, packet.getHeader());

        for (DNSQuestion question : packet.getQuestions()) {
            writeQuestion(buffer, question);
        }

        for (DNSRecord record : packet.getAnswers()) {
            writeRecord(buffer, record);
        }

        // Authorities and Additionals will come later

        byte[] data = new byte[buffer.position()];

        buffer.flip();

        buffer.get(data);

        return data;
    }

    private void writeHeader(ByteBuffer buffer,
                             DNSHeader header) {

        buffer.putShort((short) header.getTransactionId());

        buffer.putShort((short) header.getFlags());

        buffer.putShort((short) header.getQuestionCount());

        buffer.putShort((short) header.getAnswerCount());

        buffer.putShort((short) header.getAuthorityCount());

        buffer.putShort((short) header.getAdditionalCount());
    }

    private void writeQuestion(ByteBuffer buffer,
                               DNSQuestion question) {

        writeDomainName(buffer, question.getDomain());

        buffer.putShort((short) question.getType().getValue());

        buffer.putShort((short) question.getDnsClass().getValue());
    }

    private void writeDomainName(ByteBuffer buffer,
                                 String domain) {

        String[] labels = domain.split("\\.");

        for (String label : labels) {

            buffer.put((byte) label.length());

            buffer.put(label.getBytes());

        }

        // End of domain name
        buffer.put((byte) 0);
    }

    private void writeRecord(ByteBuffer buffer,
                             DNSRecord record) {

        writeDomainName(buffer, record.getName());

        buffer.putShort((short) record.getType().getValue());

        buffer.putShort((short) record.getDnsClass().getValue());

        buffer.putInt((int) record.getTtl());

        switch (record.getType()) {

            case A:

                buffer.putShort((short) 4);

                writeIPv4(buffer, record.getValue());

                break;

            default:

                throw new UnsupportedOperationException(
                        "Unsupported record type : "
                                + record.getType());

        }

    }

    private void writeIPv4(ByteBuffer buffer,
                           String ipAddress) {

        String[] octets = ipAddress.split("\\.");

        if (octets.length != 4) {
            throw new IllegalArgumentException(
                    "Invalid IPv4 Address : " + ipAddress);
        }

        for (String octet : octets) {

            int value = Integer.parseInt(octet);

            buffer.put((byte) value);

        }

    }
}