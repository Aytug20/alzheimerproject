package com.example.projectalzheimer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = Firebase.auth
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.alzheimer_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.add_memory){
            val intent = Intent(this,MemoryActivity::class.java)
            startActivity(intent)

        }else if(item.itemId == R.id.add_location){
            val intent = Intent(this,MapUserActivity::class.java)
            startActivity(intent)
        }else if(item.itemId == R.id.add_task){
            val intent = Intent(this,TaskActivity::class.java)
            startActivity(intent)
        }else if(item.itemId == R.id.add_signout){
            auth.signOut()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }



        return super.onOptionsItemSelected(item)
    }
}