package com.fernandowagner104.todolist.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.fernandowagner104.todolist.databinding.ActivityAddTaskBinding
import com.fernandowagner104.todolist.datasource.TaskDataSource
import com.fernandowagner104.todolist.extensions.format
import com.fernandowagner104.todolist.extensions.text
import com.fernandowagner104.todolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root) // vai receber a root da view

        if (intent.hasExtra(TASK_ID)) {
            val taskId = intent.getIntExtra(TASK_ID, 0)

            TaskDataSource.findById(taskId)?.let {
                binding.tilTitle.text = it.title
                binding.tilDate.text = it.date
                binding.tilHour.text = it.hour

            }
        }

        insertListeners()

    }

    private fun insertListeners() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()

            datePicker.addOnPositiveButtonClickListener {

                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1 //isso teve quer ser feito para que a nossa hora fosse mostrada corretamente
                binding.tilDate.text =  Date(it + offset).format() // Date é uma classe da java.util, o .format e o .text são da nossa AppExtensions

            } // qnd usuário clicar no OK, a data será jogada o it: Long

            datePicker.show(supportFragmentManager,"DATE_PICKER_TAG") // o segundo parâmetro é apenas para criar um id para ele, mas ele pode ser null
        }

        binding.tilHour.editText?.setOnClickListener{
            val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()

            timePicker.addOnPositiveButtonClickListener {
                val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.tilHour.text = "$hour:$minute"

            }

            timePicker.show(supportFragmentManager,null)

        }


        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnNewTask.setOnClickListener {
            val task = Task(
                title = binding.tilTitle.text ,
                date = binding.tilDate.text ,
                hour = binding.tilHour.text,
                id = intent.getIntExtra(TASK_ID, 0)
            )

            TaskDataSource.insertTask(task)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    companion object {
        const val TASK_ID = "task_id"
    }

}