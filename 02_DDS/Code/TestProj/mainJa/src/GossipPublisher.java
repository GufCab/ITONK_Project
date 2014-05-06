import com.rti.dds.domain.DomainParticipant;
import com.rti.dds.domain.DomainParticipantFactory;
import com.rti.dds.infrastructure.InstanceHandle_t;
import com.rti.dds.infrastructure.StatusKind;
import com.rti.dds.publication.Publisher;
import com.rti.dds.topic.Topic;
import com.rti.dds.topic.TopicQos;
import com.rti.dds.type.builtin.StringDataWriter;
import com.rti.dds.type.builtin.StringTypeSupport;

/**
 * Created by guf on 5/5/14.
 */

public class GossipPublisher {
    //private final String _topic_name = "Gossip";
    private final String _topic_name = "Hello, World!";
    private DomainParticipant _domain;
    private Topic _topic;
    private StringDataWriter _dataWriter;


    public GossipPublisher()
    {
        // Create the DDS Domain participant on domain ID 0
        _domain = DomainParticipantFactory.get_instance().create_participant(
                0, // Domain ID = 0
                DomainParticipantFactory.PARTICIPANT_QOS_DEFAULT,
                null, // listener
                StatusKind.STATUS_MASK_NONE);
        if (_domain == null) {
            System.err.println("Unable to create domain participant");
            return;
        }

        // Create the topic "Hello World" for the String type
        _topic = _domain.create_topic(
                _topic_name,
                StringTypeSupport.get_type_name(),
                DomainParticipant.TOPIC_QOS_DEFAULT,
                null, // listener
                StatusKind.STATUS_MASK_NONE);
        if (_topic == null) {
            System.err.println("Unable to create topic.");
            return;
        }

        TopicQos myQoS = new TopicQos();


        // Create the data writer using the default publisher
        _dataWriter =
                (StringDataWriter) _domain.create_datawriter(
                    _topic,
                    Publisher.DATAWRITER_QOS_DEFAULT,
                    null, // listener
                    StatusKind.STATUS_MASK_NONE);
        if (_dataWriter == null) {
            System.err.println("Unable to create data writer\n");
            return;
        }

        System.out.println("Publisher created for: " + _topic_name);
    }

    public void PublishMessage(String message)
    {
        _dataWriter.write(message, InstanceHandle_t.HANDLE_NIL);
    }

    public void SetTopic(String message)
    {

    }
}
