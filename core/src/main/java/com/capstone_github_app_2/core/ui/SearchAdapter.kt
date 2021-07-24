package com.capstone_github_app_2.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone_github_app_2.core.R
import com.capstone_github_app_2.core.domain.model.User
import com.example.capstone_github_app_2.core.databinding.GithubItemListBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var searchData = ArrayList<User>()

    var onItemClick : ((User) -> Unit)? = null

    fun putData(newListData : List<User>?)
    {
        if (newListData == null) return
        searchData.clear()
        searchData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(LayoutInflater.from(view.context)
                .inflate(R.layout.github_item_list, view, false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val sData = searchData[position]
        holder.bind(sData)
    }

    override fun getItemCount(): Int {
        return searchData.size
    }

    inner class SearchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        private val binding = GithubItemListBinding.bind(itemView)

        fun bind(data : User)
        {
            with (binding)
            {
                Glide.with(itemView.context).load(data.avatarUrl).into(userImage)
                userName.text = data.login
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(searchData[adapterPosition])
            }
        }
    }
}