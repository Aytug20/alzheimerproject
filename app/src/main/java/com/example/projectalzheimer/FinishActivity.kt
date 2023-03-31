package com.example.projectalzheimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectalzheimer.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {

    private var binding: ActivityFinishBinding? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        binding?.btnFinish?.setOnClickListener{
            finish()
        }
    }
}