package com.example.quizapp.Fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.quizapp.databinding.FragmentSpinBinding
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
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.random.Random


class spinFragment : Fragment() {
   val binding by lazy {
       FragmentSpinBinding.inflate(layoutInflater)
   }
    private lateinit var timer : CountDownTimer
    private val itemtitle= arrayOf("100","Try again","500","Try again","200","Try again")

    var chances=0L
    var wincoin=0L

    var currentcoin=0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.imageView4.setOnClickListener {
            var bottomsheetdialog : BottomSheetDialogFragment =withdrawalfragment()
            bottomsheetdialog.show(requireActivity().supportFragmentManager,"test")
            bottomsheetdialog.enterTransition
        }
        binding.textView6.setOnClickListener {
            var bottomsheetdialog :BottomSheetDialogFragment =withdrawalfragment()
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
        Firebase.database.reference.child("Playchance").
        child(Firebase.auth.currentUser!!.uid).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val  value = snapshot.value
                if (value is Long) {
                    chances=value
                   binding.spinnertextnumber.text=chances?.toString()
                }else{
                    var temp=0
                    binding.spinnertextnumber.text=temp?.toString()
                    binding.spinbutton.isEnabled=false

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        Firebase.database.reference.child("PlayerCoin").
        child(Firebase.auth.currentUser!!.uid).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value
                if (value is Long) {
                    currentcoin=value


                    binding.textView6.text=value?.toString()

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return binding.root
    }
    private fun showResult(itemTitle:String,spin:Int){
        if(spin%2==0){
            val currentTimeMillis = System.currentTimeMillis()

// Format the timestamp as a date string

// Format the timestamp as a date string
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val dateTimeString: String = sdf.format(Date(currentTimeMillis))

            wincoin=itemTitle.toLong()
            Firebase.database.reference.child("PlayerCoin").child(Firebase.auth.currentUser!!.uid).setValue(wincoin+currentcoin).addOnSuccessListener {
//                binding.textView6.text= (wincoin+currentcoin).toString()
            }


            var historydataclass=historydataclass(System.currentTimeMillis().toString().toLong(),wincoin.toString(),false)
            Firebase.database.reference.child("PlayerCoinhistory").child(Firebase.auth.currentUser!!.uid).push().setValue(historydataclass).
            addOnSuccessListener {
//                binding.textView6.text= (wincoin+currentcoin).toString()
            }

        }
        Toast.makeText(requireContext(),itemTitle, Toast.LENGTH_SHORT).show()
        chances--
        Firebase.database.reference.child("Playchance").child(Firebase.auth.currentUser!!.uid)
            .setValue(chances).addOnSuccessListener {


            }

        binding.spinbutton.isEnabled=true

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.spinbutton.setOnClickListener {
            binding.spinbutton.isEnabled=false
        if(chances>0){
            val spin= Random.nextInt(6)
    val degree=60f*spin
    timer=object :CountDownTimer(5000,50){
        var rotation=0f

        override fun onTick(p0: Long) {
            rotation+=5f
            if(rotation>=degree) {
                rotation = degree
                timer.cancel()
                showResult(itemtitle[spin],spin)
            }
            binding.wheel.rotation=rotation

        }

        override fun onFinish() {}





    }.start()


}
        else{
            Toast.makeText(requireContext(), "out of chances", Toast.LENGTH_SHORT).show()
        }




        }

    }


    }
