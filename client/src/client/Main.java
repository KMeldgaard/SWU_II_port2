/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;
    
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Main {

    public static void main(String argv[]) throws Exception {
        
        //kaspers test af GUI
        //Måske skal client kode rykkes til nogle static metoder??
        GUI f = new GUI();
      f.setSize(500,500);
      f.setVisible(true);
      f.addWindowListener(new WindowAdapter(){
          public void windowClosing(WindowEvent e){
            System.exit(0);
            }
        });
        
        boolean waitingForServer = false;
        Person person1 = new Person("Mads", 22, 14);
        Client client = new Client("localhost", 6789);
        
        client.connect();
        
        client.writePerson(person1);
        
        
        
        String serverMessage = client.inFromServer();
        System.out.println(serverMessage);
        
        /*if(serverMessage == "ready"){
            client.writePerson(person1);
        }*/
        
        client.disConnect();
        
        /*String sentence;
        String modifiedSentence;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket("localhost", 6789);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        sentence = inFromUser.readLine();
        outToServer.writeBytes(sentence + '\n');
        modifiedSentence = inFromServer.readLine();
        System.out.println("FROM SERVER: " + modifiedSentence);
        clientSocket.close();*/
    }
}

