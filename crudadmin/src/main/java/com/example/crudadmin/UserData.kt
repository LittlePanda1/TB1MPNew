package com.example.crudadmin
import android.os.Parcelable
import kotlinx.parcelize.Parcelize





@Parcelize
data class UserData(
    val id: String = "", // 🔑 PRIMARY KEY
    val storeName: String? = null,
    val name: String? = null,
    val operator: String? = null,
    val location: String? = null,
    val phone: String? = null,
    val timestamp: Long = 0L
) : Parcelable