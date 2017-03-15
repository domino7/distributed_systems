package javatcpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaTcpClient {
    
    int portNumber = 12345;
    Socket socket = null;
    String nick;
    Scanner scanner = new Scanner(System.in);
    PrintWriter out;
    BufferedReader in;


    public static void main(String[] args) throws IOException {
        
        JavaTcpClient cl = new JavaTcpClient();
        System.out.println("JAVA TCP CLIENT");
        String hostName = "localhost";
        int portNumber = 12345;
        Socket socket = null;
        
        try {
            // create socket 
            socket = new Socket(hostName, portNumber);
            cl.out = new PrintWriter(socket.getOutputStream(), true);
            cl.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
            
            cl.getNick();
            cl.out.println(cl.nick);
            String response = cl.in.readLine();
            System.out.println(response);
            
            while (true){   

                // send msg, read response
                cl.sendMessage();
                cl.readMessage();

            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null){
                socket.close();
            }
        }
    }
    
    private void getNick(){
        System.out.print("Your nick: ");
        this.nick = scanner.nextLine();
    }
    
    
    private void sendMessage() {
        String msg = scanner.nextLine();
        out.println(msg);
    }

    private void readMessage() {
        String res;
        try {
            res = in.readLine();
            System.out.println(res);
        } catch (IOException ex) {
            Logger.getLogger(JavaTcpClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
