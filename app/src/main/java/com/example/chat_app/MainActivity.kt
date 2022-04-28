package com.example.chat_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        registe_button.setOnClickListener {
          performRegister()
        }

        already_have_account.setOnClickListener {
            Log.d("mainact", "Try to show login activity")

//            launch login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }


    fun performRegister() {
        val email = email_edtText.text.toString()
        val pass = pass_editText.text.toString()

        if(email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please enter text in email/password", Toast.LENGTH_SHORT).show()
        }

        Log.d("mainact", "email is: $email")
        Log.d("mainact", "password is: $pass")

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener{
                if(!it.isSuccessful) return@addOnCompleteListener

                Log.d("firebase", "Successfully created user with uid: ${it.result.user?.uid}")
                Toast.makeText(this, "Successfully created user with uid: ${it.result.user?.uid}", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Log.d("firebase", "Failed to Create user: ${it.message}")
                Toast.makeText(this, "Failed to Create user: ${it.message}", Toast.LENGTH_SHORT).show()
            }

    }
}