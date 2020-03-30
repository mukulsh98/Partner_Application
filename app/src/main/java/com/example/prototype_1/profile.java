package com.example.prototype_1;

public class profile {
    String name;
    String email;
    String number;
    String password;
    String genre;
    String id;
public profile() {

    }
 public profile(String name,String email, String number,String password,String genre,String id){

    this.email=email;
    this.name=name;
    this.number=number;
    this.password=password;
    this.genre=genre;
    this.id=id;
 }
 public String getName(){
    return name;
 }

    public String getEmail() {
        return email;
    }

    public String getGenre() {
        return genre;
    }

    public String getNumber() {
        return number;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }
}

