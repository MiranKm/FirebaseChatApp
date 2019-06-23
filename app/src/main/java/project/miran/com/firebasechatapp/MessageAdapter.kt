package project.miran.com.firebasechatapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_message.view.*
import android.view.animation.AnimationUtils.loadAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils


class MessageAdapter() : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    private var list: MutableList<FriendlyMessage> = arrayListOf()
    private var lastPosition = -1

    fun addData(data: MutableList<FriendlyMessage>){
        list.addAll(data)
        notifyDataSetChanged()
    }

     fun addData(data: FriendlyMessage){
        list.add(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(list.get(position))
        setAnimation(holder.itemView, position);

    }

    fun clearAll() {
        list.clear()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoView = itemView.photoImageView
        val messageTV = itemView.messageTextView
        val authorTV = itemView.nameTextView


        fun setData(data: FriendlyMessage) {
            Glide.with(photoView.context).load(data.imageUrl).into(photoView)
            messageTV.text = data.text
            authorTV.text = data.name
        }
    }
    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation = loadAnimation(viewToAnimate.context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

}