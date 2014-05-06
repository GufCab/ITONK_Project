import com.rti.dds.domain.DomainParticipant;
import com.rti.dds.domain.DomainParticipantFactory;
import com.rti.dds.infrastructure.RETCODE_ERROR;
import com.rti.dds.infrastructure.RETCODE_NO_DATA;
import com.rti.dds.infrastructure.StatusKind;
import com.rti.dds.subscription.DataReader;
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

        OfficeSubscriber GossipSubscriber = new OfficeSubscriber(null, null, "Gossip");
        OfficeSubscriber SportsSubscriber = new OfficeSubscriber(null, null, "Sport");

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