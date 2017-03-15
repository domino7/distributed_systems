package javatcpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaTcpServer {
    
    ServerSocket serverSocket = null;
    DatagramSocket dSocket = null; 
    int portNumber = 12345;
    private ArrayList<ClientConnection> clientList;
    private boolean serverIsRunning;
    
    private void listenForClients(){
        ExecutorService executor = Executors.newFixedThreadPool(10);
        while(serverIsRunning){
        
            try {
                Socket clientSocket = this.serverSocket.accept();
                Runnable con = new ClientConnection(this, clientSocket);
                executor.execute(con);
                clientList.add((ClientConnection) con);
                
            } catch (IOException ex) {
                Logger.getLogger(JavaTcpServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        
        JavaTcpServer sr = new JavaTcpServer();
        sr.portNumber = 12345;
        sr.clientList = new ArrayList<ClientConnection>();
     
        
        
        try {
            // create socket
            sr.serverSocket = new ServerSocket(sr.portNumber);
            sr.dSocket = new DatagramSocket(sr.portNumber);
            
            System.out.println("JAVA SERVER STARTED"); 
            
            sr.serverIsRunning = true;
            sr.listenForClients();
        } catch (IOException e) {            
            e.printStackTrace();
        }
        finally{
            if (sr.serverSocket != null){
                sr.serverSocket.close();
            }
        }
    }

    void sendOnChat(long id, String msg) {
        for(ClientConnection cc: clientList){
            if (cc.getId() != id){
                cc.sendMessage(msg);
            }
        }
    }
    
}
