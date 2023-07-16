package com.sample.firebaseapp.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport.Session.User
import com.sample.firebaseapp.RequestListener
import com.sample.firebaseapp.chat.viewholder.MessageListReceiverViewHolder
import com.sample.firebaseapp.chat.viewholder.MessageListSenderViewHolder
import com.sample.firebaseapp.databinding.LayoutMessageReceiverBinding
import com.sample.firebaseapp.databinding.LayoutMessageSenderBinding
import com.sample.firebaseapp.model.MessageModel
import com.sample.firebaseapp.model.UserModel
import kotlinx.coroutines.selects.select

class MessageListAdapter(
    private var items: ArrayList<MessageModel>?,
    private val currentUserId: String?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var messageViewType: MessageDetailEnum = MessageDetailEnum.SENDER
    private var longClickListener: ((MessageModel) -> Unit)? = null
    private var clickListener: ((MessageModel) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            MessageDetailEnum.RECEIVER.ordinal -> {
                MessageListReceiverViewHolder(
                    LayoutMessageReceiverBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                MessageListSenderViewHolder(
                    LayoutMessageSenderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }


    fun setLongClickListener(listener: (MessageModel) -> Unit) {
        longClickListener = listener
    }
    fun setClickListener(listener: (MessageModel) -> Unit) {
        clickListener = listener
    }

    fun updateData(list: ArrayList<MessageModel>?) {
        items = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is MessageListReceiverViewHolder) {
            holder.bind(items?.get(position))


        }

        if (holder is MessageListSenderViewHolder) {
            holder.bind(items?.get(position))

            // sadece gönderdiğimiz mesajları silmek burada silme işlemi yapılıyor.
            holder.itemView.setOnLongClickListener {
                val message = items?.get(position)
                message?.let {
                    longClickListener?.invoke(it)
                }
                true
            }
        }

        holder.itemView.setOnClickListener {

            val message = items?.get(position)
            message?.let {
                clickListener?.invoke(it)
            }
        }
    }

    fun delete(message: MessageModel) {
        message?.let {
            items?.remove(it)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }


    override fun getItemViewType(position: Int): Int {
        val message = items?.get(position)
        messageViewType = if (message?.userId == currentUserId) {
            MessageDetailEnum.SENDER
        } else {
            MessageDetailEnum.RECEIVER
        }
        return messageViewType.ordinal
    }

}


enum class MessageDetailEnum {
    RECEIVER,
    SENDER,
}