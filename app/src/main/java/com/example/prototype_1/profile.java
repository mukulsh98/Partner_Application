package com.example.prototype_1;


// Contains 19 field : where different no_of_product=2, one for image of partner, lat & long of the shop, name, rating, offer, shopName, avalable_comodity, email, 2 feedback,genre, id,details of product, total no_of_products, status...

// we have public methods to change other fields like lat , longitude etc which are currently empty by creating a method to change them...
public class profile {
    String name;
    String email;
    String number;
    String status;
    String genre;
    String id;
    String lat;
    String longitude;
    String address;
    String rating;
    String shopName;
    String no_of_product;
    String product01;
    String product02;
    String feedback01;
    String feedback02;
    String total_no_of_products;
    String offer;

 public profile(String name,String email, String number,String genre,String id, String status, String shopName ,String address){

    this.email=email;
    this.status=status;
    this.name=name;
    this.number=number;
    this.genre=genre;
    this.id=id;
    lat="";
    longitude="";
    this.address=address;
    this.shopName=shopName;
    rating="0";
    no_of_product="2";
    product01="";
    product02="";
    feedback01="";
    feedback02="";
    total_no_of_products="";
    offer="";

 }
 public String getName(){
    return name;
 }

 public void setLat(String lat){
     this.lat=lat;
 }

    public void setLongitude(String longitude){
        this.longitude=longitude;
    }

    public void setProduct01(String product01){
        this.product01=product01;
    }

    public void setRating(String rating){
     this.rating=rating;
    }
    public void setProduct02(String product02){
        this.product02=product02;
    }
    public void setFeedback01(String feedback01){
        this.feedback01=feedback01;
    }
    public void setFeedback02(String feedback02){
        this.feedback02=feedback02;
    }
    public void setTotal_no_of_products(String total_no_of_products){
        this.total_no_of_products=total_no_of_products;
    }
    public void setOffer(String offer){
        this.offer=offer;
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

    public String getStatus() {return status;  }

    public String getId() {
        return id;
    }


}

