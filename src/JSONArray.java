import java.util.List;
import java.util.stream.Collectors;

public class JSONArray implements JSONElement{
    private List<JSONElement> elements;

    public JSONArray(List<JSONElement> elements) {
        this.elements = elements;
    }

    @Override
    public Object getValue() {
        return elements.stream().map(JSONElement::getValue).collect(Collectors.toList());
    }
}
