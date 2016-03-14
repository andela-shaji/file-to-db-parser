package checkpoint.andela.parser;

/**
 * Created by suadahaji on 3/10/16.
 */
public class AttributeValuePair {
    private String key = null;

    private String value = null;

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
