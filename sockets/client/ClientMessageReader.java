/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javatcpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

class ClientMessageReader extends Thread {
    private Socket socket;
    private BufferedReader in;
    private boolean clientActive;
    
    public ClientMessageReader(Socket socket) {
        this.socket = socket;
        this.clientActive = true;
        try {  
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(ClientMessageReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void readMessage() {
        String res;
        try {
            res = in.readLine();
            if (res.equals("exit")){
                clientActive = false;
                socket.close();
            } else {
                System.out.println(res);
            }
        } catch (IOException ex) {
            Logger.getLogger(JavaTcpClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run(){
        while(clientActive){
            readMessage();
        }
    }
    
}
