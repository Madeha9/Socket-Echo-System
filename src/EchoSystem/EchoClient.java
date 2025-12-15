package EchoSystem;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class EchoClient {
    //TCP
    static void main(String[] args) throws IOException {
        String valueEnvPort = System.getenv("PORT");
        int port = parseInt(valueEnvPort);
// with try catch, no need close() need update and config
        InputStream inStream;
        OutputStream outStream;
        try (Socket connect = new Socket("localhost", port)) {
            System.out.println("connected successfully, please type something , type (exit) to exit: ");

            //input and output streams read and write
            inStream = connect.getInputStream();
            outStream = connect.getOutputStream();

            while (true) {
                Scanner scanner = new Scanner(System.in);
                PrintWriter sendMessage = new PrintWriter(outStream, true);
                BufferedReader readMessage = new BufferedReader(new InputStreamReader(inStream));
                //Apply the exit

                //write text from the keyboard
                String text = scanner.nextLine();
                sendMessage.println(text);
                //read Echo from the server
                String echoResponse = readMessage.readLine();
                System.out.println(" Server-Echo:  " + echoResponse);

                if (text.equalsIgnoreCase("exit")) {
                    System.out.println(" connection is closed");
                    break;
                }
            }
            connect.close();
        } catch (IOException e) {
            //close the Resources
            e.printStackTrace();
            //throw new RuntimeException(e);
        }

    }
}

