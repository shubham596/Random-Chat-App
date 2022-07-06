
package com.example.chattingapp

import android.content.Context
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageAdapter(val context:Context,val messageList:ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var r=1
    var s=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType==1){

            val view:View=LayoutInflater.from(context).inflate(R.layout.recieve,parent,false)
            return RecieveViewHolder(view)
        }else{

            val view:View=LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentmessage=messageList[position]
        if(holder.javaClass==SentViewHolder::class.java){


            val viewholder=holder as SentViewHolder

            if(currentmessage.message?.startsWith("https://firebasestorage") == true){
                holder.sentImage.visibility=View.VISIBLE
                Glide.with(context).load(currentmessage.message).into(holder.sentImage)

            }else{
                holder.sentmessage.visibility=View.VISIBLE
                holder.sentmessage.text=currentmessage.message
            }





        }else{


            val viewholder= holder as RecieveViewHolder
            if(currentmessage.message?.startsWith("https://firebasestorage") == true){
                holder.recieveimage.visibility=View.VISIBLE
                Glide.with(context).load(currentmessage.message).into(holder.recieveimage)

            }else{
                holder.recieveimage.visibility=View.VISIBLE
                holder.recievemessage.text=currentmessage.message
            }



        }
    }


    override fun getItemViewType(position: Int): Int {
        val currentMessage=messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){

            return s

        }else{

            return r

        }



    }


    override fun getItemCount(): Int {

        return messageList.size

    }

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var sentImage=itemView.findViewById<ImageView>(R.id.sentimage)
        var sentmessage=itemView.findViewById<TextView>(R.id.sentmessge)

    }
    class RecieveViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var recieveimage=itemView.findViewById<ImageView>(R.id.recieveimage)
        var recievemessage=itemView.findViewById<TextView>(R.id.recievemessge)
    }
}