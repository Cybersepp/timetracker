package data.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import data.Recording;
import logic.treeItems.ProjectTreeItem;
import logic.treeItems.TaskTreeItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjectItemDeserialization extends StdDeserializer<ProjectTreeItem> {

    public ProjectItemDeserialization(Class<?> vc) {
        super(vc);
    }

    public ProjectItemDeserialization() {
        this(null);
    }

    @Override
    public ProjectTreeItem deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        String projectName = node.get("value").asText();
        boolean archived = node.get("archived").asBoolean();

        JsonNode tasks = node.get("tasks");
        ObjectMapper objectMapper = new ObjectMapper();
        List<TaskTreeItem> taskList = Arrays.asList(objectMapper.treeToValue(tasks, TaskTreeItem[].class));
        return new ProjectTreeItem(projectName, archived, taskList);

    }
}
