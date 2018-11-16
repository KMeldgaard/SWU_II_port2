package Server;

/**
 * Generelt starter "server programmet" 2 servere.
 * Hver har deres egen funktionalitet
 * Det kunne svare til at lave noget fault isolation
 * @author Mads og Kasper
 */

public class Main {
    
    protected static int PORT = 6789;
    protected static int PORT2 = 6781;
         
    public static void main(String argv[]) throws Exception { 
    
        Server server = new Server(PORT); //sender liste af personer i DB
        Server serve2 = new Server(PORT2); //modtager Person objekt og ligger det i DB
        server.startServer();
        serve2.startServer();
     
        while(true){
            server.clientConnection();
            serve2.clientConnection();
        }
     
    }
}
