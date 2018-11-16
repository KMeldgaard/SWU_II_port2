package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import Server.Request;

public class GUI extends JFrame implements ActionListener{
    
    private static String[] stud_ret = {"Dipl. Robot" , "Civil Robot"};
    
    private JLabel promptN = new JLabel("Navn: ");
    private JTextField navn = new JTextField(12);
    private JLabel promptA = new JLabel("Alder: ");
    private JTextField alder = new JTextField(2);
    private JLabel pers_i_db_label = new JLabel("Personer i DB:");
    private JLabel server_msg = new JLabel(" ");
  
    private JButton tilfoej = new JButton("Tilføj person");
    private List list = new List(15, true);
    
    public GUI()  {
        setTitle("GUI");
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
      
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        add(promptN, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        add(navn, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        add(promptA, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        add(alder, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        add(server_msg, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        add(tilfoej, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        add(pers_i_db_label, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.gridheight = 10;
        add(list, c);
        
        //tilføj action listener til knappen, så den kan bruges
        tilfoej.addActionListener(this);
        tilfoej.setActionCommand("til");
        
        //this.update_list();
                
    }
        
    public void actionPerformed(ActionEvent e){
        if (e.getSource().equals(tilfoej)){
            tilAction();
        }
  }
    
    private void clearTextFields(){
        navn.setText("");
        alder.setText("");
    }
    
    private void update_list(){
        Client client = new Client("localhost", Main.PORT, Request.GET_PERSONS);
        boolean con = client.connect();
        
        if (!con){ //mest til debug
            return;
        }
        
        client.sendReq();
        ArrayList<Person> plist = client.recievePersons();
        
        try {
        //fjern alle entries og erstat med nye
        list.removeAll();
        list.add("ID\tNAVN\tALDER\t");
            for (Person p : plist){               
                list.add(p.getId()+ "\t"
                + p.getName() + "\t"
                + p.getAge());
            }
        } catch (NullPointerException ne){
            System.out.println("Persons NOT recieved");
            ne.printStackTrace();
        }
    }
    
    private void tilAction(){
        try{
            Person p = new Person(
                    navn.getText(),
                    Integer.parseInt(alder.getText()),
                    -1);
            clearTextFields();
            
            //send information
            Client client = new Client("localhost", Main.PORT, Request.ADD_PERSON);
            client.connect();
            client.sendReq();
            client.writePerson(p);
            client.inFromServer();
            server_msg.setText(client.getServer_msg());
            client.disConnect();
        } catch (java.lang.NumberFormatException e){ //input er forkert i alder
            System.out.println("NumberFormatExeption: " + e.getMessage());
        }
        return;
    }
}
