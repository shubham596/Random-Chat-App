package com.example.chattingapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.chattingapp.databinding.FragmentEditBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage


class EditFragment : Fragment() {
lateinit var binding:FragmentEditBinding
    lateinit var launcher: ActivityResultLauncher<String>
    lateinit var database: FirebaseDatabase
    lateinit var storage: FirebaseStorage
    lateinit var firebaseAuth: FirebaseAuth

     var holdpic:String="https://picsum.photos/536/354"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentEditBinding.inflate(layoutInflater,container,false)

firebaseAuth= FirebaseAuth.getInstance()
        storage= FirebaseStorage.getInstance()
        database= FirebaseDatabase.getInstance()








        launcher= registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                binding.imageViewEdit.setImageURI(it)

                val reference=storage.getReference().child("image").child(firebaseAuth.currentUser?.uid.toString())

                reference.putFile(it).addOnSuccessListener { taskSnapshot ->

                    reference.downloadUrl.addOnSuccessListener {

                        holdpic=it.toString()
                        Log.d("hello",it.toString())
                        binding.lbtnEdit.visibility=View.VISIBLE
                        }
                    }
                }



            }

binding.imageViewEdit.setOnClickListener{
    launcher.launch("image/*")
}




binding.lbtnEdit.setOnClickListener {
    var name=binding.Editname.text.toString()

    val childUpdates = hashMapOf<String, Any>(
        "name" to name,
        "imageprofile" to holdpic
    )

    val reference=database.getReference().child("user").child(firebaseAuth.currentUser?.uid.toString())
    reference.updateChildren(childUpdates).addOnCompleteListener {
        binding.Editname.setText("")
    }



}




        return binding.root
    }

}