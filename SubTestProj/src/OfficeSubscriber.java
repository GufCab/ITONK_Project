/**
 * Created by chj on 5/6/14.
 */

import com.rti.dds.domain.DomainParticipant;
import com.rti.dds.domain.DomainParticipantFactory;
import com.rti.dds.domain.DomainParticipantQos;
import com.rti.dds.infrastructure.RETCODE_ERROR;
import com.rti.dds.infrastructure.RETCODE_NO_DATA;
import com.rti.dds.infrastructure.StatusKind;
import com.rti.dds.subscription.*;
import com.rti.dds.topic.Topic;
import com.rti.dds.topic.TopicQos;
import com.rti.dds.type.builtin.StringDataReader;
import com.rti.dds.type.builtin.StringTypeSupport;


public class OfficeSubscriber extends DataReaderAdapter{

    // For clean shutdown sequence
    private static boolean shutdown_flag = false;

    private DomainParticipant mParticipant;
    private Topic mTopic;
    private StringDataReader mDataReader;

    public OfficeSubscriber(DomainParticipantQos objParticipantQoS, TopicQos objTopicQoS, DataReaderQos objDataReaderQos, String strTopicName) {

        // Create the DDS Domain mParticipant on domain ID 0
        mParticipant = DomainParticipantFactory.get_instance().create_participant(
                0, // Domain ID = 0
                objParticipantQoS,
                null, // listener
                StatusKind.STATUS_MASK_NONE);

        //Validate creation of particiant
        if (mParticipant == null) {
            System.err.println("Unable to create domain mParticipant");
            return;
        }

        // Create the topic for the String type
        mTopic = mParticipant.create_topic(
                strTopicName,
                StringTypeSupport.get_type_name(),
                objTopicQoS,
                null, // listener
                StatusKind.STATUS_MASK_NONE);
        if (mTopic == null) {
            System.err.println("Unable to create topic.");
            return;
        }

        // Create the data reader using the default publisher
        mDataReader = (StringDataReader) mParticipant.create_datareader(
                        mTopic,
                        objDataReaderQos,
                        this,         // Listener
                        StatusKind.DATA_AVAILABLE_STATUS);
        if (mDataReader == null) {
            System.err.println("Unable to create DDS Data Reader");
            return;
        }
    }

    /*
     * This method gets called back by DDS when one or more data samples have
     * been received.
     */
    public void on_data_available(DataReader reader) {
        StringDataReader stringReader = (StringDataReader) reader;
        SampleInfo info = new SampleInfo();
        for (;;) {
            try {
                String sample = stringReader.take_next_sample(info);
                if (info.valid_data) {
                    System.out.println(sample);
                    if (sample.equals("")) {
                        shutdown_flag = true;
                    }
                }
            } catch (RETCODE_NO_DATA noData) {
                // No more data to read
                break;
            } catch (RETCODE_ERROR e) {
                // An error occurred
                e.printStackTrace();
            }
        }
    }

    public void onDestroy() {
        System.out.println("Shutting down...");
        mParticipant.delete_contained_entities();
        DomainParticipantFactory.get_instance().delete_participant(mParticipant);
    }
}
