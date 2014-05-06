import com.rti.dds.domain.DomainParticipant;
import com.rti.dds.domain.DomainParticipantFactory;
import com.rti.dds.infrastructure.*;
import com.rti.dds.subscription.DataReaderQos;
import com.rti.dds.subscription.Subscriber;

/**
 * Created by chj on 5/6/14.
 */




public class main {

    // For clean shutdown sequence
    private static boolean shutdown_flag = false;

    public static final void main(String[] args) {

        DataReaderQos GossipQoS = Subscriber.DATAREADER_QOS_DEFAULT;

        GossipQoS.reliability.kind = ReliabilityQosPolicyKind.RELIABLE_RELIABILITY_QOS;

        GossipQoS.durability.kind = DurabilityQosPolicyKind.TRANSIENT_DURABILITY_QOS;

        GossipQoS.ownership.kind = OwnershipQosPolicyKind.SHARED_OWNERSHIP_QOS;

        GossipQoS.history.kind = HistoryQosPolicyKind.KEEP_LAST_HISTORY_QOS;
        GossipQoS.history.depth = 100;

        GossipQoS.liveliness.kind = LivelinessQosPolicyKind.AUTOMATIC_LIVELINESS_QOS;
        GossipQoS.liveliness.lease_duration.add(new Duration_t(Duration_t.DURATION_INFINITY_SEC, Duration_t.DURATION_INFINITY_NSEC));

        //Deadline

        GossipQoS.time_based_filter.minimum_separation.add(new Duration_t(0, 0));


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

        OfficeSubscriber SportsSubscriber1 = new OfficeSubscriber(
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