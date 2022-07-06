package com.example.chattingapp

import android.widget.ImageView


class User {
    var name:String?=null
    var email:String?=null
    var uid:String?=null
var imageprofile:String?=null
    constructor(){}
    constructor(name:String?,email:String?,uid:String?){
        this.name=name
        this.email=email
        this.uid=uid
this.imageprofile="https://html.com/wp-content/uploads/flamingo.webp"
    }


}