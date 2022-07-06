package com.example.chattingapp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView
import java.util.zip.Inflater

class UserAdapter(val context: Context,val userList:ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.viewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):viewholder{
var view=LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val currentUser=userList[position]
        var name=currentUser.name?.trim()
        holder.name.text=currentUser.name
        Glide.with(context).load(currentUser.imageprofile).into(holder.imageprofile)
        holder.itemView.setOnClickListener{
            val intent=Intent(context,ChatActivity::class.java)
            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {

       return userList.size
    }

    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var name=itemView.findViewById<TextView>(R.id.name)
        var imageprofile=itemView.findViewById<CircleImageView>(R.id.imageprofile)
    }

}