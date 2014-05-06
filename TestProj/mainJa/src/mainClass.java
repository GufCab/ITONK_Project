import com.rti.dds.domain.DomainParticipant;
import com.rti.dds.domain.DomainParticipantFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by guf on 5/5/14.
 */

public class mainClass {
        private final static String GOSSIP_TOPIC = "Gossip";

        public static final void main(String[] args) {


            OfficePublisher gossipper = new OfficePublisher(GOSSIP_TOPIC, DomainParticipant.TOPIC_QOS_DEFAULT, DomainParticipantFactory.PARTICIPANT_QOS_DEFAULT);
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


