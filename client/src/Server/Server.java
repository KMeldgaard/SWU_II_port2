
package Server;
import java.io.*;
import java.net.*;
import client.Person;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
TODO:
Implementer mySQL connector
Implementer requests

*/

public class Server {
    private int port;
    private ServerSocket server;
    Socket connectionSocket;
    
    
    public Server(int port){
        this.port = port;
    }   
    
    private void sendMessage(String message){
        try {
            OutputStreamWriter toClient = new OutputStreamWriter(connectionSocket.getOutputStream());
            BufferedWriter buf = new BufferedWriter(toClient);
            buf.write(message);
            buf.flush();
        } 
        catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void sendList(){
        ObjectOutputStream list = null;
        try {
            ArrayList al = new ArrayList<Person>();
            al = DBAdgang.hentPersoner();
            list = new ObjectOutputStream(connectionSocket.getOutputStream());
            System.out.println("here");
            list.writeObject(al);
            System.out.println("here2");
            sendMessage("the list was send");
        } 
        catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        
        }
    }
    
    private void insertPerson(){
        ObjectInputStream person = null;
        
                try{
                    Person newPerson = null;
                    person = new ObjectInputStream(connectionSocket.getInputStream());
                    try{
                        newPerson = (Person)person.readObject();
                        newPerson.tilDB();
                        System.out.println(newPerson.getName());
                        sendMessage("person was inserted");
                    }
                    catch(ClassNotFoundException ex){
                        ex.printStackTrace();
                    }
                }
                catch(IOException ex){
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }

        }
    
    public void startServer(){
        try{    
            server = new ServerSocket(this.port);
            System.out.println("the server is now started");    
        }
        catch(IOException io){
            System.out.println("failed to start server");
            io.printStackTrace();
        }
    
    }

    public void clientConnection(){
        try{
           
            connectionSocket = server.accept();
        
            DataInputStream request = new DataInputStream(connectionSocket.getInputStream());
            
            int req = request.readInt();
            System.out.println(req);
            
            
            if(req == Request.GET_PERSONS.ordinal()){
                sendList();
            }
            
            else if(req == Request.ADD_PERSON.ordinal()){
                insertPerson();
            }
                        
        }
        catch(IOException io){
            System.out.println("something went wrong when the client tried to connect");
            io.printStackTrace();
        }

    }
}
