package jackson;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AutoValueTest
{
    public ObjectMapper getObjectMapper()
        throws Exception
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(MapperFeature.AUTO_DETECT_CREATORS);
        objectMapper.disable(MapperFeature.AUTO_DETECT_FIELDS);
        objectMapper.disable(MapperFeature.AUTO_DETECT_SETTERS);
        objectMapper.disable(MapperFeature.AUTO_DETECT_GETTERS);
        objectMapper.disable(MapperFeature.AUTO_DETECT_IS_GETTERS);
        objectMapper.disable(MapperFeature.USE_GETTERS_AS_SETTERS);
        objectMapper.disable(MapperFeature.INFER_PROPERTY_MUTATORS);
        objectMapper.disable(MapperFeature.ALLOW_FINAL_FIELDS_AS_MUTATORS);
        return objectMapper;
    }

    @Test
    public void testAutoValue()
        throws Exception
    {
        ObjectMapper objectMapper = getObjectMapper();

        AutoValueBean bean = AutoValueBean.create("a-string", 1, true);

        String beanResult = objectMapper.writeValueAsString(bean);

        Map<String, Object> result = objectMapper.readValue(beanResult, new TypeReference<Map<String, Object>>() {});

        Assert.assertEquals(3, result.size());
        Assert.assertTrue(result.containsKey("string"));
        Assert.assertTrue(result.containsKey("int"));
        Assert.assertTrue(result.containsKey("bool"));
        Assert.assertEquals("a-string", result.get("string"));
        Assert.assertEquals(1, ((Number) result.get("int")).intValue());
        Assert.assertEquals(true, ((Boolean) result.get("bool")).booleanValue());
    }

    @Test
    public void testAutoValueDisableAccessModifiers()
        throws Exception
    {
        ObjectMapper objectMapper = getObjectMapper();
        objectMapper.disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS);

        AutoValueBean bean = AutoValueBean.create("a-string", 1, true);

        String beanResult = objectMapper.writeValueAsString(bean);

        Map<String, Object> result = objectMapper.readValue(beanResult, new TypeReference<Map<String, Object>>() {});

        Assert.assertEquals(3, result.size());
        Assert.assertTrue(result.containsKey("string"));
        Assert.assertTrue(result.containsKey("int"));
        Assert.assertTrue(result.containsKey("bool"));
        Assert.assertEquals("a-string", result.get("string"));
        Assert.assertEquals(1, ((Number) result.get("int")).intValue());
        Assert.assertEquals(true, ((Boolean) result.get("bool")).booleanValue());
    }
}
