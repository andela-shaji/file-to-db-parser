package checkpoint.andela.parser;

/**
 * Created by suadahaji.
 */
public class AttributeValuePair {
    private String key = null;

    private String value = null;

    public AttributeValuePair() {
    }

    public AttributeValuePair(String key, String value) {
        setKey(key);
        setValue(value);
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
