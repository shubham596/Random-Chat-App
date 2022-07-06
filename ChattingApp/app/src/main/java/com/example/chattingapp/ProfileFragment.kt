package com.example.chattingapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide

import com.example.chattingapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ProfileFragment : Fragment() {
    lateinit var firebaseAuth:FirebaseAuth
    lateinit var mDbRef: DatabaseReference

lateinit var binding:FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProfileBinding.inflate(layoutInflater, container, false)


        firebaseAuth= FirebaseAuth.getInstance()
        mDbRef= FirebaseDatabase.getInstance().getReference()


        mDbRef.child("user").child(firebaseAuth.currentUser?.uid.toString()).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {


                    val currentUser=snapshot.getValue(User::class.java)

                Glide.with(requireContext()).load(currentUser?.imageprofile).into(binding.profileimage)

binding.txtEmail.text=currentUser?.email.toString()

                }




            override fun onCancelled(error: DatabaseError) {

            }

        })

binding.btnLogout.setOnClickListener {
    firebaseAuth.signOut()
    val gotoLoginpage= Intent(requireContext(),LogIn::class.java)
    startActivity(gotoLoginpage)
activity?.finish()
}



        return binding.root
    }

}