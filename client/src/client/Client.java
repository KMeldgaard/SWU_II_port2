
package client;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Client {
    
    private int portNumber;
    private String ip;
    private Socket connection;
    private ObjectOutputStream output;
    private DataOutputStream writer;
    
    public Client(String ip, int portNumber){
        this.ip = ip;
        this.portNumber = portNumber;
    }
    //virker 
    public void connect(){
        try{
            connection = new Socket(this.ip, this.portNumber);
            //writer = new PrintWriter(connection.getOutputStream(), true);
            output = new ObjectOutputStream(connection.getOutputStream());
            writer = new DataOutputStream(connection.getOutputStream());
            System.out.println("you connected to the server");
        }
        catch(IOException ex){ //virker 
            System.out.println("failed to connect to server");    
            ex.printStackTrace();
        }
        
    }
    //skal sende strings til severen
    public void writeString(String message){
        if(connection.isConnected()){
            try {
                writer.writeBytes(message);
                writer.flush();
            } 
            catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //skal sende objekter til serveren 
    public void writePerson(Person person){
        if(connection.isConnected()){
            try {
                output.writeObject(person);
                //writer.print(person);
                //writer.flush();
            }
            catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //skal læse strings fra serveren 
    public String inFromServer(){
        String message = ""; 
        try{
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            message = inFromServer.readLine();    
        }
        catch(IOException ie){
            ie.printStackTrace();
        }
        return message;
    }
    //problemer med serveren, den kaster nogle exceptions når jeg disconnecter
    public void disConnect(){
        try{
            connection.close();
            System.out.println("you closed connection to the server");
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

}
