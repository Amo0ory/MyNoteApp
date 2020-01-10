package com.example.mynoteapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_note.*
import java.lang.Exception

class AddNote : AppCompatActivity() {

    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        var bundle:Bundle? =intent.extras

        try {
            id = bundle!!.getInt("ID", 0)
            if (id != 0) {
                edited_title.setText(bundle.getString("name"))
                edit_des.setText(bundle.getString("des"))
            }
        }catch (ex:Exception){}


    }

    fun buAdd(view:View){


        var dbManager = DbManager(this)
        var values = ContentValues()
        values.put("Title",edited_title.text.toString())
        values.put("Description",edit_des.text.toString())

        if(id == 0) {
            val ID = dbManager.Insert(values)
            if (ID > 0) {

                Toast.makeText(this, "Note has been added", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this, "Note cannot be added", Toast.LENGTH_LONG).show()
            }
        }else{
            var selectionArgs = arrayOf(id.toString())
            val ID = dbManager.Update(values,"ID=?", selectionArgs)
            if (ID > 0) {

                Toast.makeText(this, "Note has been added", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this, "Note cannot be added", Toast.LENGTH_LONG).show()
            }
        }



    }

    /*
    fun buFinish(view: View){

        finish()
    }

     */

}
