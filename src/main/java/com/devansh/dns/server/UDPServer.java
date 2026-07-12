package com.devansh.dns.server;

import com.devansh.dns.parser.DNSParser;
import com.devansh.dns.parser.DNSWriter;
import com.devansh.dns.protocol.DNSPacket;
import com.devansh.dns.protocol.DNSQuestion;
import com.devansh.dns.resolver.DNSResolver;
import com.devansh.dns.resolver.ForwardingDNSResolver;
import com.devansh.dns.resolver.StaticDNSResolver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;

public class UDPServer {

    private static final int PORT = 8053;
    private static final int BUFFER_SIZE = 512;

    private final DNSParser parser = new DNSParser();
    private final DNSWriter writer = new DNSWriter();
    private final DNSResolver resolver =
            new ForwardingDNSResolver();

    public void start() throws Exception {

        try (DatagramSocket socket = new DatagramSocket(PORT)) {

            System.out.println("====================================");
            System.out.println(" DNS Server Started");
            System.out.println(" Listening on UDP Port : " + PORT);
            System.out.println("====================================");

            while (true) {

                byte[] buffer = new byte[BUFFER_SIZE];

                DatagramPacket packet =
                        new DatagramPacket(buffer, buffer.length);

                socket.receive(packet);

                System.out.println();
                System.out.println("====================================");
                System.out.println("Received Request");
                System.out.println("====================================");

                ByteBuffer byteBuffer = ByteBuffer.wrap(
                        packet.getData(),
                        0,
                        packet.getLength());

                // Parse DNS Request
                DNSPacket request = parser.parse(byteBuffer);

                printPacketInfo(request);

                // Resolve DNS Request
                DNSPacket response = resolver.resolve(request);

                // Serialize Response
                byte[] responseBytes = writer.write(response);

                // Send Response
                DatagramPacket responsePacket = new DatagramPacket(
                        responseBytes,
                        responseBytes.length,
                        packet.getAddress(),
                        packet.getPort()
                );

                socket.send(responsePacket);

                System.out.println("Response Sent");
                System.out.println("====================================");
            }
        }
    }

    private void printPacketInfo(DNSPacket packet) {

        System.out.println("Transaction ID : "
                + packet.getHeader().getTransactionId());

        System.out.println("Flags          : "
                + String.format("0x%04X",
                packet.getHeader().getFlags()));

        System.out.println("Questions      : "
                + packet.getHeader().getQuestionCount());

        System.out.println("Answers        : "
                + packet.getHeader().getAnswerCount());

        System.out.println("Authorities    : "
                + packet.getHeader().getAuthorityCount());

        System.out.println("Additionals    : "
                + packet.getHeader().getAdditionalCount());

        System.out.println();

        for (DNSQuestion question : packet.getQuestions()) {

            System.out.println("Question");
            System.out.println("--------");
            System.out.println("Domain : " + question.getDomain());
            System.out.println("Type   : " + question.getType());
            System.out.println("Class  : " + question.getDnsClass());
            System.out.println();
        }
    }
}