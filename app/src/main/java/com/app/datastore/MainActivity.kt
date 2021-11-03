package com.app.datastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import com.app.datastore.data.UserManager
import com.app.datastore.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var userManager : UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userManager = UserManager(this)
        buttonsListener()
        observers()
    }

    private fun observers() {
        userManager.userAgeFlow.asLiveData().observe(this,{response->
            binding.ageTv.text = "Yaş : $response"
        })

        userManager.userNameFlow.asLiveData().observe(this,{response->
            binding.nameTv.text = "Ad : $response"
        })

        userManager.userSurnameFlow.asLiveData().observe(this,{response->
            binding.surnameTv.text = "Soyad : $response"
        })
    }

    private fun buttonsListener() {
        binding.saveBtn.setOnClickListener {
            GlobalScope.launch {
                userManager.saveUser(binding.nameEt.text.toString(),binding.surnameEt.text.toString(),binding.ageEt.text.toString().toInt())
            }
        }

        binding.deleteBtn.setOnClickListener {
            GlobalScope.launch {
                userManager.clearUser()
            }
        }
    }


}