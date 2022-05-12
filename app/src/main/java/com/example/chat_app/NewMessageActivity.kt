package com.example.chat_app

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.user_row_new_messages.view.*
import kotlin.collections.forEach as forEach1

class NewMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        supportActionBar?.title = "Select User"

        var adapter = GroupAdapter<GroupieViewHolder>()
        adapter = GroupAdapter<GroupieViewHolder>()



        fetchUsers()

//        recyclerview_newmessages.layoutManager = LinearLayoutManager(this) Done in XML File
        recyclerview_newmessages.adapter = adapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun fetchUsers() {
//        Fetch Messages from Firebase Database
    val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            var adapter = GroupAdapter<GroupieViewHolder>()

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach1 {
                    Log.d("NewMessages", it.toString())
                    val user = it.getValue(User::class.java)
                    if (user != null) {
                        adapter.add(UserItems(user))
                    }
                }
                recyclerview_newmessages.adapter = adapter
            }
        })
    }
}

class UserItems(val user: User): Item<GroupieViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.user_row_new_messages
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
//        It will be called in our list for each user object
        viewHolder.itemView.textView_newmessages.text = user.username

    }

}
