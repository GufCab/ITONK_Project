import com.rti.dds.domain.DomainParticipant;
import com.rti.dds.domain.DomainParticipantFactory;
import com.rti.dds.infrastructure.*;
import com.rti.dds.publication.DataWriterQos;
import com.rti.dds.publication.Publisher;
import com.rti.dds.topic.TopicQos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by guf on 5/5/14.
 */

public class mainClass {
        private final static String GOSSIP_TOPIC = "Gossip";
        private final static String SPORT_TOPIC = "Sport";

        public static final void main(String[] args) {

            TopicQos topicQos = DomainParticipant.TOPIC_QOS_DEFAULT;
            DataWriterQos dataWriterQos = Publisher.DATAWRITER_QOS_DEFAULT;
            //Duration_t duration_t = new Duration_t(100000,100000);

            topicQos.lifespan.duration.add(new Duration_t(Duration_t.DURATION_INFINITE_SEC, Duration_t.DURATION_INFINITY_NSEC));

            dataWriterQos.lifespan.duration.add(new Duration_t(Duration_t.DURATION_INFINITE_SEC, Duration_t.DURATION_INFINITY_NSEC));
            dataWriterQos.durability.kind = DurabilityQosPolicyKind.TRANSIENT_DURABILITY_QOS;
            dataWriterQos.history.kind = HistoryQosPolicyKind.KEEP_LAST_HISTORY_QOS;
            dataWriterQos.history.depth = 100;
            dataWriterQos.reliability.kind = ReliabilityQosPolicyKind.RELIABLE_RELIABILITY_QOS;
            dataWriterQos.ownership.kind = OwnershipQosPolicyKind.SHARED_OWNERSHIP_QOS;
            dataWriterQos.liveliness.kind = LivelinessQosPolicyKind.AUTOMATIC_LIVELINESS_QOS;
            dataWriterQos.liveliness.lease_duration.add(new Duration_t(Duration_t.DURATION_INFINITE_SEC, Duration_t.DURATION_INFINITY_NSEC));

            OfficePublisher gossipPub = new OfficePublisher(GOSSIP_TOPIC, DomainParticipant.TOPIC_QOS_DEFAULT, DomainParticipantFactory.PARTICIPANT_QOS_DEFAULT, dataWriterQos);
            OfficePublisher sportPub = new OfficePublisher(SPORT_TOPIC, DomainParticipant.TOPIC_QOS_DEFAULT, DomainParticipantFactory.PARTICIPANT_QOS_DEFAULT, dataWriterQos);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                try {
                    System.out.print("Please type gossip a message> ");
                    String toWrite = reader.readLine();
                    gossipPub.PublishMessage(toWrite);

                    System.out.print("Please type sport a message> ");
                    toWrite = reader.readLine();
                    sportPub.PublishMessage(toWrite);
                } catch (IOException e)  {
                    System.out.println("Something went.. wrong..?");
                }
            }
        }

    }


