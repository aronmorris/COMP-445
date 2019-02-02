package httpc;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * There was some repetition between the GET and POST handlers so to maintain DRY, this helper class was made
 * and the repeated methods delegated here
 */
public class HeaderHelper {

    private static HashMap<String, String> properties;

    //Acquires headers from the program arguments and puts them in a map for later
    public static HashMap<String, String> generateHeaders(String[] args) {

        String key, value;

        properties = new HashMap<>();

        //exclude final arg which contains " : " as part of url http://etc.tld
        for (int i = 0; i < args.length - 1; i++) {

            if (args[i].contains(":")) {

                key = args[i].substring(0, args[i].indexOf(':'));

                value = args[i].substring(args[i].indexOf(':') + 1);

                properties.put(key, value);

            }

        }

        return properties;

    }
    //acquires the headers received from the request and prints to console
    public static void printHTTPHeaders(HttpURLConnection con) {

        Map<String, List<String>> headers = con.getHeaderFields();

        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {

            System.out.println(entry.getKey() + ": " + entry.getValue());

        }

        System.out.println();

    }

}
