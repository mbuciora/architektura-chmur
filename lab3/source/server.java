import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;  
import java.util.Date;  

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Test {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(4080), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            Map<String, String> params = queryToMap(t.getRequestURI().getQuery()); 
            String response = "";

            if (params == null) {
                response = "Hello World from java!\n";
            } else if (params.get("cmd").equals("time")) {
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");  
                Date date = new Date(); 
                response = formatter.format(date);
            } else if (params.get("cmd").equals("rev")) {
                String str = params.get("str");
                Integer lowerCase = 0;
                Integer upperCase = 0;
                Integer numbers = 0;
                Integer specialCharacters = 0;

            	for (int i=0; i<str.length(); i++) {
                    char ch= str.charAt(i);
                    if (ch >= 'A' && ch <= 'Z')
                        upperCase++;
                    else if (ch >= 'a' && ch <= 'z')
                        lowerCase++;
                    else if (ch >= '0' && ch <= '9')
                        numbers++;
                    else
                        specialCharacters++;
                }
                
                response =  "{\"lowercase\":" + lowerCase.toString() + ",\"uppercase\":" + upperCase.toString() + ",\"digits\":" + numbers.toString() + ",\"special\":" + specialCharacters.toString() + "}";
            }

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        static public Map<String, String> queryToMap(String query) {
            if(query == null) {
                return null;
            }
            Map<String, String> result = new HashMap<>();
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(entry[0], entry[1]);
                }else{
                    result.put(entry[0], "");
                }
            }
            return result;
        }
    }
}
