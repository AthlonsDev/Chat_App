package com.example.chat_app.views

import com.example.chat_app.R
import com.example.chat_app.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chat_from_row.view.*

class ChatFromItems(val text: String, val user: User): Item<GroupieViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.textView2.text = text
        val uri = user.profileImageUrl
        val target = viewHolder.itemView.imageView
        Picasso.get().load(uri).into(target)

    }

}

class ChatToItems(val text: String, val user: User): Item<GroupieViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.textView2.text = text

        val uri = user.profileImageUrl
        val target = viewHolder.itemView.imageView
        Picasso.get().load(uri).into(target)
    }


}