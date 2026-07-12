package com.devansh.dns.resolver;

import com.devansh.dns.protocol.DNSPacket;

import java.io.IOException;

public interface DNSResolver {

    DNSPacket resolve(DNSPacket request) throws IOException;

}