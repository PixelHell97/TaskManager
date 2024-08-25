package com.pixelh97.taskmanager.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pixelh97.taskmanager.data.model.Task
import com.pixelh97.taskmanager.databinding.ItemTaskBinding

class TaskAdapter(
    private val tasksList: List<Task>,
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(
        private val binding: ItemTaskBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.taskName.text = task.title
            binding.taskCreatedFrom.text = task.formatDurationFromNow()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int = tasksList.size

    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int,
    ) {
        val task = tasksList[position]
        holder.bind(task)
    }
}
