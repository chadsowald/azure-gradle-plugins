package lenala.azure.gradle.functions.bindings;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Map;

public class BindingSerializer extends StdSerializer<Binding> {

    public BindingSerializer() {
        this(null);
    }

    public BindingSerializer(Class<Binding> item) {
        super(item);
    }

    @Override
    public void serialize(Binding value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeStringField("type", value.getType());
        generator.writeStringField("direction", value.getDirection());
        generator.writeStringField("name", value.getName());
        final Map<String, Object> attributes = value.getBindingAttributes();
        for (final Map.Entry<String, Object> entry : attributes.entrySet()) {
            // Skip 'name' property since we have serialized before the for-loop
            if (entry.getKey().equals("name")) {
                continue;
            }
            generator.writeObjectField(entry.getKey(), entry.getValue());
        }
        generator.writeEndObject();
    }
}
