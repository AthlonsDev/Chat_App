package com.example.chat_app

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener{
            val email = email_edtText.text.toString()
            val password = pass_editText.text.toString()

            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter text in email/password", Toast.LENGTH_SHORT).show()
            }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{
                    if(it.isSuccessful) {

                    }

                }
                .addOnFailureListener{
                    Toast.makeText(this, "Failed to login user: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

}