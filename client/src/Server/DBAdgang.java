
package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import client.Person;

/**
 * Næsten identisk med klassen fra portfølje 1
 * Tablenavn, hvordan en person indsættes og hvordan de trækkes ud af DB
 * @author mads & kasper
 */

public class DBAdgang {
    
     public static void indsaetPersonDB(Person enPerson)
   {
      
      Connection con;
      Statement stmt;
      Properties properties = new Properties();
      properties.setProperty("user", "root");
      properties.setProperty("password", "fynbonyt");
      properties.setProperty("useSSL", "false");
      properties.setProperty("autoReconnect", "true");

      
      String url      = "jdbc:mysql://localhost:3306/person?serverTimezone=UTC&useSSL=false";
      String login    = "root";
      String password = "fynbonyt";

      try
      {
       Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url, login, password);
        stmt= con.createStatement ();
        
        String s = "Insert into Person values ("+null+",'"+enPerson.getName()+"',"+enPerson.getAge()+");";
        stmt.executeUpdate (s);
      }
      catch(java.lang.ClassNotFoundException e)
      {
        System.err.println("ClassNotFoundException: " + e.getMessage());
      }
      catch(SQLException ex)
      {
        System.err.println("SQLException: " + ex.getMessage());
      }
   }
     
     
     public static ArrayList<Person> hentPersoner()
   {
      int antal = 0;
      Connection con;
      Statement stmt;
      Properties properties = new Properties();
      properties.setProperty("user", "root");
      properties.setProperty("password", "fynbonyt");
      properties.setProperty("useSSL", "false");
      properties.setProperty("autoReconnect", "true");

      
      String url      = "jdbc:mysql://localhost:3306/person?serverTimezone=UTC&useSSL=false";
      String login    = "root";
      String password = "fynbonyt";

      ArrayList aL = new ArrayList<Person>();
          
      try
      {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url, login, password);
        stmt= con.createStatement ();
        String s = "Select * From Person";
        ResultSet rs = stmt.executeQuery (s);
        
    
        while(rs.next())  // placerer cursor paa foerste tuppel
            aL.add(new Person(rs.getString("NAME"),rs.getInt("AGE"),rs.getInt("ID")));
      }
      catch(java.lang.ClassNotFoundException e)
      {
        System.err.println("ClassNotFoundException: " + e.getMessage());
      }
      catch(SQLException ex)
      {
        System.err.println("SQLException: " + ex.getMessage());
      }
      return aL;
   }
}

