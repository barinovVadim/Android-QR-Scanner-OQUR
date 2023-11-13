package com.example.wym_002

import android.provider.ContactsContract.Data
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "data")
    var data: String,
    @ColumnInfo(name = "spend")
    var spend: String,
    @ColumnInfo(name = "category")
    var category: String,
    @ColumnInfo(name = "card")
    var card: String,
    @ColumnInfo(name = "amount")
    var amount: Int,
)