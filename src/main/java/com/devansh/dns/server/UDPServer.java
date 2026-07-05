package com.devansh.dns.server;

import com.devansh.dns.packet.DNSHeader;
import com.devansh.dns.parser.DNSParser;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    public void start() throws Exception{
        // Port 53 requires administrator/root privileges. So during development we use 8053
        // It is used to set up network communication using the UDP (User Datagram Protocol).
        DatagramSocket socket=new DatagramSocket(8053);
        System.out.println("DNS server started");
        while(true){
            byte[] buffer=new byte[512];
            DatagramPacket packet=new DatagramPacket(buffer,buffer.length);
            socket.receive(packet);
            DNSParser parser = new DNSParser();
            DNSHeader header = parser.parseHeader(buffer);
            System.out.println("Transaction ID : " +
                    Integer.toHexString(header.getTransactionId() & 0xFFFF));

            System.out.println("Flags          : " +
                    Integer.toHexString(header.getFlags() & 0xFFFF));

            System.out.println("Questions      : " +
                    header.getQuestionCount());

            System.out.println("Received "+packet.getLength()+" bytes");

            for (int i = 0; i < packet.getLength(); i++) {
                System.out.printf("%02X ", buffer[i]);
            }

            System.out.println();
        }
    }
}
