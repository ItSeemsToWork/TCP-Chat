import java.io.*;
import java.net.*;
import java.util.LinkedList;
 
public class TCPServer {
 
    final static int PORT = 8001;
    static LinkedList<ClientThread> clientsList = new LinkedList<>();
    private static Socket clientSocket;
    private static ServerSocket serverSocket;
    
    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Start Server on "+PORT+" port");
        while(true) {
            try {
                clientSocket = serverSocket.accept();
                System.out.println("get new connection");
                ClientThread сlientThread = new ClientThread(clientSocket);
                clientsList.add(сlientThread);
            }
            catch(IOException ioe) { 
                ioe.printStackTrace();
            }
        }
    }
}
    
    class ClientThread extends Thread {
        Socket socket;
        BufferedReader in;
        PrintWriter out;
        String name;
        
        ClientThread(Socket s) throws IOException { 
            socket = s; 
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            start();
        }
        
        public void run() {
            try {
            	name = in.readLine();
                while(true) {
                    String clientMessage = in.readLine();
                    System.out.println("log: " + name + ": " + clientMessage);
                    for (ClientThread ct : TCPServer.clientsList) {
                        ct.out.write(name+": "+clientMessage + "\n");
                        ct.out.flush();
                    }
                }
            }
        catch(Exception e) { 
            e.printStackTrace();
        }
    }
}