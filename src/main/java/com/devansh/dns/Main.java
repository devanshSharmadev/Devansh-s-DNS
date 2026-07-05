package com.devansh.dns;

import com.devansh.dns.server.UDPServer;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception{

        UDPServer udpServer=new UDPServer();
        udpServer.start();
    }
}