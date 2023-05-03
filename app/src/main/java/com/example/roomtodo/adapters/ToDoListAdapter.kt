package com.example.roomtodo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomtodo.databinding.TaskListBinding
import com.example.roomtodo.models.TodoModel

class ToDoListAdapter(
    private val onClick: (action: String, TodoModel) -> Unit
) : ListAdapter<TodoModel, ToDoListAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(
        private val binding: TaskListBinding,
        private val onClick: (action:String, TodoModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TodoModel) {
            binding.showTaskTVTaskList.text = item.task
            binding.checkBoxTaskList.isChecked = item.check

            binding.showTaskTVTaskList.setOnClickListener {
                onClick("general",item)
            }
            binding.checkBoxTaskList.setOnClickListener {
                onClick("isChecked", item)
            }
        }

        companion object {
            fun from(
                parent: ViewGroup,
                onCLick: (action :String,TodoModel) -> Unit
            ): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, onCLick)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = getItem(position)
        return holder.bind(task)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TodoModel>() {
            override fun areItemsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
                return oldItem.tid == newItem.tid
            }

            override fun areContentsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
