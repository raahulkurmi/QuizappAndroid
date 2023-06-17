package com.example.quizapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.Quizsection
import com.example.quizapp.databinding.CategoryitemBinding
import com.example.quizapp.modelclass.modelclass

class categoryadapater(var categorylist: ArrayList<modelclass>, var requireActivity: FragmentActivity) : RecyclerView.Adapter<categoryadapater.Mycategoryviewholder>() {

    class Mycategoryviewholder(var binding: CategoryitemBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Mycategoryviewholder {
        return Mycategoryviewholder(CategoryitemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount()=categorylist.size


    override fun onBindViewHolder(holder: Mycategoryviewholder, position: Int) {

        var datalist=categorylist[position]
        holder.binding.categoryimage.setImageResource(datalist.catimage)
        holder.binding.recycletext.text=categorylist[position].cattext
        holder.binding.categorybtn.setOnClickListener {
            var intent=Intent(requireActivity, Quizsection::class.java)
            intent.putExtra("imageView7",datalist.catimage)
            intent.putExtra("questiontype",datalist.cattext)
            requireActivity.startActivity(intent)

        }
    }
}


