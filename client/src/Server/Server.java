
package Server;
import java.io.*;
import java.net.*;
import client.Person;

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
             Person newPerson = null;
        
            //BufferedReader inFromClient =
            //new BufferedReader(new InputStreamReader(connectionSocket.getInputStream())); 
        
            OutputStreamWriter outToClient = new OutputStreamWriter(connectionSocket.getOutputStream());
            BufferedWriter buf = new BufferedWriter(outToClient);
        
            //clientMessage = inFromClient.readLine();
        
            //System.out.println("resieved: " + clientMessage);
            
            ObjectInputStream person = new ObjectInputStream(connectionSocket.getInputStream());
                try{
                    newPerson = (Person)person.readObject();
                    System.out.println(newPerson.getName());
                }
                catch(ClassNotFoundException ex){
                    ex.printStackTrace();
                }
            //send bekræftigelse til client
            buf.write(clientMessage);
            buf.flush();
            
        }
        catch(IOException io){
            System.out.println("something went wrong when the client tried to connect");
            io.printStackTrace();
        }

    }
}
