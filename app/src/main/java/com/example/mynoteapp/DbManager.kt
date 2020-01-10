package com.example.mynoteapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.media.projection.MediaProjection
import android.widget.Toast

class DbManager {

    val dbName = "MyNotes"
    val dbTable = "Notes"
    val colID = "ID"
    val colTitle = "Title"
    val colDescription = "Description"
    val dbVersion =  1
    // CREATE TABLE IF NOT EXISTS MyNotes (1 INTEGER PRIMARY KEY, title TITLE, Description TEXT);
    val sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + dbTable +" ("+ colID +" INTEGER PRIMARY KEY,"+
            colTitle +" TEXT, " + colDescription +" TEXT);"
    var sqldb:SQLiteDatabase? = null

    constructor(context:Context){
        var db = DatabaseHelperNotes(context)
        sqldb = db.writableDatabase
    }
   inner class DatabaseHelperNotes:SQLiteOpenHelper{

       var context:Context? = null

        constructor(context:Context):super(context,dbName,null,dbVersion){
        this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {

            db!!.execSQL(sqlCreateTable)
            Toast.makeText(this.context,"Database is created",Toast.LENGTH_LONG).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

            db!!.execSQL("Drop table IF EXISTS " + dbTable)

        }


    }

    fun Insert(values:ContentValues):Long{
        val ID = sqldb!!.insert(dbTable,"",values)

        return ID
    }
    fun Query(projection: Array<String>, selection:String, selectionArg:Array<String>, SortOrder:String):Cursor{

        val qb = SQLiteQueryBuilder()
        qb.tables = dbTable
        val cursor = qb.query(sqldb,projection,selection,selectionArg,null,null,SortOrder)
        return cursor
    }
    fun Delete(selection:String, selectionArgs:Array<String>):Int{
        val count = sqldb!!.delete(dbTable,selection,selectionArgs)
        return count
    }
    fun Update(values:ContentValues, selection:String,selectionArg: Array<String>):Int{
        val count = sqldb!!.update(dbTable,values,selection, selectionArg)

        return count
    }

}