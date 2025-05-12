package com.example.ex5_sqlite_113

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UsersDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "StudentDB.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE ${DBContract.UserEntry.TABLE_NAME} (" +
                "${DBContract.UserEntry.COLUMN_REGISTER} TEXT PRIMARY KEY, " +
                "${DBContract.UserEntry.COLUMN_NAME} TEXT, " +
                "${DBContract.UserEntry.COLUMN_CGPA} REAL)"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DBContract.UserEntry.TABLE_NAME}")
        onCreate(db)
    }

    // Insert Student Record
    fun insertUser(user: UserModel): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(DBContract.UserEntry.COLUMN_REGISTER, user.registerNumber)
            put(DBContract.UserEntry.COLUMN_NAME, user.name)
            put(DBContract.UserEntry.COLUMN_CGPA, user.cgpa)
        }
        val result = db.insert(DBContract.UserEntry.TABLE_NAME, null, values)
        db.close()
        return result != -1L
    }

    // Update Student Record
    fun updateUser(user: UserModel): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(DBContract.UserEntry.COLUMN_NAME, user.name)
            put(DBContract.UserEntry.COLUMN_CGPA, user.cgpa)
        }
        val result = db.update(
            DBContract.UserEntry.TABLE_NAME,
            values,
            "${DBContract.UserEntry.COLUMN_REGISTER} = ?",
            arrayOf(user.registerNumber)
        )
        db.close()
        return result > 0
    }

    // Delete Student Record
    fun deleteUser(registerNumber: String): Boolean {
        val db = this.writableDatabase
        val result = db.delete(
            DBContract.UserEntry.TABLE_NAME,
            "${DBContract.UserEntry.COLUMN_REGISTER} = ?",
            arrayOf(registerNumber)
        )
        db.close()
        return result > 0
    }

    // Retrieve All Users
    fun getAllUsers(): List<UserModel> {
        val usersList = mutableListOf<UserModel>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM ${DBContract.UserEntry.TABLE_NAME}", null)

        if (cursor.moveToFirst()) {
            do {
                val register = cursor.getString(0)
                val name = cursor.getString(1)
                val cgpa = cursor.getDouble(2)
                usersList.add(UserModel(register, name, cgpa))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return usersList
    }
}
