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
    private JavaTcpServer sr;
    
    public ClientConnection(JavaTcpServer sr, Socket clientSocket) {
        this.sr = sr;
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
            sendMessage("exit");
            clientSocket.close();
            System.out.println(userNick +" socket closed");
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
            parseMessage(msg);
            
        }
  
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

    public void sendMessage(String msg) {
        out.println(msg);
    }

    private void parseMessage(String msg) {
        if (msg.toLowerCase().equals("exit")){
            clientActive = false;
            closeSocket();
        } else if (msg.equals("M")){
            sendMessage("udp sending");
            sendUdpMsg();
        } else if(msg.equals("N")){
            sendMessage("udp multicast sending");
        } else {
            sr.sendOnChat(this.getId() , this.userNick.toUpperCase() + ": " + msg);
        }
            
    }

    private void sendUdpMsg() {
        prepareChannel();
        sendAsciArt();
    }

    private void prepareChannel() {
        
    }

    private void sendAsciArt() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
