package Server;


public class Main {
    
    protected static int PORT = 6789;
    protected static int PORT2 = 6781;
         
    public static void main(String argv[]) throws Exception { 
    
        Server server = new Server(PORT);
        Server serve2 = new Server(PORT2);
        server.startServer();
        serve2.startServer();
     
        while(true){
            server.clientConnection();
            serve2.clientConnection();
        }
     
    }
}
