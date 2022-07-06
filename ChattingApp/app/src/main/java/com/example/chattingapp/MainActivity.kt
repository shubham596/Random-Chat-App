package com.example.chattingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chattingapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView


class MainActivity : AppCompatActivity() {
lateinit var binding:ActivityMainBinding
lateinit var toggle:ActionBarDrawerToggle
    lateinit var firebaseAuth:FirebaseAuth
    lateinit var mDbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding= ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()
        mDbRef=FirebaseDatabase.getInstance().getReference()
        toggle = ActionBarDrawerToggle(this,binding.navDrawer, R.string.open, R.string.close)
        binding.navDrawer.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        callingFragment(HomeFragment(),"Chat")

        val header=binding.navView.getHeaderView(0)
        var imghead=header.findViewById<CircleImageView>(R.id.headpic)
        var namehead=header.findViewById<TextView>(R.id.headname)
        var nameemail=header.findViewById<TextView>(R.id.heademail)
        imghead.setOnClickListener{
            callingFragment(EditFragment(),"Edit profile pic")
            binding.navDrawer.closeDrawers()
        }
        namehead.setOnClickListener {
            callingFragment(EditFragment(),"Edit profile pic")
            binding.navDrawer.closeDrawers()
        }

        mDbRef.child("user").child(firebaseAuth.currentUser?.uid.toString()).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {



                    val currentUser=snapshot.getValue(User::class.java)


                    Glide.with(this@MainActivity).load(currentUser?.imageprofile).into(imghead)

                namehead.text=currentUser?.name
                nameemail.text=currentUser?.email

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })




   binding.navView.setNavigationItemSelectedListener {
       when(it.itemId){
           R.id.logout->{
               firebaseAuth.signOut()
               val gotoLoginpage=Intent(this,LogIn::class.java)
               startActivity(gotoLoginpage)
               finish()
           }
           R.id.EditFrag-> callingFragment(EditFragment(),"Edit profile")
           R.id.Homefragment->callingFragment(HomeFragment(),"Chat")
       }
       binding.navDrawer.closeDrawers()
       true
   }




binding.bottomnavView.setOnNavigationItemSelectedListener {
           when(it.itemId){
               R.id.chat -> callingFragment(HomeFragment(),"Chat")
               R.id.news->callingFragment(ProfileFragment(),"Profile")

           }
           true
       }

    }




    private fun callingFragment(frag: Fragment,title:String) {
val fragmentTras=supportFragmentManager.beginTransaction().replace(R.id.fragmentCont,frag)
       fragmentTras.commit()
        setTitle(title)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
            }
        return super.onOptionsItemSelected(item)
    }

}

