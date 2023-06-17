package com.example.quizapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentWithdrawalfragmentBinding
import com.example.quizapp.modelclass.historydataclass
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class withdrawalfragment : BottomSheetDialogFragment() {
    private  val binding by lazy {
        FragmentWithdrawalfragmentBinding.inflate(layoutInflater)
    }

var currentcoin=0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Firebase.database.reference.child("PlayerCoin").
        child(Firebase.auth.currentUser!!.uid).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value
                if (value is Long) {
                    currentcoin=value


                    binding.textView12.text=value?.toString()

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
       binding.button.setOnClickListener {
           if(binding.editTextText2.text.toString().toDouble()<=currentcoin){
               Firebase.database.reference.child("PlayerCoin").
               child(Firebase.auth.currentUser!!.uid).setValue(currentcoin-binding.editTextText2.text.toString().toDouble())
               var historydataclass= historydataclass(System.currentTimeMillis().toString().toLong(),binding.editTextText2.text.toString(),true)
               Firebase.database.reference.child("PlayerCoinhistory").child(Firebase.auth.currentUser!!.uid).push().setValue(historydataclass)


           }else{
               Toast.makeText(requireContext(), "coins not available", Toast.LENGTH_SHORT).show()
           }
       }
        return binding.root
    }

}