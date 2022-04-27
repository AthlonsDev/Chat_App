package com.example.chat_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        registe_button.setOnClickListener {
            val email = email_edtText.text.toString()
            val pass = pass_editText.text.toString()

            Log.d("mainact", "email is: $email")
            Log.d("mainact", "password is: $pass")
        }

        already_have_account.setOnClickListener {
            Log.d("mainact", "Try to show login activity")

//            launch login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}