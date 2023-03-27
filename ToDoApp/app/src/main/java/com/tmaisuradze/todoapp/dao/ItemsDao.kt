package com.tmaisuradze.todoapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tmaisuradze.todoapp.entity.Item

@Dao
interface ItemsDao {

    @Insert
    fun addItem(item: Item)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateItem(item: Item)

    @Query("DELETE FROM item WHERE listId = :listId")
    fun removeItems(listId: Int)

    @Query("SELECT * FROM item WHERE listId = :listId")
    fun getListItems(listId: Int): List<Item>


}