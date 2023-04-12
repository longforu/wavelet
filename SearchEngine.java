import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    String[] things = new String[10];

    public String handleRequest(URI url) {
        if (url.getPath().equals("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                for (int i = 0; i < things.length; i++) {
                    if (things[i] == null) {
                        things[i] = parameters[1];
                        return String.format("Added %s to the list!", parameters[1]);
                    }
                }
            }
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                  String[] results = new String[10];
                  for (int i = 0; i < things.length; i++) {
                    if (things[i] != null && things[i].contains(parameters[1])) {
                      results[i] = things[i];
                    }
                  }
                  // only return the non null values
                  String ret = "";
                  for (int i = 0; i < results.length; i++) {
                    if (results[i] != null) {
                      ret += results[i] + ",";
                    }
                  }
                  return ret;
                }
            }
            
        }
        return "404 Not Found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
