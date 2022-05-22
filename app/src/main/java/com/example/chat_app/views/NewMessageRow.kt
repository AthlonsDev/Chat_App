package com.example.chat_app.views

import com.example.chat_app.R
import com.example.chat_app.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.user_row_new_messages.view.*

class UserItems(val user: User): Item<GroupieViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.user_row_new_messages
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
//        It will be called in our list for each user object
        viewHolder.itemView.textView_newmessages.text = user.username
        val thumbnail = viewHolder.itemView.imageView_newmessages
        Picasso.get().load(user.profileImageUrl).into(thumbnail)

    }

}
