package EchoSystem;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

//Server should save the messages in Log File (use read + Write )
public class EchoServer {

    static void main(String[] args) throws IOException, InterruptedException {
        try {
            //Exit Config-proprities

            String exitCommand;
            Properties prop = new Properties();
            prop.load(new FileInputStream("config.properties"));
            exitCommand = prop.getProperty("exitCommand");
            if (exitCommand == null) {
                exitCommand = "exit";
            }
            System.out.println("Exit Command is : " + exitCommand);
            System.out.println("Set the port as an environment variable in Edit Configuration or through the terminal");

            System.out.println("The server is ready, waiting for Client connection... . ");

            //Set the port through the Terminal

            String portEnv = System.getenv("PORT");
            int port = Integer.parseInt(portEnv);
            //TCP
            ServerSocket myserverSocket;
            myserverSocket = new ServerSocket(port);

            //wait for a user to connect only one user can connect
            while (true) {
                Socket client = myserverSocket.accept();
                System.out.println("Client connected : " + client.getInetAddress().getHostAddress());
                //input and output streams & Log File
                InputStream inStream = client.getInputStream();
                OutputStream outStream = client.getOutputStream();
                BufferedReader breader = new BufferedReader(new InputStreamReader(inStream));
                PrintWriter pwriter = new PrintWriter(outStream, true);
                String clientMessage;
                //as longer as the user types
                //create the log file for the server to save Chat information
                BufferedWriter chatHistory = new BufferedWriter(new FileWriter("server.log", true));
                String time = java.time.LocalDateTime.now().toString();
                String clientIP = client.getInetAddress().getHostAddress();
                while ((clientMessage = breader.readLine()) != null) {
//HTTPS request
                    if (clientMessage.replaceAll("\\s+", " ").startsWith("/cmd wget ")) {
                        String url = clientMessage.substring(10).trim();
                        System.out.println("Get URL : " + url);
                        String httpIno = httpGet(url);
                        pwriter.println(httpIno);
                        String httpLog = time + " | " + clientIP + " | " + "CMD /cmd wget URL " + url + " | " + "BYTES COUNT=" + httpIno.getBytes(StandardCharsets.UTF_8).length;
                        chatHistory.write(httpLog);
                        chatHistory.newLine();
                        chatHistory.flush();

                    } else {
                        String logInfo = time + "|" + clientIP + "|" + clientMessage;

                        chatHistory.write(logInfo);
                        chatHistory.newLine();
                        chatHistory.flush();
                        pwriter.println(clientMessage);
                        //URL extraxt
                    }

                    if (clientMessage.equalsIgnoreCase("exit")) {
                        System.out.println(" client " + " " + client.getInetAddress().getHostAddress() + " " + "has been exited ");
                        System.out.println("waiting a new Client........ ");
                        break;
                    }
                }
                client.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    // HTTP request Method
    static String httpGet(String url) throws IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        } catch (IllegalArgumentException e) {
            return "CMD-ERROR wget URL= reason=" + e.getMessage();
        }
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();
            int bodyLength = responseBody.length();
            int byteCount = responseBody.getBytes(StandardCharsets.UTF_8).length;
            //System.out.println( byteCount);
            System.out.println(response.body());
            return "CMD-RESULT wget URL = " + url + " " + "length=" + " " + bodyLength + " " + "status code : " + " " + response.statusCode() + "  " + " Bytes : " + byteCount;


        } catch (IOException e) {
            return "CMD-ERROR wget URL= reason=" + e.getMessage();
        }

    }
}
