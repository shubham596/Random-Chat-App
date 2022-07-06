

package com.example.chattingapp

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class ChatActivity : AppCompatActivity() {
    lateinit var chatRecyclerView: RecyclerView
    lateinit var messageBox:EditText
    lateinit var sentButton:ImageView
    lateinit var messageAdapter: MessageAdapter
    lateinit var messageList:ArrayList<Message>
    private lateinit var mDbRef:DatabaseReference
    lateinit var storage: FirebaseStorage
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var launcher: ActivityResultLauncher<String>
    lateinit var shareimagebtn:ImageView
    //private lateinit var mDbRef:FirebaseDatabase
    var recieverRoom:String?=null
    var senderRoom:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name=intent.getStringExtra("name")
        val recieverUid=intent.getStringExtra("uid")


        val senderuid= FirebaseAuth.getInstance().currentUser?.uid


        firebaseAuth= FirebaseAuth.getInstance()
        storage= FirebaseStorage.getInstance()
        mDbRef=FirebaseDatabase.getInstance().getReference()

        senderRoom=recieverUid+senderuid
        recieverRoom=senderuid+recieverUid

        supportActionBar?.title=name

        chatRecyclerView=findViewById(R.id.chatRecyclerView)
        messageBox=findViewById(R.id.messagebox)
        sentButton=findViewById(R.id.sentbutton)
        shareimagebtn=findViewById(R.id.shareimagebtn)


        messageList= ArrayList()
        messageAdapter= MessageAdapter(this,messageList)
        chatRecyclerView.layoutManager=LinearLayoutManager(this)

        chatRecyclerView.adapter=messageAdapter


        mDbRef.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                messageList.clear()

                for (postsnapshot in snapshot.children){
                    val message=postsnapshot.getValue(Message::class.java)
                    messageList.add(message!!)
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })





        sentButton.setOnClickListener {
            var message=messageBox.text.toString()
            if(message.isBlank()){
                return@setOnClickListener;
            }
            val messageObject=Message(message,senderuid)
            mDbRef.child("chats").child(senderRoom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                mDbRef.child("chats").child(recieverRoom!!).child("messages").push().setValue(messageObject)
            }
            messageBox.setText("")
        }


        launcher= registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {


                val reference=storage.getReference().child("messageboximage").child(firebaseAuth.currentUser?.uid.toString())

                reference.putFile(it).addOnSuccessListener { taskSnapshot ->

                    reference.downloadUrl.addOnSuccessListener {

                        var message=it.toString()
                        val messageObject=Message(message,senderuid)
                        mDbRef.child("chats").child(senderRoom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                            mDbRef.child("chats").child(recieverRoom!!).child("messages").push().setValue(messageObject)
                        }
                        messageBox.setText("")


                    }
                }
            }



        }










        shareimagebtn.setOnClickListener {
            launcher.launch("image/*")
        }


    }
}