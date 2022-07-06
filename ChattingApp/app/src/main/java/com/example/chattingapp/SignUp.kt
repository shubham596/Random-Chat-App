package com.example.chattingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class SignUp : AppCompatActivity() {
    lateinit var pas:EditText
    lateinit var suser:EditText
    lateinit var confirmpas:EditText
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var mDbRef:DatabaseReference
    lateinit var names:EditText
    lateinit var checkBox: CheckBox



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        var login=findViewById<TextView>(R.id.login)
suser=findViewById(R.id.suser)
      pas=findViewById(R.id.spass)
        confirmpas=findViewById(R.id.sconfirmpas)
        names=findViewById(R.id.name)




checkBox=findViewById(R.id.checkbox)




        var button=findViewById<Button>(R.id.lbtn)
        button.setOnClickListener {
            signup()

        }


        login.setOnClickListener {
            var intent= Intent(this,LogIn::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signup() {
        var email=suser.text.toString()
        var password=pas.text.toString()
        var confirmpassword=confirmpas.text.toString()
        var name=names.text.toString()



        if(email.isBlank()||password.isBlank()||confirmpassword.isBlank()){
            Toast.makeText(this,"Email or password can't be blank",Toast.LENGTH_SHORT).show()
            return
        }

        if(password!=confirmpassword){
            Toast.makeText(this,"Confirm password must be same",Toast.LENGTH_SHORT).show()
            return
        }
        if(checkBox.isChecked==false){
            Toast.makeText(this,"Please confirm the checkbox",Toast.LENGTH_SHORT).show()
            return
        }

firebaseAuth= FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                   addUserToDatabase(name,email, firebaseAuth.currentUser?.uid!!)
                    Toast.makeText(this,"Login successfull",Toast.LENGTH_SHORT).show()
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this,"Please correct the email or password",Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
mDbRef= FirebaseDatabase.getInstance().getReference()

            mDbRef.child("user").child(uid).setValue(User(name,email,uid))


    }
}