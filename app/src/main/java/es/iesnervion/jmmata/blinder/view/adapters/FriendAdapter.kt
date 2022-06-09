package es.iesnervion.jmmata.blinder.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.iesnervion.jmmata.blinder.R
import es.iesnervion.jmmata.blinder.businessObject.FriendBO
import es.iesnervion.jmmata.blinder.databinding.RowChatBinding

class FriendAdapter (private val onFriendSelectedListener : (FriendBO)-> Unit):
    ListAdapter<FriendBO, FriendAdapter.FriendViewHolder>(UserDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_chat, parent, false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.binding.bind(getItem(position), onFriendSelectedListener)
    }

    inner class FriendViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = RowChatBinding.bind(view)
    }
}

object UserDiffCallback : DiffUtil.ItemCallback<FriendBO>() {
    override fun areItemsTheSame(oldItem: FriendBO, newItem: FriendBO): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: FriendBO, newItem: FriendBO): Boolean =
        oldItem == newItem
}

private fun RowChatBinding.bind(friend: FriendBO, onFriendSelectedListener: (FriendBO) -> Unit){
    root.setOnClickListener { onFriendSelectedListener(friend) }
    chatUserName.text = friend.friendName
    //TODO:
}