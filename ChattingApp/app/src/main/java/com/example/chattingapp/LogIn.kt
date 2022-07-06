package com.example.chattingapp

import android.app.PendingIntent.getActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {
    lateinit var pas: EditText
    lateinit var suser: EditText

    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        supportActionBar?.hide()
        var gotosingup=findViewById<TextView>(R.id.login)
        suser=findViewById(R.id.suser)
        pas=findViewById(R.id.spass)
        firebaseAuth= FirebaseAuth.getInstance()
        var f=firebaseAuth.currentUser
        if(f!=null){
            val intent:Intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        gotosingup.setOnClickListener {
            var intent= Intent(this,SignUp::class.java)
            startActivity(intent)
            finish()
        }
        var button=findViewById<Button>(R.id.lbtn)
        button.setOnClickListener {
            login()

        }

    }

    private fun login() {
        var email=suser.text.toString()
        var password=pas.text.toString()
Log.d("hello","shubham $email and $password")
        if(email.isBlank()||password.isBlank()){
            Toast.makeText(this,"Email or password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){

                    Toast.makeText(this,"login successful",Toast.LENGTH_SHORT).show()
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()

                }else{
                    Toast.makeText(this,"Please check the email and password",Toast.LENGTH_SHORT).show()
                }
            }

        }


}
