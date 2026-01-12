package cc.ddrpa.chaparral;

import cc.ddrpa.chaparral.annotation.Sensitive;
import cc.ddrpa.chaparral.desensitizer.DesensitizerFactory;
import cc.ddrpa.chaparral.desensitizer.IDesensitizer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Objects;

import static cc.ddrpa.chaparral.Constant.DEFAULT_MASK;

public class DesensitizeSerializer extends StdSerializer<Object> implements ContextualSerializer {
    private IDesensitizer desensitizer;

    protected DesensitizeSerializer() {
        super(Object.class);
    }

    protected DesensitizeSerializer(Class<Object> t) {
        super(t);
    }

    protected DesensitizeSerializer(JavaType type) {
        super(type);
    }

    protected DesensitizeSerializer(Class<?> t, boolean dummy) {
        super(t, dummy);
    }

    protected DesensitizeSerializer(StdSerializer<?> src) {
        super(src);
    }

    public IDesensitizer getDesensitizer() {
        return desensitizer;
    }

    public void setDesensitizer(IDesensitizer desensitizer) {
        this.desensitizer = desensitizer;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        IDesensitizer desensitizer = getDesensitizer();
        if (Objects.nonNull(desensitizer)) {
            try {
                gen.writeObject(desensitizer.desensitize(value));
            } catch (Exception e) {
                gen.writeString(DEFAULT_MASK);
            }
        } else if (value instanceof String) {
            gen.writeString(DEFAULT_MASK);
        } else {
            gen.writeObject(value);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (Objects.nonNull(property)) {
            Sensitive annotation = property.getAnnotation(Sensitive.class);
            if (Objects.nonNull(annotation)) {
                DesensitizeSerializer serializer = new DesensitizeSerializer(Object.class);
                serializer.setDesensitizer(DesensitizerFactory.getDesensitizer(annotation.strategy(), annotation.using()));
                return serializer;
            }
        }
        return prov.findNullValueSerializer(property);
    }
}