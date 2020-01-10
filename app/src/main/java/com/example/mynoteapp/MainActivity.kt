package com.example.mynoteapp

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.BaseBundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.note_ticket.view.*
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {

    var listOfNote = ArrayList<Note>()
    var context:Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //loading data
        //listOfNote.add(Note(1,"Agility Network Solution Services has rich experience and expertise in the Industry of Information Technology, Logistics, Testing, Telecommunications, Media & Entertainment such as Business Process, Software development, Cloud deployment, Consultant services.","Job offer/Intern"))
        //listOfNote.add(Note(2,"Agility Network Solution Services has rich experience and expertise in the Industry of Information Technology, Logistics, Testing, Telecommunications, Media & Entertainment such as Business Process, Software development, Cloud deployment, Consultant services.","Job offer/Permenant"))
        //listOfNote.add(Note(3,"Agility Network Solution Services has rich experience and expertise in the Industry of Information Technology, Logistics, Testing, Telecommunications, Media & Entertainment such as Business Process, Software development, Cloud deployment, Consultant services.","Job offer/Part Time"))
        LoadQuery("%")
        //var adapter:notAdapter = notAdapter(this,listOfNote)
        //listOfNotes.adapter = adapter

        // load from database






    }

    override fun onResume() {
        super.onResume()
        LoadQuery("%")

    }

    fun LoadQuery(title:String){

        val projection = arrayOf("ID","Title","Description")
        var dbConnect = DbManager(this)
        val selectionArgs = arrayOf(title)
        val cursor = dbConnect.Query(projection,"Title like ?", selectionArgs,"Title")
        listOfNote.clear()

        if(cursor.moveToFirst()){

            do{

                val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                val Title = cursor.getString(cursor.getColumnIndex("Title"))
                val Description = cursor.getString(cursor.getColumnIndex("Description"))

                listOfNote.add(Note(ID,Description,Title))


            }while (cursor.moveToNext())
        }
        var adapter:notAdapter = notAdapter(this,listOfNote)
        listOfNotes.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.add ->{

                var intent = Intent(this, AddNote::class.java)
                startActivity(intent)


            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class notAdapter:BaseAdapter {

        var listOfNote = ArrayList<Note>()
        var context:Context? = null

        constructor(context:Context, listOfNote: ArrayList<Note>):super(){

           this.context = context
            this.listOfNote = listOfNote

        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var note = listOfNote[position]
            var myView = layoutInflater.inflate(R.layout.note_ticket,null)
            myView.title.text = note.notName
            myView.content.text = note.noteContent
            myView.delete.setOnClickListener(View.OnClickListener {
                var dbManager = DbManager(this.context!!)
                val selectionArgs = arrayOf(note.noteID.toString())
                dbManager.Delete("ID=?",selectionArgs)
                LoadQuery("%")

            })
            myView.edit.setOnClickListener(View.OnClickListener {
              //  GotoUpdate(note)
                var intent = Intent(this.context!!, AddNote::class.java)
                intent.putExtra("ID",note.noteID)
                intent.putExtra("des",note.noteContent)
                intent.putExtra("name",note.notName)
                startActivity(intent)
            })

            return myView
        }

        override fun getItem(position: Int): Any {
            return listOfNote[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()

        }

        override fun getCount(): Int {

            return listOfNote.size
        }
        fun GotoUpdate(note:Note){
            var intent = Intent(this.context!!, AddNote::class.java)
            intent.putExtra("ID",note.noteID)
            intent.putExtra("des",note.noteContent)
            intent.putExtra("name",note.notName)
            startActivity(intent)

        }


    }



}
