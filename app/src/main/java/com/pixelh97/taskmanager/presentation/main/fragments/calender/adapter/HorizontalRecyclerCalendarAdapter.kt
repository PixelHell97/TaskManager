package com.pixelh97.taskmanager.presentation.main.fragments.calender.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pixelh97.taskmanager.R
import com.pixelh97.taskmanager.databinding.ItemCalendarHorizontalBinding
import com.tejpratapsingh.recyclercalendar.adapter.RecyclerCalendarBaseAdapter
import com.tejpratapsingh.recyclercalendar.model.RecyclerCalendarConfiguration
import com.tejpratapsingh.recyclercalendar.model.RecyclerCalenderViewItem
import com.tejpratapsingh.recyclercalendar.utilities.CalendarUtils
import java.util.Calendar
import java.util.Date

class HorizontalRecyclerCalendarAdapter(
    startDate: Date,
    endDate: Date,
    private val configuration: RecyclerCalendarConfiguration,
    private var selectedDate: Date,
    private val dateSelectListener: OnDateSelected,
) : RecyclerCalendarBaseAdapter(startDate, endDate, configuration) {
    interface OnDateSelected {
        fun onDateSelected(date: Date)
    }

    inner class MonthCalendarViewHolder(
        private val binding: ItemCalendarHorizontalBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        val textViewDay = binding.textCalenderItemHorizontalDay
        val textViewDate = binding.textCalenderItemHorizontalDate
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val binding =
            ItemCalendarHorizontalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        return MonthCalendarViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        calendarItem: RecyclerCalenderViewItem,
    ) {
        val monthViewHolder: MonthCalendarViewHolder = holder as MonthCalendarViewHolder
        val context: Context = monthViewHolder.itemView.context
        monthViewHolder.itemView.visibility = View.VISIBLE

        monthViewHolder.itemView.setOnClickListener(null)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            monthViewHolder.itemView.background = null
        } else {
            monthViewHolder.itemView.setBackgroundDrawable(null)
        }
        monthViewHolder.textViewDay.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.colorBlack,
            ),
        )
        monthViewHolder.textViewDate.setTextColor(
            ContextCompat.getColor(
                context,
                R.color.colorBlack,
            ),
        )

        if (calendarItem.isHeader) {
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.time = calendarItem.date

            val month: String =
                CalendarUtils.dateStringFromFormat(
                    locale = configuration.calendarLocale,
                    date = selectedCalendar.time,
                    format = CalendarUtils.DISPLAY_MONTH_FORMAT,
                ) ?: ""
            val year = selectedCalendar[Calendar.YEAR].toLong()

            monthViewHolder.textViewDay.text = year.toString()
            monthViewHolder.textViewDate.text = month

            monthViewHolder.itemView.setOnClickListener(null)
        } else if (calendarItem.isEmpty) {
            monthViewHolder.itemView.visibility = View.GONE
            monthViewHolder.textViewDay.text = ""
            monthViewHolder.textViewDate.text = ""
        } else {
            val calendarDate = Calendar.getInstance()
            calendarDate.time = calendarItem.date

            val stringCalendarTimeFormat: String =
                CalendarUtils.dateStringFromFormat(
                    locale = configuration.calendarLocale,
                    date = calendarItem.date,
                    format = CalendarUtils.DB_DATE_FORMAT,
                )
                    ?: ""
            val stringSelectedTimeFormat: String =
                CalendarUtils.dateStringFromFormat(
                    locale = configuration.calendarLocale,
                    date = selectedDate,
                    format = CalendarUtils.DB_DATE_FORMAT,
                ) ?: ""

            if (stringCalendarTimeFormat == stringSelectedTimeFormat) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    monthViewHolder.itemView.background =
                        ContextCompat.getDrawable(context, R.drawable.layout_round_corner_filled)
                } else {
                    monthViewHolder.itemView.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.layout_round_corner_filled,
                        ),
                    )
                }
                monthViewHolder.textViewDay.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white,
                    ),
                )
                monthViewHolder.textViewDate.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white,
                    ),
                )
            }

            val day: String =
                CalendarUtils.dateStringFromFormat(
                    locale = configuration.calendarLocale,
                    date = calendarDate.time,
                    format = CalendarUtils.DISPLAY_WEEK_DAY_FORMAT,
                ) ?: ""

            monthViewHolder.textViewDay.text = day

            monthViewHolder.textViewDate.text =
                CalendarUtils.dateStringFromFormat(
                    locale = configuration.calendarLocale,
                    date = calendarDate.time,
                    format = CalendarUtils.DISPLAY_DATE_FORMAT,
                ) ?: ""

            monthViewHolder.itemView.setOnClickListener {
                selectedDate = calendarItem.date
                dateSelectListener.onDateSelected(calendarItem.date)
                notifyDataSetChanged()
            }
        }
    }
}
