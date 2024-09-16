import java.util.HashMap;
import java.util.Map;

public class JSONObject implements JSONElement{
    private Map<String, JSONElement> valueMap;

    public JSONObject(Map<String, JSONElement> valueMap) {
        this.valueMap = valueMap;
    }

    @Override
    public Object getValue() {

        Map<String, Object> result = new HashMap<>();
        valueMap.forEach((key, value) -> result.put(key, value.getValue()));
        return result;
    }
}
