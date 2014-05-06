import com.rti.dds.domain.DomainParticipant;
import com.rti.dds.domain.DomainParticipantFactory;
import com.rti.dds.infrastructure.*;
import com.rti.dds.publication.DataWriterQos;
import com.rti.dds.publication.Publisher;
import com.rti.dds.subscription.DataReader;
import com.rti.dds.subscription.DataReaderQos;
import com.rti.dds.subscription.SampleInfo;
import com.rti.dds.subscription.Subscriber;
import com.rti.dds.topic.Topic;
import com.rti.dds.type.builtin.StringDataReader;
import com.rti.dds.type.builtin.StringTypeSupport;

/**
 * Created by chj on 5/6/14.
 */




public class main {

    // For clean shutdown sequence
    private static boolean shutdown_flag = false;

    public static final void main(String[] args) {

        DataReaderQos GossipQoS = Subscriber.DATAREADER_QOS_DEFAULT;

        GossipQoS.durability.kind = DurabilityQosPolicyKind.TRANSIENT_DURABILITY_QOS;
        GossipQoS.durability.direct_communication = true;

        GossipQoS.history.kind = HistoryQosPolicyKind.KEEP_LAST_HISTORY_QOS;
        GossipQoS.history.depth = 100;

        GossipQoS.reliability.kind = ReliabilityQosPolicyKind.RELIABLE_RELIABILITY_QOS;


        OfficeSubscriber GossipSubscriber = new OfficeSubscriber(
                DomainParticipantFactory.PARTICIPANT_QOS_DEFAULT,
                DomainParticipant.TOPIC_QOS_DEFAULT,
                GossipQoS,
                "Gossip");
        OfficeSubscriber SportsSubscriber = new OfficeSubscriber(
                DomainParticipantFactory.PARTICIPANT_QOS_DEFAULT,
                DomainParticipant.TOPIC_QOS_DEFAULT,
                Subscriber.DATAREADER_QOS_DEFAULT,
                "Sport");

        System.out.println("Ready to read data.");
        System.out.println("Press CTRL+C to terminate.");
        for (;;) {
            try {
                Thread.sleep(2000);
                if(shutdown_flag) break;
            } catch (InterruptedException e) {
                // Nothing to do...
            }
        }


        GossipSubscriber.onDestroy();
        SportsSubscriber.onDestroy();


    }


}