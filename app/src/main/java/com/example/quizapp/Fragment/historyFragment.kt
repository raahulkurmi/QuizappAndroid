package com.example.quizapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.R
import com.example.quizapp.adapter.categoryadapater
import com.example.quizapp.adapter.historyadapter
import com.example.quizapp.databinding.FragmentHistoryBinding
import com.example.quizapp.modelclass.historydataclass
import com.example.quizapp.modelclass.signupmodelclass
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.Collections

class historyFragment : Fragment() {
    private val binding: FragmentHistoryBinding by lazy {
        FragmentHistoryBinding.inflate(layoutInflater)
    }
    var currentcoin = 0L


    private var coincategory = ArrayList<historydataclass>()
    lateinit var adaptor: historyadapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.database.reference.child("PlayerCoinhistory").child(Firebase.auth.currentUser!!.uid).addValueEventListener(
            object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    coincategory.clear()
                    var listhistory1=ArrayList<historydataclass>()
                    for(datasnap in snapshot.children){
                        var data=datasnap.getValue(historydataclass::class.java)

                        listhistory1.add(data!!)

                    }
                    Collections.reverse(listhistory1)
                    coincategory.addAll(listhistory1)


                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }
        )
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        binding.imageView4.setOnClickListener {
            var bottomsheetdialog: BottomSheetDialogFragment = withdrawalfragment()
            bottomsheetdialog.show(requireActivity().supportFragmentManager, "test")
            bottomsheetdialog.enterTransition
        }
        binding.textView6.setOnClickListener {
            var bottomsheetdialog: BottomSheetDialogFragment = withdrawalfragment()
            bottomsheetdialog.show(requireActivity().supportFragmentManager, "test")
            bottomsheetdialog.enterTransition
        }
        binding.historyrecycler.layoutManager=LinearLayoutManager(requireContext())
        adaptor=historyadapter(coincategory)
        binding.historyrecycler.adapter=adaptor
        binding.historyrecycler.setHasFixedSize(true)

        Firebase.database.reference.child("User").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
//                    for(data in snapshot.children){
                        var user = snapshot.getValue<signupmodelclass>()
                        binding.textView5.text = user?.name


                    }


//                }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(requireContext(), "value not found", Toast.LENGTH_SHORT)
                            .show()

                    }

                }


            )

        Firebase.database.reference.child("PlayerCoin").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.value
                    if (value is Long) {
                        currentcoin = value


                        binding.textView6.text = value?.toString()

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        return binding.root
    }


    
}



