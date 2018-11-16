
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
    
    
    public Server(int port){
        this.port = port;
    }    
    
    private void sendList(Socket connectionSocket){
        ObjectOutputStream list = null;
        try {
            ArrayList al = new ArrayList<Person>();
            al = DBAdgang.hentPersoner();
            list = new ObjectOutputStream(connectionSocket.getOutputStream());
            list.writeObject(al);
        } 
        catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        
        }
    }
    
    private void insertPerson(Socket connectionSocket){
        ObjectInputStream person = null;
                try{
                    Person newPerson = null;
                    person = new ObjectInputStream(connectionSocket.getInputStream());
                    try{
                        newPerson = (Person)person.readObject();
                        newPerson.tilDB();
                        System.out.println(newPerson.getName());
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
            String clientMessage = "done";
            Socket connectionSocket = server.accept();
        
            DataInputStream request = new DataInputStream(connectionSocket.getInputStream());
           
            Request req = Request.values()[request.readInt()];
            
            
            if(req == Request.GET_PERSONS){
                sendList(connectionSocket);
            }
            
            else if(req == Request.ADD_PERSON){
                insertPerson(connectionSocket);
            }
                        
        }
        catch(IOException io){
            System.out.println("something went wrong when the client tried to connect");
            io.printStackTrace();
        }

    }
}
