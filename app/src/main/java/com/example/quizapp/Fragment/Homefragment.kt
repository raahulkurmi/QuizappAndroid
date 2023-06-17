package com.example.quizapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quizapp.R
import com.example.quizapp.adapter.categoryadapater
import com.example.quizapp.databinding.FragmentHomefragmentBinding
import com.example.quizapp.modelclass.modelclass
import com.example.quizapp.modelclass.signupmodelclass
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Homefragment : Fragment() {
    private val binding: FragmentHomefragmentBinding by lazy {
        FragmentHomefragmentBinding.inflate(layoutInflater)

    }
private  var categorylist=ArrayList<modelclass>()
    var currentcoin=0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.imageView4.setOnClickListener {
            var bottomsheetdialog :BottomSheetDialogFragment=withdrawalfragment()
            bottomsheetdialog.show(requireActivity().supportFragmentManager,"test")
            bottomsheetdialog.enterTransition
        }
        binding.textView6.setOnClickListener {
            var bottomsheetdialog :BottomSheetDialogFragment=withdrawalfragment()
            bottomsheetdialog.show(requireActivity().supportFragmentManager,"test")
            bottomsheetdialog.enterTransition
        }
        Firebase.database.reference.child("User").child(Firebase.auth.currentUser!!.uid).addValueEventListener(
            object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
//                    for(data in snapshot.children){
                    var user=snapshot.getValue<signupmodelclass>()
                    binding.textView5.text=user?.name



                }


//                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "value not found", Toast.LENGTH_SHORT).show()

                }

            }


        )
        Firebase.database.reference.child("PlayerCoin").
        child(Firebase.auth.currentUser!!.uid).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value
                if (value is Long) {
                    currentcoin=value


                    binding.textView6.text=currentcoin?.toString()

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
            return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categorylist.clear()
        categorylist.add(modelclass(R.drawable.science,"Science"))
        categorylist.add(modelclass(R.drawable.sst,"SST"))
        categorylist.add(modelclass(R.drawable.engliesh,"english"))
        categorylist.add(modelclass(R.drawable.maths,"Mathematics"))
        binding.categoryrecyclerview.layoutManager=GridLayoutManager(requireContext(),2)
        var adapter = categoryadapater(categorylist,requireActivity())
        binding.categoryrecyclerview.adapter=adapter
        binding.categoryrecyclerview.setHasFixedSize(true)
    }
    companion object {


    }
}