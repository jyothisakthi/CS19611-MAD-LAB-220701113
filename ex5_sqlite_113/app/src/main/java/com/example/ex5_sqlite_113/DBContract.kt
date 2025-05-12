package com.example.ex5_sqlite_113

import android.provider.BaseColumns

object DBContract {
    object UserEntry : BaseColumns {
        const val TABLE_NAME = "users"
        const val COLUMN_REGISTER = "register_number"
        const val COLUMN_NAME = "name"
        const val COLUMN_CGPA = "cgpa"
    }
}
