package com.example.chat_app.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_app.R
import com.example.chat_app.models.ChatMessage
import com.example.chat_app.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.chat_from_row.view.*

class ChatLogActivity : AppCompatActivity() {

    val adapter = GroupAdapter<GroupieViewHolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        supportActionBar?.title = user?.username

        messages_recycler_view.adapter = adapter
//        setupDummyData()
        listenForMessages()
        button_send.setOnClickListener{
            performsSendMessage()
        }


    }

    private fun listenForMessages() {
        val ref = FirebaseDatabase.getInstance().getReference("messages")
//        new data that belongs to the reference above
        ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)
                if (chatMessage != null) {
                    Log.d("chat", "$chatMessage.text ${FirebaseAuth.getInstance().uid}")


                    if(chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                        adapter.add(ChatFromItems(chatMessage.text))
                    } else {
                        adapter.add(ChatToItems(chatMessage.text))
                    }

                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }



    private  fun performsSendMessage() {

        val text = editText_messages.text.toString()

        val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val toId = user?.uid

        if(fromId == null) {
            return
        }

        val refernce = FirebaseDatabase.getInstance().getReference("/messages").push()

        val chatMessage = ChatMessage(refernce.key!!, fromId, toId!!, text, System.currentTimeMillis())
        refernce.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d("Chat Message", "Saved Message")
            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setupDummyData() {
        val adapter = GroupAdapter<GroupieViewHolder>()

        adapter.add(ChatFromItems("from Message"))
        adapter.add(ChatToItems("To message"))
        adapter.add(ChatFromItems("from Message"))
        adapter.add(ChatToItems("To message"))


        messages_recycler_view.adapter = adapter

    }
}

class ChatFromItems(val text: String): Item<GroupieViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
//        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imageView.imageView)
        viewHolder.itemView.textView2.text = text
    }

}

class ChatToItems(val text: String): Item<GroupieViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
//        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imageView.imageView)
        viewHolder.itemView.textView2.text = text
    }

}