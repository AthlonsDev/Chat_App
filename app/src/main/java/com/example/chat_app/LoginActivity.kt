package com.example.chat_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener{

        }

        create_account_text.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    fun performLogin() {
        val email = email_edtText.text.toString()
        val password = pass_editText.text.toString()

        if(email.isEmpty() || password.isEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{
                    if(it.isSuccessful) {
                        Log.d("firebase", "Successfully sign in user: ${it.result.user?.uid}")

//                        Proceed to app
                    }

                }
            Toast.makeText(this, "Please enter text in email/password", Toast.LENGTH_SHORT).show()
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if(it.isSuccessful) {

                    Toast.makeText(this, "Successfully logged user: ${it.result.user?.uid}", Toast.LENGTH_SHORT).show()
                }

            }
            .addOnFailureListener{
                Toast.makeText(this, "Failed to login user: ${it.message}", Toast.LENGTH_SHORT).show()
            }

    }

}