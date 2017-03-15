package javatcpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaTcpServer {
    
    ServerSocket serverSocket = null;
    int portNumber = 12345;
    private ArrayList<ClientConnection> clientList;
    
    private void listenForClients(){
        try {
            Socket clientSocket = this.serverSocket.accept();
            
            ClientConnection con = new ClientConnection(clientSocket);
            con.start();
            clientList.add(con);
            
        } catch (IOException ex) {
            Logger.getLogger(JavaTcpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) throws IOException {
        
        JavaTcpServer sr = new JavaTcpServer();
        sr.portNumber = 12345;
        sr.clientList = new ArrayList<ClientConnection>();
     
        
        
        try {
            // create socket
            sr.serverSocket = new ServerSocket(sr.portNumber);
            System.out.println("JAVA TCP SERVER STARTED"); 
            
            while(true){
                
                sr.listenForClients();
       
            }
        } catch (IOException e) {            
            e.printStackTrace();
        }
        finally{
            if (sr.serverSocket != null){
                sr.serverSocket.close();
            }
        }
    }
    
}
