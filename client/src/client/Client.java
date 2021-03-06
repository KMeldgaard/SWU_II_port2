
package client;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import Server.Request;
import java.util.ArrayList;

/**
 * Læg mærke til at request reelt ikke bruges til noget pt!
 * @author Mads & Kasper
 */

public class Client {
    
    private int portNumber;
    private String ip;
    private int req;
    private Socket connection;
    private ObjectOutputStream output;
    private DataOutputStream writer;
    private String server_msg;
    
    public Client(String ip, int portNumber, int req){
        this.ip = ip;
        this.portNumber = portNumber;
        this.req = req;
    }
    //virker 
    public boolean connect(){
        boolean con;
        try{
            connection = new Socket(this.ip, this.portNumber);
            //writer = new PrintWriter(connection.getOutputStream(), true);
            output = new ObjectOutputStream(connection.getOutputStream());
            writer = new DataOutputStream(connection.getOutputStream());
            writer.flush();
            System.out.println("you connected to the server");
            con = true;
        }
        catch(IOException ex){ //virker 
            System.out.println("failed to connect to server");    
            ex.printStackTrace();
            con = false;
        }
        return con;
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
    
    
   /* public void sendReq(){
        if(connection.isConnected()){
            try {
                System.out.println("here");
                writer.writeBytes(Integer.toString(req)); //sender request attribut som int
                System.out.println("here2");
                writer.flush();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return;
    }*/
    
    //bruges pt ikke!
    public void sendReq(){
        try {
            System.out.println("here");
            OutputStreamWriter toClient = new OutputStreamWriter(connection.getOutputStream());
            BufferedWriter buf = new BufferedWriter(toClient);
            buf.write(Integer.toString(req));
            buf.flush();
            System.out.println("here2");
        } 
        catch (IOException ex) {
            ex.printStackTrace();
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
    //bruges pt ikke!
    public void inFromServer(){
        try{
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            server_msg = inFromServer.readLine();    
        }
        catch(IOException ie){
            server_msg = "Something went wrong!";
            System.out.println(server_msg);
            ie.printStackTrace();
        }
        return;
    }
    //problemer med serveren, den kaster nogle exceptions når der disconnectes
    public void disConnect(){
        try{
            connection.close();
            System.out.println("you closed connection to the server");
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    //modtager en liste af Person fra server
    ArrayList<Person> recievePersons(){
        ArrayList<Person> persons = new ArrayList<Person>();
        try { //modtag objekt stream fra server
            ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
            try {
                Object obj = input.readObject();
                persons = (ArrayList<Person>) obj; //cast input til ArrayList
            } catch (ClassNotFoundException ce){
                server_msg = "Something went wrong!";
                System.out.println(server_msg);
            }
        } catch (IOException ie){
            server_msg = "Something went wrong!";
            System.out.println(server_msg);
            ie.printStackTrace();
        }
        return persons;
    }

    public String getServer_msg() { //til GUI info
        return server_msg;
    }
    
    

}
