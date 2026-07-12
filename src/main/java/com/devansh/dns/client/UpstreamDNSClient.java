package com.devansh.dns.client;

import com.devansh.dns.parser.DNSParser;
import com.devansh.dns.parser.DNSWriter;
import com.devansh.dns.protocol.DNSPacket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class UpstreamDNSClient {
    private static final String DNS_SERVER = "8.8.8.8";
    private static final int DNS_PORT = 53;
    private static final int BUFFER_SIZE = 512;
    private static final int TIMEOUT = 5000;

    private final DNSWriter writer = new DNSWriter();
    private final DNSParser parser = new DNSParser();


    public DNSPacket resolve(DNSPacket request) throws IOException {

        // Convert DNSPacket -> byte[]
        byte[] requestBytes = writer.write(request);

        InetAddress address = InetAddress.getByName(DNS_SERVER);

        try (DatagramSocket socket = new DatagramSocket()) {

            socket.setSoTimeout(TIMEOUT);

            // Send request
            DatagramPacket requestPacket =
                    new DatagramPacket(
                            requestBytes,
                            requestBytes.length,
                            address,
                            DNS_PORT);

            socket.send(requestPacket);

            // Receive response
            byte[] responseBuffer = new byte[BUFFER_SIZE];

            DatagramPacket responsePacket =
                    new DatagramPacket(
                            responseBuffer,
                            responseBuffer.length);

            socket.receive(responsePacket);

            // Parse response
            ByteBuffer byteBuffer =
                    ByteBuffer.wrap(
                            responsePacket.getData(),
                            0,
                            responsePacket.getLength());

            return parser.parse(byteBuffer);
        }
    }
}
