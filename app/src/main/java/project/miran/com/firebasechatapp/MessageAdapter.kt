package project.miran.com.firebasechatapp

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter (var list:MutableList<FriendlyMessage>): RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}