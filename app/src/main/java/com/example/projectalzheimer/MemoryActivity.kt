package com.example.projectalzheimer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectalzheimer.databinding.ActivityMemoryBinding
import kotlin.Exception

class MemoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMemoryBinding
    private lateinit var personList : ArrayList<Person>
    private lateinit var memoryAdapter: MemoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        personList = ArrayList<Person>()

        memoryAdapter = MemoryAdapter(personList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = memoryAdapter

        try{
            val database = this.openOrCreateDatabase("Kisiler", MODE_PRIVATE,null)

            val cursor = database.rawQuery("SELECT * FROM persons",null)
            val artNameIx = cursor.getColumnIndex("memoryname")
            val idIx = cursor.getColumnIndex("id")

            while (cursor.moveToNext()) {
                val name = cursor.getString(artNameIx)
                val id = cursor.getInt(idIx)
                val person = Person(name, id)
                personList.add(person)
            }

            memoryAdapter.notifyDataSetChanged()

            cursor.close()

        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.memory_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_memory_person){
            val intent = Intent(this@MemoryActivity,DetailsActivity::class.java)
            intent.putExtra("info","new")
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }
}