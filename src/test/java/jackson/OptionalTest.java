package jackson;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.google.common.collect.ImmutableMap;

public class OptionalTest
{
    public ObjectMapper getObjectMapper(JsonInclude.Include mode)
        throws Exception
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new GuavaModule().configureAbsentsAsNulls(true));
        objectMapper.registerModule(new Jdk8Module().configureAbsentsAsNulls(true));
        objectMapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
        objectMapper.setSerializationInclusion(mode);
        return objectMapper;
    }

    @Test
    public void testOptionalAbsentNonNull()
        throws Exception
    {
        ObjectMapper objectMapper = getObjectMapper(JsonInclude.Include.NON_NULL);

        GuavaTestBean guavaBean = new GuavaTestBean(com.google.common.base.Optional.absent(), ImmutableMap.<String, String>of());
        Jdk8TestBean jdk8Bean = new Jdk8TestBean(java.util.Optional.<String>empty(), ImmutableMap.<String, String>of());

        String guavaResult = objectMapper.writeValueAsString(guavaBean);
        String jdk8Result = objectMapper.writeValueAsString(jdk8Bean);

        assertEquals("guava vs. jdk8 failed", guavaResult, jdk8Result);
        assertEquals("result was incorrect", "{\"map\":{}}", guavaResult);
    }

    @Test
    public void testMapOptionalAbsentNonNull()
        throws Exception
    {
        ObjectMapper objectMapper = getObjectMapper(JsonInclude.Include.NON_NULL);

        ImmutableMap<String, Object> guavaMap = ImmutableMap.<String, Object>of(
            "key", com.google.common.base.Optional.absent(),
            "map", ImmutableMap.<String, String>of());
        ImmutableMap<String, Object> jdk8Map = ImmutableMap.<String, Object>of(
            "key", java.util.Optional.empty(),
            "map", ImmutableMap.<String, String>of());

        String guavaResult = objectMapper.writeValueAsString(guavaMap);
        String jdk8Result = objectMapper.writeValueAsString(jdk8Map);

        assertEquals("guava vs. jdk8 failed", guavaResult, jdk8Result);
        assertEquals("result was incorrect", "{\"map\":{}}", guavaResult);
    }

    @Test
    public void testGuavaOptionalAbsentBeanVsMap()
        throws Exception
    {
        ObjectMapper objectMapper = getObjectMapper(JsonInclude.Include.NON_NULL);

        GuavaTestBean guavaBean = new GuavaTestBean(com.google.common.base.Optional.absent(), ImmutableMap.<String, String>of());
        ImmutableMap<String, Object> guavaMap = ImmutableMap.<String, Object>of(
            "key", com.google.common.base.Optional.absent(),
            "map", ImmutableMap.<String, String>of());

        String guavaBeanResult = objectMapper.writeValueAsString(guavaBean);
        String guavaMapResult = objectMapper.writeValueAsString(guavaMap);

        assertEquals("guava bean vs. map failed", guavaBeanResult, guavaMapResult);
        assertEquals("result was incorrect", "{\"map\":{}}", guavaBeanResult);
    }

    @Test
    public void testJdk8OptionalAbsentBeanVsMap()
        throws Exception
    {
        ObjectMapper objectMapper = getObjectMapper(JsonInclude.Include.NON_NULL);

        Jdk8TestBean jdk8Bean = new Jdk8TestBean(java.util.Optional.<String>empty(), ImmutableMap.<String, String>of());
        ImmutableMap<String, Object> jdk8Map = ImmutableMap.<String, Object>of(
            "key", java.util.Optional.empty(),
            "map", ImmutableMap.<String, String>of());

        String jdk8BeanResult = objectMapper.writeValueAsString(jdk8Bean);
        String jdk8MapResult = objectMapper.writeValueAsString(jdk8Map);

        assertEquals("jdk8 bean vs. map failed", jdk8BeanResult, jdk8MapResult);
        assertEquals("result was incorrect", "{\"map\":{}}", jdk8BeanResult);
    }

    public static class GuavaTestBean
    {
        private final com.google.common.base.Optional<String> key;
        private final Map<String, String> map;

        @JsonCreator
        public GuavaTestBean(@JsonProperty("key") com.google.common.base.Optional<String> key,
                             @JsonProperty("map") Map<String, String> map)
        {
            this.key = key;
            this.map = map;
        }

        @JsonProperty
        public com.google.common.base.Optional<String> getKey()
        {
            return key;
        }

        @JsonProperty
        public Map<String, String> getMap()
        {
            return map;
        }
    }

    public static class Jdk8TestBean
    {
        private final java.util.Optional<String> key;
        private final Map<String, String> map;

        @JsonCreator
        public Jdk8TestBean(@JsonProperty("key") java.util.Optional<String> key,
                            @JsonProperty("map") Map<String, String> map)
        {
            this.key = key;
            this.map = map;
        }

        @JsonProperty
        public java.util.Optional<String> getKey()
        {
            return key;
        }

        @JsonProperty
        public Map<String, String> getMap()
        {
            return map;
        }
    }
}
