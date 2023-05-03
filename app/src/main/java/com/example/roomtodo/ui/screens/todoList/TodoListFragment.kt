package com.example.roomtodo.ui.screens.todoList

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomtodo.R
import com.example.roomtodo.Utils.Extensions.SharedPref
import com.example.roomtodo.adapters.ToDoListAdapter
import com.example.roomtodo.databinding.FragmentTodoListBinding
import com.example.roomtodo.models.TodoModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TodoListFragment : Fragment(R.layout.fragment_todo_list) {
    private lateinit var todoListBinding: FragmentTodoListBinding

    private val viewModel: TodoListViewModel by viewModels()
    private lateinit var adapter: ToDoListAdapter

    @Inject
    lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObserve()
        adapter = ToDoListAdapter { action, task ->
            when (action) {
                "isChecked" -> updateCheckState(task)
                "general" -> showOptionsforTask(task)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        todoListBinding = FragmentTodoListBinding.bind(view)

        initViews()
        todoListBinding.logoutFabBtnTodolist.setOnClickListener {
            sharedPref.resetPref()
            findNavController().popBackStack()
        }
        todoListBinding.addTaskFabBtnTodoList.setOnClickListener {
            addNewTask()
        }
        loadTask()
    }

    //    @SuppressLint("ResourceAsColor")
    private fun addNewTask() {
        val editTaskContainer = TextInputLayout(
            requireContext(),
            null,
            com.google.android.material.R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox
        )
        editTaskContainer.setPadding(10)
        val editTask = TextInputEditText(requireContext())
        editTask.hint = "Task:"
        editTask.textSize = 17.0F
        editTask.inputType = InputType.TYPE_CLASS_TEXT
//        editTask.setTextColor(R.color.sky_blue)

        editTaskContainer.addView(editTask)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("WANTS TO ADD NEW TASK?")
            .setView(editTaskContainer)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("ADD TASK") { _, _ ->
                viewModel.insertTask(editTask.text.toString(), sharedPref.getUser())
            }.show()
    }

    private fun loadTask() {
        viewModel.getTaskList(sharedPref.getUser())
    }

    private fun initObserve() {
        viewModel.taskList.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun showOptionsforTask(task: TodoModel) {
        val editTask = EditText(requireContext())
        editTask.hint = "Task:"
        editTask.inputType = InputType.TYPE_CLASS_TEXT
        editTask.textSize = 17.0F
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Or Update: ")
            .setView(editTask)
            .setNegativeButton("Cancel", null)
            .setNeutralButton("Update") { _, _ ->
                val editedTask = editTask.text.toString()
                viewModel.updateTask(
                    TodoModel(
                        tid = task.tid,
                        user = task.user,
                        task = editedTask,
                        check = task.check
                    )
                )
            }
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteTask(task)
            }.show()
    }

    private fun updateCheckState(task: TodoModel) {
        val isChecked = task.check
        val newTask =
            TodoModel(tid = task.tid, user = task.user, task = task.task, check = !isChecked)
        viewModel.updateTask(newTask)
    }

    private fun initViews() {
        val layoutManager = LinearLayoutManager(activity)
        todoListBinding.recyclerViewTodoList.setHasFixedSize(true)
        todoListBinding.recyclerViewTodoList.layoutManager = layoutManager
        todoListBinding.recyclerViewTodoList.adapter = adapter
    }
}
