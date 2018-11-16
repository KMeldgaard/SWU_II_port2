package Server;


public class Main {
    
    protected static int PORT = 6789;
         
    public static void main(String argv[]) throws Exception { 
    
        Server server = new Server(PORT);
        server.startServer();
     
        while(true){
            server.clientConnection();
        }
     
    }
}
