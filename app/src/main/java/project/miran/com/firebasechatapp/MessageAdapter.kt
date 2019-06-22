package project.miran.com.firebasechatapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_message.view.*

class MessageAdapter(var list: MutableList<FriendlyMessage>) : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(list.get(position))
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
}