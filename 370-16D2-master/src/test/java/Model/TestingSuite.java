package Model;

import java.util.HashMap;

/**
 * Created by Sam on 2016-11-26.
 *
 * A suite of commonly used jsons to test other classes.
 */

public class TestingSuite {

    private static HashMap<String, String> availableJsons;

    public TestingSuite(){
        availableJsons = new HashMap<>();
        /*
      Valid json strings to create robots for use in Gameboard Testing
     */
        String tankJson = "{ \"team\" : \"testTeam\"\n" +
                ", \"class\" : \"Tank\"\n" +
                ", \"name\" : \"test tank\"\n" +
                ", \"matches\" : 0\n" +
                ", \"wins\" : 0\n" +
                ", \"losses\" : 0\n" +
                ", \"executions\" : 0\n" +
                ", \"lived\" : 0\n" +
                ", \"died\" : 0\n" +
                ", \"absorbed\" : 0\n" +
                ", \"killed\" : 0\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] }";
        availableJsons.put("tankJson", tankJson);
        String sniperJson = "{ \"team\" : \"testTeam\"\n" +
                ", \"class\" : \"Sniper\"\n" +
                ", \"name\" : \"test sniper\"\n" +
                ", \"matches\" : 0\n" +
                ", \"wins\" : 0\n" +
                ", \"losses\" : 0\n" +
                ", \"executions\" :0\n" +
                ", \"lived\" : 0\n" +
                ", \"died\" : 0\n" +
                ", \"absorbed\" : 0\n" +
                ", \"killed\" : 0\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] }";
        availableJsons.put("sniperJson", sniperJson);
        String scoutJson = "{ \"team\" : \"testTeam\"\n" +
                ", \"class\" : \"Scout\"\n" +
                ", \"name\" : \"test scout\"\n" +
                ", \"matches\" : 0\n" +
                ", \"wins\" : 0\n" +
                ", \"losses\" : 0\n" +
                ", \"executions\" : 0\n" +
                ", \"lived\" : 0\n" +
                ", \"died\" : 0\n" +
                ", \"absorbed\" : 0\n" +
                ", \"killed\" : 0\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] }";
        availableJsons.put("scoutJson", scoutJson);
        String json = "{ \"team\" : \"A1\"\n" +
                ", \"class\" : \"Sniper\"\n" +
                ", \"name\" : \"a sniper\"\n" +
                ", \"matches\" : 43\n" +
                ", \"wins\" : 12\n" +
                ", \"losses\" : 38\n" +
                ", \"executions\" : 55\n" +
                ", \"lived\" : 10\n" +
                ", \"died\" : 45\n" +
                ", \"absorbed\" : 93\n" +
                ", \"killed\" : 12\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] }";
        availableJsons.put("json", json);
        String badJSON2 = "{ \"class\" : \"Sniper\"\n" +
                ", \"name\" : \"a sniper\"\n" +
                ", \"matches\" : 43\n" +
                ", \"wins\" : 12\n" +
                ", \"losses\" : 38\n" +
                ", \"executions\" : 55\n" +
                ", \"lived\" : 10\n" +
                ", \"died\" : 45\n" +
                ", \"absorbed\" : 93\n" +
                ", \"killed\" : 12\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] }";
        availableJsons.put("badJSON2", badJSON2);
        String badJSON3 = "{ \"team\" : \"A1\"\n" +
                ", \"name\" : \"a sniper\"\n" +
                ", \"matches\" : 43\n" +
                ", \"wins\" : 12\n" +
                ", \"losses\" : 38\n" +
                ", \"executions\" : 55\n" +
                ", \"lived\" : 10\n" +
                ", \"died\" : 45\n" +
                ", \"absorbed\" : 93\n" +
                ", \"killed\" : 12\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] }";
        availableJsons.put("badJSON3", badJSON3);
        String badJSON4 = "{ \"team\" : \"A1\"\n" +
                ", \"class\" : \"Sniper\"\n" +
                ", \"matches\" : 43\n" +
                ", \"wins\" : 12\n" +
                ", \"losses\" : 38\n" +
                ", \"executions\" : 55\n" +
                ", \"lived\" : 10\n" +
                ", \"died\" : 45\n" +
                ", \"absorbed\" : 93\n" +
                ", \"killed\" : 12\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] }";
        availableJsons.put("badJSON4", badJSON4);
        String badJSON5 = "{ \"team\" : \"A1\"\n" +
                ", \"class\" : \"Sniper\"\n" +
                ", \"name\" : \"a sniper\"\n" +
                ", \"wins\" : 12\n" +
                ", \"losses\" : 38\n" +
                ", \"executions\" : 55\n" +
                ", \"lived\" : 10\n" +
                ", \"died\" : 45\n" +
                ", \"absorbed\" : 93\n" +
                ", \"killed\" : 12\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] }";
        availableJsons.put("badJSON5", badJSON5);
        String badJSON6 = "{ \"team\" : \"A1\"\n" +
                ", \"class\" : \"Sniper\"\n" +
                ", \"name\" : \"a sniper\"\n" +
                ", \"matches\" : 43\n" +
                ", \"losses\" : 38\n" +
                ", \"executions\" : 55\n" +
                ", \"lived\" : 10\n" +
                ", \"died\" : 45\n" +
                ", \"absorbed\" : 93\n" +
                ", \"killed\" : 12\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] }";
        availableJsons.put("badJSON6", badJSON6);
        String badJSON7 = "{ \"team\" : \"A1\"\n" +
                ", \"class\" : \"Sniper\"\n" +
                ", \"name\" : \"a sniper\"\n" +
                ", \"matches\" : 43\n" +
                ", \"wins\" : 12\n" +
                ", \"executions\" : 55\n" +
                ", \"lived\" : 10\n" +
                ", \"died\" : 45\n" +
                ", \"absorbed\" : 93\n" +
                ", \"killed\" : 12\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] }";
        availableJsons.put("badJSON7", badJSON7);
        String badJSON8 = "{ \"team\" : \"A1\"\n" +
                ", \"class\" : \"Sniper\"\n" +
                ", \"name\" : \"a sniper\"\n" +
                ", \"matches\" : 43\n" +
                ", \"wins\" : 12\n" +
                ", \"losses\" : 38\n" +
                ", \"lived\" : 10\n" +
                ", \"died\" : 45\n" +
                ", \"absorbed\" : 93\n" +
                ", \"killed\" : 12\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] }";
        availableJsons.put("badJSON8", badJSON8);
        String badJSON9 = "{ \"team\" : \"A1\"\n" +
                ", \"class\" : \"Sniper\"\n" +
                ", \"name\" : \"a sniper\"\n" +
                ", \"matches\" : 43\n" +
                ", \"wins\" : 12\n" +
                ", \"losses\" : 38\n" +
                ", \"executions\" : 55\n" +
                ", \"died\" : 45\n" +
                ", \"absorbed\" : 93\n" +
                ", \"killed\" : 12\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] }";
        availableJsons.put("badJSON9", badJSON9);
        String badJSON10 = "{ \"team\" : \"A1\"\n" +
                ", \"class\" : \"Sniper\"\n" +
                ", \"name\" : \"a sniper\"\n" +
                ", \"matches\" : 43\n" +
                ", \"wins\" : 12\n" +
                ", \"losses\" : 38\n" +
                ", \"executions\" : 55\n" +
                ", \"lived\" : 10\n" +
                ", \"absorbed\" : 93\n" +
                ", \"killed\" : 12\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] }";
        availableJsons.put("badJSON10", badJSON10);
        String badJSON11 = "{ \"team\" : \"A1\"\n" +
                ", \"class\" : \"Sniper\"\n" +
                ", \"name\" : \"a sniper\"\n" +
                ", \"matches\" : 43\n" +
                ", \"wins\" : 12\n" +
                ", \"losses\" : 38\n" +
                ", \"executions\" : 55\n" +
                ", \"lived\" : 10\n" +
                ", \"died\" : 45\n" +
                ", \"killed\" : 12\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] }";
        availableJsons.put("badJSON11", badJSON11);
        String badJSON12 = "{ \"team\" : \"A1\"\n" +
                ", \"class\" : \"Sniper\"\n" +
                ", \"name\" : \"a sniper\"\n" +
                ", \"matches\" : 43\n" +
                ", \"wins\" : 12\n" +
                ", \"losses\" : 38\n" +
                ", \"executions\" : 55\n" +
                ", \"lived\" : 10\n" +
                ", \"died\" : 45\n" +
                ", \"absorbed\" : 93\n" +
                ", \"moved\" : 0\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] }";
        availableJsons.put("badJSON12", badJSON12);
        String badJSON13 = "{ \"team\" : \"A1\"\n" +
                ", \"class\" : \"Sniper\"\n" +
                ", \"name\" : \"a sniper\"\n" +
                ", \"matches\" : 43\n" +
                ", \"wins\" : 12\n" +
                ", \"losses\" : 38\n" +
                ", \"executions\" : 55\n" +
                ", \"lived\" : 10\n" +
                ", \"died\" : 45\n" +
                ", \"absorbed\" : 93\n" +
                ", \"killed\" : 12\n" +
                ", \"code\" : [ \"variable lastShot ; \"\n" +
                ", \"0 lastShot ! \"\n" +
                ", \": play ( -- ) \"\n" +
                ", \" 0 begin dup lastShot ? + 1 6 /mod drop \"\n" +
                ", \" empty? if .\\\"no one there\\\" \"\n" +
                ", \" else dup lastShot ! \"\n" +
                ", \" dup 1 shoot! leave \"\n" +
                ", \" then 1 + dup 5 > \"\n" +
                ", \" until drop ; \"\n" +
                "] }";
        availableJsons.put("badJSON13", badJSON13);
        String badJSON14 = "{ \"team\" : \"A1\"\n" +
                ", \"class\" : \"Sniper\"\n" +
                ", \"name\" : \"a sniper\"\n" +
                ", \"matches\" : 43\n" +
                ", \"wins\" : 12\n" +
                ", \"losses\" : 38\n" +
                ", \"executions\" : 55\n" +
                ", \"lived\" : 10\n" +
                ", \"died\" : 45\n" +
                ", \"absorbed\" : 93\n" +
                ", \"killed\" : 12\n" +
                ", \"moved\" : 0\n" +
                "}";
        availableJsons.put("badJSON14", badJSON14);



    }


    /**
     *  getJSON returns the input corespondent to a pre-populated JSON of the same name
     *
     * @param key is the name of the pre-populated JSON
     * @throws IllegalArgumentException if the key does not exist
     * @return A pre-populated JSON
     */

    public static String getJSON(String key) throws IllegalArgumentException{

        if (availableJsons.containsKey(key)) {
            return availableJsons.get(key);
       }
       else{
           throw new IllegalArgumentException("JSON key is not valid");
       }

    }


}
