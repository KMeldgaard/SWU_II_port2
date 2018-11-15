
package Server;


public class Main {
         
    public static void main(String argv[]) throws Exception { 
    
        Server server = new Server(6789);
        server.startServer();
     
        while(true){
            server.clientConnection();
        }
     
    }
}
