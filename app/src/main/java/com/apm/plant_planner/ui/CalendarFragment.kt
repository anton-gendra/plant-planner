package com.apm.plant_planner.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.apm.plant_planner.R
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    private lateinit var calendarView: CalendarView
    private lateinit var textNoEvents: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarView = view.findViewById(R.id.calendarView)
        textNoEvents = view.findViewById(R.id.textNoEvents)

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate.time)
            val actualDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

            if (formattedDate == actualDate) {
                textNoEvents.text = getString(R.string.no_events_today)
            } else if (selectedDate.time.before(Date())) {
                textNoEvents.text = getString(R.string.no_events_past, formattedDate)
            } else {
                textNoEvents.text = getString(R.string.no_events_future, formattedDate)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }
}
