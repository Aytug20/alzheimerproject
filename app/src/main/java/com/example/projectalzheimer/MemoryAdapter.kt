package com.example.projectalzheimer

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectalzheimer.databinding.RecyclerRowBinding

class MemoryAdapter(val personList: ArrayList<Person>) : RecyclerView.Adapter<MemoryAdapter.MemoryHolder>() {

    class MemoryHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoryHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MemoryHolder(binding)
    }

    override fun onBindViewHolder(holder: MemoryHolder, position: Int) {
        holder.binding.recyclerViewTextView.text = personList.get(position).name
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,DetailsActivity::class.java)
            intent.putExtra("info","old")
            intent.putExtra("id",personList.get(position).id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return personList.size
    }

}