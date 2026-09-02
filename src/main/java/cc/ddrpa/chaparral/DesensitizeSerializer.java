package cc.ddrpa.chaparral;

import cc.ddrpa.chaparral.annotation.Sensitive;
import cc.ddrpa.chaparral.desensitizer.DesensitizerFactory;
import cc.ddrpa.chaparral.desensitizer.IDesensitizer;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.BeanProperty;
import tools.jackson.databind.JsonMappingException;
import tools.jackson.databind.JsonSerializer;
import tools.jackson.databind.SerializerProvider;
import tools.jackson.databind.ser.ContextualSerializer;
import tools.jackson.databind.ser.std.StdSerializer;

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

    public IDesensitizer getDesensitizer() {
        return desensitizer;
    }

    public void setDesensitizer(IDesensitizer desensitizer) {
        this.desensitizer = desensitizer;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws JacksonException {
        IDesensitizer desensitizer = getDesensitizer();
        if (Objects.nonNull(desensitizer)) {
            try {
                Object masked = desensitizer.desensitize(value);
                gen.writePOJO(masked);
            } catch (Exception e) {
                try {
                    gen.writeString(DEFAULT_MASK);
                } catch (IOException io) {
                    throw JacksonException.from(io);
                }
            }
        } else if (value instanceof String) {
            try {
                gen.writeString(DEFAULT_MASK);
            } catch (IOException io) {
                throw JacksonException.from(io);
            }
        } else {
            try {
                gen.writePOJO(value);
            } catch (IOException io) {
                throw JacksonException.from(io);
            }
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
