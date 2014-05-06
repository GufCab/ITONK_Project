import com.rti.dds.domain.DomainParticipant;
import com.rti.dds.domain.DomainParticipantFactory;
import com.rti.dds.infrastructure.DurabilityQosPolicyKind;
import com.rti.dds.infrastructure.Duration_t;
import com.rti.dds.infrastructure.HistoryQosPolicyKind;
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

        public static final void main(String[] args) {

            TopicQos topicQos = DomainParticipant.TOPIC_QOS_DEFAULT;
            DataWriterQos dataWriterQos = Publisher.DATAWRITER_QOS_DEFAULT;
            Duration_t duration_t = new Duration_t(100000,100000);

            topicQos.lifespan.duration.add(duration_t);

            dataWriterQos.lifespan.duration.add(duration_t);
            dataWriterQos.durability.kind = DurabilityQosPolicyKind.TRANSIENT_DURABILITY_QOS;
            dataWriterQos.history.kind = HistoryQosPolicyKind.KEEP_LAST_HISTORY_QOS;
            dataWriterQos.history.depth = 100;

            OfficePublisher gossipper = new OfficePublisher(GOSSIP_TOPIC, DomainParticipant.TOPIC_QOS_DEFAULT, DomainParticipantFactory.PARTICIPANT_QOS_DEFAULT, dataWriterQos);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.print("Please type a message> ");
                try {
                    String toWrite = reader.readLine();
                    gossipper.PublishMessage(toWrite);
                } catch (IOException e)  {
                    System.out.println("Something went.. wrong..?");
                }

                /*try{
                    Thread.sleep(1000, 10);
                }catch (Exception e)
                {
                    System.out.println("Something went.. wrong..?");
                    System.out.println(e.getMessage());
                }*/
            }

        }

    }


