package com.fernandowagner104.todolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.fernandowagner104.todolist.databinding.ActivityMainBinding
import com.fernandowagner104.todolist.datasource.TaskDataSource
import com.fernandowagner104.todolist.model.Task

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding // o lateinit quer dizer que a var vai ser inicializada só depois
    private val adapter by lazy { TaskListAdapter() } // o lazy vai esperar ser chamado o atributo para que se faça a instaciação do nosso adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTasks.adapter = adapter


        insertListeners()
        updateList()
    }

    private fun insertListeners() {
        binding.fab.setOnClickListener {
            startActivityForResult(Intent(this,AddTaskActivity::class.java), CREATE_NEW_TASK)
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)


        }

        adapter.listenerDelete = {
            TaskDataSource.deleteTask(it)
            updateList()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK ) updateList()
    }

    private fun updateList() { // atualizar o adapter da lista
        val list = TaskDataSource.getList()

        binding.includeEmpty.emptyState.visibility = if (list.isEmpty() ) View.VISIBLE
         else View.GONE

        adapter.submitList(TaskDataSource.getList())

    }

    companion object {
        private const val CREATE_NEW_TASK = 1000
    }
}