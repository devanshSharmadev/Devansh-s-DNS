package com.devansh.dns.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    public void start() throws Exception{
        // Port 53 requires administrator/root privileges. So during development we use 5353
        DatagramSocket socket=new DatagramSocket(8053);
        System.out.println("DNS server started");
        while(true){
            byte[] buffer=new byte[512];
            DatagramPacket packet=new DatagramPacket(buffer,buffer.length);
            socket.receive(packet);

            System.out.println("Received "+packet.getLength()+" bytes");
        }
    }
}
