package data.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import data.Recording;
import logic.treeItems.TaskTreeItem;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TaskItemDeserialization extends StdDeserializer<TaskTreeItem> {

    public TaskItemDeserialization(Class<?> vc) {
        super(vc);
    }

    public TaskItemDeserialization() {
        this(null);
    }

    @Override
    public TaskTreeItem deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        String taskName = node.get("value").asText();
        boolean done = node.get("done").asBoolean();
        JsonNode recordings = node.get("recordings");

        var objectMapper = new ObjectMapper();
        List<Recording> recordingsList = Arrays.asList(objectMapper.treeToValue(recordings, Recording[].class));

        var task = new TaskTreeItem(taskName, done);
        task.addAllJuniors(recordingsList);
        return task;
    }
}
