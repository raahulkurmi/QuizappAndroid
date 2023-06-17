package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.quizapp.Fragment.withdrawalfragment
import com.example.quizapp.databinding.ActivityQuizsectionBinding
import com.example.quizapp.modelclass.questionmodelclass
import com.example.quizapp.modelclass.signupmodelclass
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class Quizsection : AppCompatActivity() {
    private val binding by lazy {
        ActivityQuizsectionBinding.inflate(layoutInflater)
    }
    var chances =0L
    var currentcoin=0L

    private lateinit var questionlist :ArrayList<questionmodelclass>
    private var currentposition=0
    private var score=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Firebase.database.reference.child("Playchance").
        child(Firebase.auth.currentUser!!.uid).addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value
                if (value is Long) {
                    chances = value
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        Firebase.database.reference.child("User").child(Firebase.auth.currentUser!!.uid).addValueEventListener(
            object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
//                    for(data in snapshot.children){
                    var user=snapshot.getValue<signupmodelclass>()
                    binding.textView5.text=user?.name



                }


//                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, "value not found", Toast.LENGTH_SHORT).show()

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



        questionlist=ArrayList<questionmodelclass>()
        var image = intent.getIntExtra("imageView7", 0)
        binding.imageView7.setImageResource(image)
        var cattext = intent.getStringExtra("questiontype")

        Firebase.firestore.collection("question").document(cattext.toString()).
        collection("question1").get().
        addOnSuccessListener { dataquestion ->
            questionlist.clear()
            for (question in dataquestion.documents) {

                    var questionlists = question.toObject(questionmodelclass::class.java)
                    questionlist.add(questionlists!!)


            }
            if(questionlist.size>0) {
                binding.textView14.text = questionlist.get(currentposition).question
                binding.button3.text = questionlist.get(currentposition).option1
                binding.button4.text = questionlist.get(currentposition).option2
                binding.button5.text = questionlist.get(currentposition).option3
                binding.button6.text = questionlist.get(currentposition).option4
            }
        }

        binding.imageView4.setOnClickListener {
            var bottomsheetdialog: BottomSheetDialogFragment = withdrawalfragment()
            bottomsheetdialog.show(this@Quizsection.supportFragmentManager, "test")
            bottomsheetdialog.enterTransition
        }
        binding.textView6.setOnClickListener {
            var bottomsheetdialog: BottomSheetDialogFragment = withdrawalfragment()
            bottomsheetdialog.show(this@Quizsection.supportFragmentManager, "test")
            bottomsheetdialog.enterTransition
        }
        binding.button3.setOnClickListener {
            currentquestion(binding.button3.text.toString())
        }
        binding.button4.setOnClickListener {
            currentquestion(binding.button4.text.toString())
        }
        binding.button5.setOnClickListener {
            currentquestion(binding.button5.text.toString())
        }
        binding.button6.setOnClickListener {
            currentquestion(binding.button6.text.toString())
        }



    }

    private fun currentquestion(S:String) {

        if(S.equals(questionlist.get(currentposition).ans)){
            score+=20
        }
        currentposition++
        if(currentposition>=questionlist.size){

            if((score/questionlist.size*20)*100>=60) {
                binding.winner.visibility = View.VISIBLE

                chances++

                Firebase.database.reference.child("Playchance").
                child(Firebase.auth.currentUser!!.uid)
                    .setValue(chances).addOnSuccessListener {
                        Toast.makeText(this@Quizsection,"Your spin chance is " + chances.toString(), Toast.LENGTH_SHORT).show()
                    }



            }else
            {
                binding.lost.visibility = View.VISIBLE
            }

        }else{
            binding.textView14.text = questionlist.get(currentposition).question
            binding.button3.text = questionlist.get(currentposition).option1
            binding.button4.text = questionlist.get(currentposition).option2
            binding.button5.text = questionlist.get(currentposition).option3
            binding.button6.text = questionlist.get(currentposition).option4
        }

    }
}