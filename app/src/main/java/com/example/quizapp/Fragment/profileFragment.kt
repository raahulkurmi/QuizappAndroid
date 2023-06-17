package com.example.quizapp.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.quizapp.MainActivity
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentProfileBinding
import com.example.quizapp.modelclass.signupmodelclass
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class profileFragment : Fragment() {
    val binding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }

    var isExpand = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.imageButton2.setOnClickListener {
            if (isExpand) {
//                binding.expandablelayout.visibility = View.VISIBLE
                binding.imageButton2.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)

            } else {
//                binding.expandablelayout.visibility = View.GONE
                binding.imageButton2.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)



            }
            isExpand = !isExpand
        }
        Firebase.database.reference.child("User").child(Firebase.auth.currentUser!!.uid).addValueEventListener(
            object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
//                    for(data in snapshot.children){
                        var user=snapshot.getValue<signupmodelclass>()
                        binding.textView7.text=user?.name
                        binding.actualname.text=user?.name
                        binding.actualemail.text=user?.email
                        binding.actualpassword.text=user?.password
                    binding.actualage.text=user?.age.toString()


//                    }


                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "value not found", Toast.LENGTH_SHORT).show()

                }

            }


        )

        binding.signoutbutton.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(requireContext(),MainActivity::class.java))


        }

        return binding.root
    }


}