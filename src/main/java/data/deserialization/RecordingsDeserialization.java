package data.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import data.Recording;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RecordingsDeserialization extends StdDeserializer<Recording> {

    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public RecordingsDeserialization(Class<Recording> vc) {
        super(vc);
    }

    public RecordingsDeserialization() {
        this(null);
    }

    @Override
    public Recording deserialize(JsonParser p, DeserializationContext ctxt) throws IOException{
        JsonNode node = p.getCodec().readTree(p);

        var start = LocalDateTime.parse(node.get("start").asText(), dateTimeFormat);
        var end = LocalDateTime.parse(node.get("end").asText(), dateTimeFormat);
        int duration = node.get("duration").asInt();

        return new Recording(start, end, duration);
    }
}
