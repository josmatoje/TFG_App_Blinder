package es.iesnervion.jmmata.blinder.view.adapters

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.iesnervion.jmmata.blinder.R
import es.iesnervion.jmmata.blinder.businessObject.FriendBO
import es.iesnervion.jmmata.blinder.databinding.RowChatBinding
import es.iesnervion.jmmata.blinder.databinding.RowFriendBinding

class FriendAdapter (private val onFriendSelectedListener : (FriendBO)-> Unit):
    ListAdapter<FriendBO, FriendAdapter.FriendViewHolder>(UserDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_friend, parent, false)
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.binding.bind(getItem(position), onFriendSelectedListener)
    }

    inner class FriendViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = RowFriendBinding.bind(view)
    }
}

object UserDiffCallback : DiffUtil.ItemCallback<FriendBO>() {
    override fun areItemsTheSame(oldItem: FriendBO, newItem: FriendBO): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: FriendBO, newItem: FriendBO): Boolean =
        oldItem == newItem
}

private fun RowFriendBinding.bind(friend: FriendBO, onFriendSelectedListener: (FriendBO) -> Unit){
    root.setOnClickListener { onFriendSelectedListener(friend) }
    freindName.text = friend.friendName
   when(friend.likes.size) {
        0 -> {
            chipLike4.visibility = View.GONE
            chipLike3.visibility = View.GONE
            chipLike2.visibility = View.GONE
            chipLike1.visibility = View.GONE
        }
       1 -> {
           chipLike4.visibility = View.GONE
           chipLike3.visibility = View.GONE
           chipLike2.visibility = View.GONE
           chipLike1.text = friend.likes[0]
       }
       2 -> {
           chipLike4.visibility = View.GONE
           chipLike3.visibility = View.GONE
           chipLike2.text = friend.likes[1]
           chipLike1.text = friend.likes[0]
       }
       3 -> {
           chipLike4.visibility = View.GONE
           chipLike3.text = friend.likes[2]
           chipLike2.text = friend.likes[1]
           chipLike1.text = friend.likes[0]
       }
        else -> {
            chipLike4.visibility = View.VISIBLE
            chipLike3.text = friend.likes[2]
            chipLike2.text = friend.likes[1]
            chipLike1.text = friend.likes[0]
        }
    }
}