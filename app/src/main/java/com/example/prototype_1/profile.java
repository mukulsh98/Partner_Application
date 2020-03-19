package com.example.prototype_1;

public class profile {
    String name;
    String email;
    String number;
    String password;
    String genre;
public profile() {

    }
 public profile(String name,String email, String number,String password,String genre){

    this.email=email;
    this.name=name;
    this.number=number;
    this.password=password;
    this.genre=genre;
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
}

