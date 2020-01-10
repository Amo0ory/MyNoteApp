package com.example.mynoteapp

class Note {

    var noteID:Int? = null
    var noteContent:String? = null
    var notName:String? = null

    constructor(noteId:Int , noteContent:String, notName:String){

        this.notName = notName
        this.noteContent = noteContent
        this.noteID = noteId

    }
}