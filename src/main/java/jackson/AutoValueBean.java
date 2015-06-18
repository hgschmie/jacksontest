package jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class AutoValueBean
{
    @JsonCreator
    public static AutoValueBean create(@JsonProperty("string") String str,
                                       @JsonProperty("int") int val,
                                       @JsonProperty("bool") boolean bool)
    {
        return new AutoValue_AutoValueBean(str, val, bool);
    }

    @JsonProperty
    public abstract String getString();

    @JsonProperty
    public abstract int getInt();

    @JsonProperty
    public abstract boolean getBool();
}
