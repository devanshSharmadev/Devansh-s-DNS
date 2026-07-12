import com.devansh.dns.client.UpstreamDNSClient;
import com.devansh.dns.protocol.*;

public class UpstreamDNSClientTest {
    public static void main(String[] args) throws Exception {

        DNSPacket request = createRequest("google.com");

        UpstreamDNSClient client = new UpstreamDNSClient();

        DNSPacket response = client.resolve(request);

        printResponse(response);
    }

    private static DNSPacket createRequest(String domain) {

        DNSPacket packet = new DNSPacket();

        DNSHeader header = new DNSHeader();

        header.setTransactionId(1234);

        // Standard recursive query
        header.setFlags(0x0100);

        header.setQuestionCount(1);

        header.setAnswerCount(0);

        header.setAuthorityCount(0);

        header.setAdditionalCount(0);

        packet.setHeader(header);

        DNSQuestion question = new DNSQuestion();

        question.setDomain(domain);

        question.setType(RecordType.A);

        question.setDnsClass(DNSClass.IN);

        packet.getQuestions().add(question);

        return packet;
    }

    private static void printResponse(DNSPacket packet) {

        System.out.println();
        System.out.println("========== RESPONSE ==========");

        System.out.println("Transaction ID : "
                + packet.getHeader().getTransactionId());

        System.out.println("Flags          : "
                + String.format("0x%04X",
                packet.getHeader().getFlags()));

        System.out.println("Questions      : "
                + packet.getQuestions().size());

        System.out.println("Answers        : "
                + packet.getAnswers().size());

        System.out.println("Authorities    : "
                + packet.getAuthorities().size());

        System.out.println("Additionals    : "
                + packet.getAdditionals().size());

        System.out.println();

        for (DNSRecord answer : packet.getAnswers()) {

            System.out.println("Name  : " + answer.getName());

            System.out.println("Type  : " + answer.getType());

            System.out.println("Class : " + answer.getDnsClass());

            System.out.println("TTL   : " + answer.getTtl());

            System.out.println("Value : " + answer.getValue());

            System.out.println("--------------------------------");
        }
    }
}
