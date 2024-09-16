public class JSONParserApp {
    public static void main(String[] args){
        String jsonStr = "{\"name\": \"test\", \"age\": 25, \"hobbies\": [\"painting\", \"reading\"], \"isAdmin\": true, \"scores\": [10, 20, 30]}";
        JSONParser jsonParser = new JSONParser();
        JSONElement jsonElement = jsonParser.parse(jsonStr);
        System.out.println(jsonElement.getValue());
    }
}
