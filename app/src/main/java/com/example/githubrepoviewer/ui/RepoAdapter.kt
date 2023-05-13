package com.example.githubrepoviewer.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepoviewer.R
import com.example.githubrepoviewer.databinding.ActivityRepoItemBinding
import com.example.githubrepoviewer.extensions.loadImage
import com.example.githubrepoviewer.model.entities.Item

class RepoAdapter(private val listener :IonItemClickListener)  : ListAdapter<Item, RepoAdapter.RepoViewHolder>(
    object : DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.toString() == newItem.toString()
        }

    }
){

    inner class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(parent.context.getSystemService(LayoutInflater::class.java).inflate(
            R.layout.activity_repo_item, parent, false
        ))
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {

        ActivityRepoItemBinding.bind(holder.itemView).apply {

            val item = getItem(position)

            item.owner?.avatarUrl?.let { avatar.loadImage(it,false) }
            repoName.text = item.name
            star.text = "Star = ${item.stargazersCount}"
            language.text = "Language: ${item.language}"
            description.text = item.description

            holder.itemView.setOnClickListener {
                listener.onItemClick(item)
            }

        }

    }

}


interface IonItemClickListener {
    fun onItemClick(item:Item)
}