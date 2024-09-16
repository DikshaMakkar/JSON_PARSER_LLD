import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONParser {
    private int index;
    private String json;

    private static final char OPEN_CURLY_BRACKET = '{';
    private static final char CLOSED_CURLY_BRACKET = '}';
    private static final char OPEN_SQUARE_BRACKET = '[';
    private static final char CLOSED_SQUARE_BRACKET = ']';
    private static final char DOUBLE_QUOTE = '"';
    private static final char COMMA = ',';
    private static final char COLON = ':';
    public JSONElement parse(String jsonStr) {
        this.index = 0;
        this.json = jsonStr;
        skipWhiteSpace();
        return parseValue();
    }

    private JSONElement parseValue(){
        char currChar = json.charAt(index);

        if(currChar == OPEN_CURLY_BRACKET){
            return parseObject();
        } else if(currChar == OPEN_SQUARE_BRACKET){
            return parseArray();
        } else if (currChar == DOUBLE_QUOTE) {
            return parseString();
        } else if (Character.isDigit(currChar) || currChar == '-') {
            return parseNumber();
        } else if (currChar == 't' || currChar == 'f') {
            return parseBoolean();
        } else if (currChar == 'n') {
            return parseNull();
        }
        throw new RuntimeException();
    }

    private JSONObject parseObject() {
        Map<String, JSONElement> valueMap = new HashMap<>();
        consume(OPEN_CURLY_BRACKET);
        skipWhiteSpace();

        while(json.charAt(index) != CLOSED_CURLY_BRACKET){
            String valueMapKey = parseString().getValue().toString();
            skipWhiteSpace();
            consume(COLON);
            skipWhiteSpace();
            JSONElement valueMapValue = parseValue();
            valueMap.put(valueMapKey, valueMapValue);
            skipWhiteSpace();

            if(json.charAt(index) == COMMA){
                consume(COMMA);
                skipWhiteSpace();
            }
        }

        consume(CLOSED_CURLY_BRACKET);
        return new JSONObject(valueMap);
    }

    private JSONArray parseArray(){
        List<JSONElement> valueList = new ArrayList<>();
        consume(OPEN_SQUARE_BRACKET);
        skipWhiteSpace();

        while(json.charAt(index) != CLOSED_SQUARE_BRACKET){
            JSONElement value = parseValue();
            valueList.add(value);
            skipWhiteSpace();
            if(json.charAt(index) == COMMA) {
                consume(COMMA);
                skipWhiteSpace();
            }
        }
        consume(CLOSED_SQUARE_BRACKET);
        return new JSONArray(valueList);
    }

    private JSONString parseString(){
        consume(DOUBLE_QUOTE);
        StringBuilder str = new StringBuilder();
        while(json.charAt(index) != DOUBLE_QUOTE){
            str.append(json.charAt(index));
            index++;
        }
        consume(DOUBLE_QUOTE);
        return new JSONString(str.toString());
    }

    private JSONNumber parseNumber(){
        StringBuilder str = new StringBuilder();
        while(Character.isDigit(json.charAt(index)) || json.charAt(index) == '.'){
            str.append(json.charAt(index));
            index++;
        }
        String number = str.toString();
        if(number.contains(".")){
            return new JSONNumber(Double.parseDouble(number));
        } else{
            return new JSONNumber(Long.parseLong(number));
        }
    }

    private JSONBoolean parseBoolean(){
        String word = consumeWord();
        if(word.equals("true")){
            return new JSONBoolean(true);
        } else if (word.equals("false")) {
            return new JSONBoolean(false);
        }
        throw new RuntimeException();
    }

    private JSONElement parseNull(){
        consumeWord();
        return null;
    }

    private void consume(char element){
        if(json.charAt(index) == element){
            index++;
        } else {
            throw new RuntimeException();
        }
    }

    private String consumeWord(){
        StringBuilder str = new StringBuilder();
        while(Character.isLetter(json.charAt(index))){
            str.append(json.charAt(index));
            index++;
        }
        return str.toString();
    }

    public void skipWhiteSpace(){
        while(Character.isWhitespace(json.charAt(index))){
            index++;
        }
    }
}
