package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.quizapp.Fragment.Homefragment
import com.example.quizapp.Fragment.historyFragment
import com.example.quizapp.Fragment.profileFragment
import com.example.quizapp.Fragment.spinFragment
import com.example.quizapp.databinding.ActivityHomeBinding
import com.example.quizapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
   val navcontroller=findNavController(R.id.fragmentContainerView)
        val bottomNavigationView=findViewById<BottomNavigationView>(R.id.bottomNavigationView2)
        bottomNavigationView.setupWithNavController(navcontroller)



    }

}
