package com.example.wym_002.db

import android.provider.ContactsContract.Data
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Items (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "data")      // дата
    var data: String,
    @ColumnInfo(name = "spend")      // описание траты пользователя
    var spend: String,
    @ColumnInfo(name = "category")       // одна из 8 категорий
    var category: String,
    @ColumnInfo(name = "card")        // откуда трата
    var card: String,
    @ColumnInfo(name = "amount")      // сумма траты
    var amount: Int,
)

@Entity(tableName = "spends")
data class Spends (
    @PrimaryKey                        // дата
    var data: String,
    @ColumnInfo(name = "savings")     // сбережения
    var spend: Int,
    @ColumnInfo(name = "budget")    // бюджет
    var category: Int,
)