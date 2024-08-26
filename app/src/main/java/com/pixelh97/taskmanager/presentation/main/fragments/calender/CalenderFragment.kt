package com.pixelh97.taskmanager.presentation.main.fragments.calender

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.PagerSnapHelper
import com.pixelh97.taskmanager.data.model.Task
import com.pixelh97.taskmanager.data.model.TaskState
import com.pixelh97.taskmanager.databinding.FragmentCalenderBinding
import com.pixelh97.taskmanager.presentation.common.Constants.NAV_TASK_TYPE
import com.pixelh97.taskmanager.presentation.common.adapter.TaskAdapter
import com.pixelh97.taskmanager.presentation.main.fragments.calender.adapter.HorizontalRecyclerCalendarAdapter
import com.pixelh97.taskmanager.presentation.task.TaskScreenType
import com.pixelh97.taskmanager.presentation.task.activity.TaskActivity
import com.tejpratapsingh.recyclercalendar.model.RecyclerCalendarConfiguration
import com.tejpratapsingh.recyclercalendar.utilities.CalendarUtils
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CalenderFragment : Fragment() {
    companion object {
        private const val DATE_FORMAT = "MMM, yyyy"
    }

    private var _binding: FragmentCalenderBinding? = null
    private val binding get() = _binding!!
    private lateinit var tasksAdapter: TaskAdapter
    private val fakeDataList =
        listOf(
            Task(
                0,
                "Design App",
                "Design todo app",
                Calendar.getInstance().time,
                null,
                null,
                null,
                TaskState.IN_PROGRESS,
            ),
            Task(
                1,
                "Cook App",
                "Design todo app",
                Calendar.getInstance().time,
                null,
                null,
                null,
                TaskState.IN_PROGRESS,
            ),
            Task(
                2,
                "ABDo App",
                "Design todo app",
                Calendar.getInstance().time,
                null,
                null,
                null,
                TaskState.IN_PROGRESS,
            ),
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCalenderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initCalender()
        initTaskAdapter()
        binding.btnAddTask.setOnClickListener {
            val intent = Intent(requireActivity(), TaskActivity::class.java)
            intent.putExtra(NAV_TASK_TYPE, TaskScreenType.ADD)
            startActivity(intent)
        }
    }

    private fun initTaskAdapter() {
        tasksAdapter = TaskAdapter(fakeDataList)
        binding.rvTasks.adapter = tasksAdapter
    }

    private fun initCalender() {
        val date = Date()
        date.time = System.currentTimeMillis()

        val startCal = Calendar.getInstance()

        val endCal = Calendar.getInstance()
        endCal.time = date
        endCal.add(Calendar.MONTH, 3)

        val configuration =
            RecyclerCalendarConfiguration(
                calenderViewType = RecyclerCalendarConfiguration.CalenderViewType.HORIZONTAL,
                calendarLocale = Locale.getDefault(),
                includeMonthHeader = true,
            )
        configuration.weekStartOffset = RecyclerCalendarConfiguration.START_DAY_OF_WEEK.MONDAY

        binding.currentDateAndPiker.text = CalendarUtils.dateStringFromFormat(
            locale = configuration.calendarLocale,
            date = date,
            format = DATE_FORMAT,
        ) ?: ""

        CalendarUtils

        Log.e("date -> ", "${date.time}")
        val calendarAdapterHorizontal =
            HorizontalRecyclerCalendarAdapter(
                startDate = startCal.time,
                endDate = endCal.time,
                configuration = configuration,
                selectedDate = date,
                dateSelectListener =
                    object : HorizontalRecyclerCalendarAdapter.OnDateSelected {
                        override fun onDateSelected(date: Date) {
                            binding.currentDateAndPiker.text = CalendarUtils.dateStringFromFormat(
                                locale = configuration.calendarLocale,
                                date = date,
                                format = DATE_FORMAT,
                            ) ?: ""
                        }
                    },
            )

        binding.calendarRecyclerView.adapter = calendarAdapterHorizontal

        val snapHelper = PagerSnapHelper() // Or LinearSnapHelper
        snapHelper.attachToRecyclerView(binding.calendarRecyclerView)
    }
}
