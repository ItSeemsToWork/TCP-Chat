import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
 
public class TCPClient {
    
    static String clientName;
    static Socket clientSocket;
    static PrintWriter out;
    static BufferedReader in;
    static BufferedReader stdIn;
 
    public static void main(String[] args) throws IOException {
        
        clientSocket = new Socket("localhost", 8001);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        stdIn = new BufferedReader(new InputStreamReader(System.in));
        
        setNickname();
        
        new Thread() {
            @Override
            public void run() {
                while(true) {
                    try {
                        String mesage = in.readLine();
                        System.out.println(mesage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            }.start();
            
            
        while(true) {
            String userMessage;
            userMessage = stdIn.readLine();
            out.write(userMessage + "\n");
            out.flush();
        }
        
        
    }
    
    
    private static void setNickname() throws IOException {
        System.out.print("Press your nick: ");
        clientName = stdIn.readLine();
        out.write(clientName + "\n");
        out.flush();
    }
 
}