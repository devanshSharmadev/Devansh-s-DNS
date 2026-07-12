package com.devansh.dns.resolver;

import com.devansh.dns.protocol.DNSPacket;

public interface DNSResolver {

    DNSPacket resolve(DNSPacket request);

}