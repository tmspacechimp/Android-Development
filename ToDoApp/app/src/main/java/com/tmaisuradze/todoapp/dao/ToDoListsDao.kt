package com.tmaisuradze.todoapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tmaisuradze.todoapp.entity.ToDoList

@Dao
interface ToDoListsDao {

    @Insert
    fun addList(toDoList: ToDoList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateList(toDoList: ToDoList): Long

    @Query("DELETE FROM ToDoList WHERE id=:listId")
    fun removeList(listId: Int)

    @Query("SELECT * FROM ToDoList WHERE id=:id")
    fun getList(id: Int): ToDoList

    @Query("SELECT * FROM ToDoList WHERE name LIKE :filterString ORDER BY modificationDate")
    fun getLists(filterString: String): List<ToDoList>

}