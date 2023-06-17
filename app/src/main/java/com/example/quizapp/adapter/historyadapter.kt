package com.example.quizapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.databinding.FragmentHistoryBinding
import com.example.quizapp.databinding.HisstoryrecyclermodelBinding
import com.example.quizapp.modelclass.historydataclass
import com.google.firebase.Timestamp
import java.lang.System.currentTimeMillis
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class historyadapter(var coincategory :ArrayList<historydataclass>): RecyclerView.Adapter<historyadapter.Historycoinviewholder>() {
    class Historycoinviewholder(var binding: HisstoryrecyclermodelBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Historycoinviewholder {
        return Historycoinviewholder(HisstoryrecyclermodelBinding.inflate(LayoutInflater.from(parent.context),parent,false ))
    }

    override fun getItemCount()=coincategory.size



    override fun onBindViewHolder(holder: Historycoinviewholder, position: Int) {


        // Assuming you have the datetime value as a Long
        val datetimeLong = coincategory[position].datetime.toLong()

// Convert the Long value to LocalDateTime
        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(datetimeLong), ZoneId.systemDefault())

// Define the desired date and time format
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

// Format the datetime using the formatter
        val formattedDateTime = dateTime.format(formatter)

// Set the formatted datetime to the TextView
        holder.binding.dattime.text = formattedDateTime
//        holder.binding.dattime.text=coincategory[position].datetime.toLong().toString()
        holder.binding.statuscoin.text=if(coincategory.get(position).iswithdrawl){"- money withdraw"}else{"+ money added"}


        holder.binding.coin.text=coincategory[position].coin
    }


}

