/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javatcpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientConnection extends Thread{

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String userNick;
    private boolean clientActive;
    
    public ClientConnection(Socket clientSocket) {
        this.clientSocket = clientSocket;
        clientActive = true;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
            closeSocket();
        }
        
    }
    
    private void closeSocket() {
        try {
            clientSocket.close();
        } catch (IOException ex1) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex1);
        }  
    }
    
    public void run(){
        String msg;
        getNick();
        out.println("Hello on chat, " + userNick);
        while(clientActive){
            msg = readMessage();
            sendMessage(msg);
        }

        closeSocket();
  
    }
    
    private void getNick(){
        userNick = readMessage();
        System.out.println("New user registered: " + userNick);        
    }
    
    private String readMessage(){
        String msg;
        try {
            msg = in.readLine();
            return msg;
        } catch (IOException ex) {
            Logger.getLogger(ClientConnection.class.getName()).log(Level.SEVERE, null, ex);
            closeSocket();
        }
        return null;
        
    }

    private void sendMessage(String msg) {
        out.println(userNick + ": " + msg);
    }
    
}
