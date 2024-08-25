package com.pixelh97.taskmanager.presentation.main.fragments.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pixelh97.taskmanager.data.model.Project
import com.pixelh97.taskmanager.databinding.ItemProjectVpBinding

class ProjectCardsAdapter(
    private val projectList: List<Project>,
) : Adapter<ProjectCardsAdapter.CardViewHolder>() {
    inner class CardViewHolder(
        private val binding: ItemProjectVpBinding,
    ) : ViewHolder(binding.root) {
        fun bind(firstProject: Project) {
            binding.primary = firstProject
            /*if (secondProject == null) {
                binding.secondaryProjectCard.isGone = true
            } else {
                binding.secondary = secondProject
            }*/
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CardViewHolder {
        val binding =
            ItemProjectVpBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun getItemCount(): Int = projectList.size

    override fun onBindViewHolder(
        holder: CardViewHolder,
        position: Int,
    ) {
        val primaryCard = projectList[position]
        /*val secondaryCard =
            if (position != projectList.size - 1) {
                projectList[position + 1]
            } else {
                null
            }*/
        holder.bind(primaryCard)
    }
}
