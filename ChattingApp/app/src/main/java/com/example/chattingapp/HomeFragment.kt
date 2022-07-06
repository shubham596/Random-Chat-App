package com.example.chattingapp

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chattingapp.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class HomeFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var userList:ArrayList<User>
    lateinit var adater:UserAdapter
    lateinit var firebaseAuth:FirebaseAuth
    lateinit var mDbRef: DatabaseReference
    lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


binding= FragmentHomeBinding.inflate(layoutInflater,container,false)

        recyclerView=binding.recylcer

        userList= ArrayList()
        firebaseAuth= FirebaseAuth.getInstance()
        mDbRef=FirebaseDatabase.getInstance().getReference()



        adater=UserAdapter(requireContext(),userList)
        recyclerView.layoutManager=LinearLayoutManager(requireContext())
        recyclerView.adapter=adater
        mDbRef.child("user").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for(postSnapshot in snapshot.children){

                    val currentUser=postSnapshot.getValue(User::class.java)

                    if(firebaseAuth.currentUser?.uid!= currentUser?.uid ){
                        userList.add(currentUser!!)
                    }



                }
                adater.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        // Inflate the layout for this fragment
       return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.logout){
            firebaseAuth.signOut()
            val intent= Intent(requireContext(),LogIn::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    }
