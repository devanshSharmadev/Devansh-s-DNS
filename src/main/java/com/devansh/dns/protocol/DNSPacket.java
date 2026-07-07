package com.devansh.dns.protocol;

import java.util.ArrayList;
import java.util.List;

public class DNSPacket {

    private DNSHeader header = new DNSHeader();

    private List<DNSQuestion> questions = new ArrayList<>();

    private List<DNSRecord> answers = new ArrayList<>();

    private List<DNSRecord> authorities = new ArrayList<>();

    private List<DNSRecord> additionals = new ArrayList<>();

    public DNSHeader getHeader() {
        return header;
    }

    public void setHeader(DNSHeader header) {
        this.header = header;
    }

    public List<DNSQuestion> getQuestions() {
        return questions;
    }

    public List<DNSRecord> getAnswers() {
        return answers;
    }

    public List<DNSRecord> getAuthorities() {
        return authorities;
    }

    public List<DNSRecord> getAdditionals() {
        return additionals;
    }
}