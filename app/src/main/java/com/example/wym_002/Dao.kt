package com.example.wym_002
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun insertItem(item: Item)
    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<Item>>

    //@Query()                       // ДОПИСАТЬ!!!!
    //fun getSpendingPerDay():Flow<List<Item>> // получение трат за день

    // нужно прописать функции для экранов
    // (вытягивание информации из таблицы для вывода на экраны)
}