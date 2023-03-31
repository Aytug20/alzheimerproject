package com.example.projectalzheimer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.example.projectalzheimer.MainActivity
import com.example.projectalzheimer.Person
import com.example.projectalzheimer.databinding.ListRecyclerRowBinding
import com.example.projectalzheimer.databinding.RecyclerRowBinding

class FeedRecyclerAdapter(private val postList : ArrayList<Person>) : RecyclerView.Adapter<FeedRecyclerAdapter.PostHolder>() {

    class PostHolder(val binding : ListRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = ListRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder((binding))
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return postList.size
    }
}