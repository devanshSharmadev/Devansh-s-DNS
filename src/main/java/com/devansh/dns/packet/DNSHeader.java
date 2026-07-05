package com.devansh.dns.packet;

public class DNSHeader {
    private short transactionId;
    private short flags;
    private short questionCount;
    private short answerCount;
    private short authorityCount;
    private short additionalCount;

    public short getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(short transactionId) {
        this.transactionId = transactionId;
    }

    public short getFlags() {
        return flags;
    }

    public void setFlags(short flags) {
        this.flags = flags;
    }

    public short getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(short questionCount) {
        this.questionCount = questionCount;
    }

    public short getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(short answerCount) {
        this.answerCount = answerCount;
    }

    public short getAuthorityCount() {
        return authorityCount;
    }

    public void setAuthorityCount(short authorityCount) {
        this.authorityCount = authorityCount;
    }

    public short getAdditionalCount() {
        return additionalCount;
    }

    public void setAdditionalCount(short additionalCount) {
        this.additionalCount = additionalCount;
    }
}
