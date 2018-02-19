package com.ddinc.todowfirebase

class Task() {

    lateinit var name: String
    lateinit var description: String
    var done: Boolean = false

    constructor(name: String,
                description: String,
                done: Boolean) : this() {
        this.name = name
        this.description = description
        this.done = done
    }


}