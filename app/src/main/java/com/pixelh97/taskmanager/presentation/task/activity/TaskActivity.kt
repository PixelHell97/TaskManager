package com.pixelh97.taskmanager.presentation.task.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pixelh97.taskmanager.R
import com.pixelh97.taskmanager.data.model.Task
import com.pixelh97.taskmanager.databinding.ActivityTaskBinding
import com.pixelh97.taskmanager.presentation.common.Constants.NAV_TASK_TYPE
import com.pixelh97.taskmanager.presentation.common.Constants.PASS_TASK
import com.pixelh97.taskmanager.presentation.task.TaskScreenType

class TaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initActionBar()
        initView(getNavType())
    }

    private fun initView(navType: TaskScreenType?) {
        when (navType) {
            TaskScreenType.ADD -> setUpAddTaskString()

            TaskScreenType.EDIT -> setUpEditTaskString()
            null -> throw IllegalArgumentException("No param passed")
        }
    }

    private fun setUpEditTaskString() {
        supportActionBar?.title = getString(R.string.edit_a_task)
        binding.btnAddOrEdit.text = getString(R.string.upgrade_task)
        val task = getTask()
    }

    private fun getTask(): Task? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(PASS_TASK, Task::class.java)
        } else {
            intent.getParcelableExtra(PASS_TASK)
        }

    private fun setUpAddTaskString() {
        supportActionBar?.title = getString(R.string.create_a_task)
        binding.btnAddOrEdit.text = getString(R.string.create_task)
    }

    private fun getNavType(): TaskScreenType? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(NAV_TASK_TYPE, TaskScreenType::class.java)
        } else {
            intent.getSerializableExtra(NAV_TASK_TYPE) as TaskScreenType
        }

    private fun initActionBar() {
        setSupportActionBar(binding.taskAppbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
