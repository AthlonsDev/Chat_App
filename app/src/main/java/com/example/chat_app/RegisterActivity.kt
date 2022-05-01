package com.example.chat_app

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.view.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)




        registe_button.setOnClickListener {
          performRegister()
        }

        already_have_account.setOnClickListener {
            Log.d("mainact", "Try to show login activity")

//            launch login activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        select_photo_button.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

    }

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            photo_circle_image_view.setImageBitmap(bitmap)
            select_photo_button.alpha = 0F

//            val bitmapDrawable = BitmapDrawable(bitmap)
//            photo_circle_image_view.setImageDrawable(bitmapDrawable)

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

                uploadImage()
            }
            .addOnFailureListener{
                Log.d("firebase", "Failed to Create user: ${it.message}")
                Toast.makeText(this, "Failed to Create user: ${it.message}", Toast.LENGTH_SHORT).show()
            }

    }

    private fun uploadImage() {
        if(selectedPhotoUri == null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("firebase", "succesfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {

                    Log.d("firebase", "File location: ${it.toString()}")



                    saveUsertoDatabase(it.toString())
                }
            }
            .addOnFailureListener{

            }
    }

    private fun saveUsertoDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user = User(uid, username_edtText.text.toString(), profileImageUrl)

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("firebase", "Saved user to Firebase Database")
            }
    }
}


class User( val uid: String, val username: String, val profileImageUrl: String)