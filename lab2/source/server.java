import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;  
import java.util.Date;  

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

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
	        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");  
            Date date = new Date();  
            Map<String, String> params = queryToMap(t.getRequestURI().getQuery()); 
            String response = "";
            if (params == null) {
                response = "Hello World from java!\n";
            } else if (params.containsKey("time")) {
                response = formatter.format(date);
            } else if (params.containsKey("rev") && params.containsKey("str")) {
                reverse = params.get("str");
            }
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
            System.out.println("Served hello world...");
            System.out.println(formatter.format(date));
            System.out.println("Size = " + params.size());
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

        static public String reverse (String text) {
            String nstr="";
            char ch;
                
            for (int i=0; i<text.length(); i++)
            {
                ch = sttextr.charAt(i);
                nstr= ch + nstr;
            }

            return nstr;
        }
    }
}
