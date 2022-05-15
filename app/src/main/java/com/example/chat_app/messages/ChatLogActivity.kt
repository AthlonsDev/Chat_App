package com.example.chat_app.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_app.R
import com.example.chat_app.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.chat_from_row.view.*

class ChatLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        supportActionBar?.title = user?.username

        val adapter = GroupAdapter<GroupieViewHolder>()

        adapter.add(ChatFromItems())
        adapter.add(ChatToItems())
        adapter.add(ChatFromItems())
        adapter.add(ChatToItems())
        adapter.add(ChatFromItems())
        adapter.add(ChatToItems())
        adapter.add(ChatFromItems())
        adapter.add(ChatToItems())

        messages_recycler_view.adapter = adapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

class ChatFromItems: Item<GroupieViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
//        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imageView.imageView)
    }

}

class ChatToItems: Item<GroupieViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
//        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imageView.imageView)
    }

}