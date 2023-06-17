package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.modelclass.signupmodelclass
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.signup.setOnClickListener {
            if (binding.Name.text.toString().equals("") || binding.age.text.toString().equals("") ||
                binding.email.text.toString().equals("") ||
                binding.Password.text.toString().equals("")
            ) {
                Toast.makeText(this, "please fill the detail", Toast.LENGTH_SHORT).show()
            } else {
                Firebase.auth.createUserWithEmailAndPassword(
                    binding.email.text.toString(),
                    binding.Password.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {

                        var userdetail=signupmodelclass(binding.Name.text.toString(),
                            binding.age.text.toString().toInt(),
                            binding.email.text.toString(),
                            binding.Password.text.toString())

                        Firebase.database.reference.child("User").
                        child(Firebase.auth.currentUser!!.uid).
                        setValue(userdetail).addOnSuccessListener {
                           startActivity(Intent(this,Home::class.java))
                            finish()
                        }



                    } else {
                        Toast.makeText(this, it.exception?.localizedMessage, Toast.LENGTH_SHORT).show()

                    }
                }
            }


        }
    }

    override fun onStart() {
        super.onStart()
        if(Firebase.auth.currentUser!=null){
            startActivity(Intent(this,Home::class.java))
            finish()

        }
    }
}