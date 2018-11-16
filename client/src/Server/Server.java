
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
        
        
                try{
                    
                    ObjectInputStream person = new ObjectInputStream(connectionSocket.getInputStream());
                    try{
                        Person newPerson;
                        Object obj = person.readObject();
                        newPerson = (Person)obj;    
                        newPerson.tilDB();
                        System.out.println(newPerson.getName());
                        sendMessage("person was inserted");
                    }
                    catch(ClassNotFoundException ex){
                        ex.printStackTrace();
                    }
                }
                catch(IOException ex){
                    ex.printStackTrace();
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
        
            
            //Forsøg på at samle alle funktioner på én server port
            //En request skal afgøre hvilken "service" servere skal levere
            
            //ataInputStream request = new DataInputStream(connectionSocket.getInputStream());
           /* BufferedReader inFromServer = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            String strreq  = inFromServer.readLine(); 
            
            System.out.println(strreq);
            int req = Integer.parseInt(strreq);*/
            
           //De to porte med forskellige services
            if(this.port == Main.PORT){
                sendList();
            }
            
            else if(this.port == Main.PORT2){
                insertPerson();
            }
                        
        }
        catch(IOException io){
            System.out.println("something went wrong when the client tried to connect");
            io.printStackTrace();
        }

    }
}
