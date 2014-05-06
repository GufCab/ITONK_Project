import com.rti.dds.domain.DomainParticipant;
import com.rti.dds.domain.DomainParticipantFactory;
import com.rti.dds.domain.DomainParticipantQos;
import com.rti.dds.infrastructure.InstanceHandle_t;
import com.rti.dds.infrastructure.RETCODE_ERROR;
import com.rti.dds.infrastructure.StatusKind;
import com.rti.dds.publication.DataWriterQos;
import com.rti.dds.topic.Topic;
import com.rti.dds.topic.TopicQos;
import com.rti.dds.type.builtin.StringDataWriter;
import com.rti.dds.type.builtin.StringTypeSupport;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by guf on 5/5/14.
 */
public class OfficePublisher {

    private String _topicName;
    private TopicQos _topicQos;
    private DomainParticipantQos _domainParticipantQos;
    private DomainParticipant _domainParticipant;
    private Topic _topic;
    private StringDataWriter _dataWriter;
    private DataWriterQos _dataWriterQos;

    public OfficePublisher(String topicName, TopicQos qos, DomainParticipantQos domainParticipantQos, DataWriterQos dataWriterQos) {
        _topicName = topicName;
        _topicQos = qos;
        _domainParticipantQos = domainParticipantQos;
        _dataWriterQos = dataWriterQos;


        // Create the DDS Domain participant on domain ID 0
        _domainParticipant = DomainParticipantFactory.get_instance().create_participant(
                0, // Domain ID = 0
                _domainParticipantQos,
                null, // listener
                StatusKind.STATUS_MASK_NONE);
        if (_domainParticipant == null) {
            System.err.println("Unable to create domain participant");
            return;
        }

        // Create the topic "Hello World" for the String type
        _topic = _domainParticipant.create_topic(
                _topicName,
                StringTypeSupport.get_type_name(),
                _topicQos,
                null, // listener
                StatusKind.STATUS_MASK_NONE);
        if (_topic == null) {
            System.err.println("Unable to create topic.");
            return;
        }

        // Create the data writer using the default publisher
        _dataWriter =
                (StringDataWriter) _domainParticipant.create_datawriter(
                        _topic,
                        _dataWriterQos,
                        null, // listener
                        StatusKind.STATUS_MASK_NONE);
        if (_dataWriter == null) {
            System.err.println("Unable to create data writer\n");
            return;
        }

        System.out.println("Publisher created for: " + _topicName);
    }

    public void PublishMessage(String message) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            _dataWriter.write(message, InstanceHandle_t.HANDLE_NIL);

        } catch (RETCODE_ERROR e) {
            // This exception can be thrown from DDS write operation
            e.printStackTrace();
        }

        //System.out.println("Exiting...");
        //_domainParticipant.delete_contained_entities();
        //DomainParticipantFactory.get_instance().delete_participant(_domainParticipant);
    }

    public void onDestroy() {
        _domainParticipant.delete_contained_entities();
        DomainParticipantFactory.get_instance().delete_participant(_domainParticipant);
    }


}
