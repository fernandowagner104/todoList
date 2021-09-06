package com.fernandowagner104.todolist.datasource

import com.fernandowagner104.todolist.model.Task

object TaskDataSource {
    private val list = arrayListOf<Task>()

    fun getList() = list.toList() // vai pegar a nossa lista

    fun insertTask(task: Task) {  // vai popular a nossa lista
        if (task.id == 0 ) {
            list.add(task.copy(id = list.size + 1) ) // estamos colocando o id manual, a função "copy" vai copiar a nossa classe e permitir que a gente altere os seus valores, msm que nossa vairáveis sejam "val"

        } else {
            list.remove(task)
            list.add(task)
        }

    }

    fun findById(taskId: Int) = list.find { it.id == taskId }

    fun deleteTask(task: Task) {
        list.remove(task)

    }

}