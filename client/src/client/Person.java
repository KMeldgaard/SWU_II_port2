
package client;

import java.io.Serializable;
import Server.DBAdgang;

public class Person implements Serializable{
    private String name;
    private int age, id;
    
    public Person(){
        name = "";
        age = 0;
        id = 0;
    }
    public Person(int id, String name, int age){
        this.name = name;
        this.age = age; 
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
    public int getId(){
        return id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAge(int age){
        this.age = age;
    }
    public void setId(int id){
        this.id = id;
    }
    
    public void tilDB()
    {
        DBAdgang.indsaetPersonDB(this);
    }
    
}
