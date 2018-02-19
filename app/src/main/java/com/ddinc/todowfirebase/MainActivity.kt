package com.ddinc.todowfirebase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.widget.Toast
import com.ddinc.todowfirebase.adapters.TaskAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var myRef: DatabaseReference
    lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fab.setOnClickListener {
            val taskTitle = tvtitle.text.toString()
            val taskDescription = tvdescription.text.toString()
            if (TextUtils.isEmpty(taskTitle)) {
                Toast.makeText(this@MainActivity, "You must enter a name for a task!", Toast.LENGTH_SHORT).show()
            } else {
                val newTaskObject = Task(taskTitle, taskDescription, false)
                myRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://todowfirebase.firebaseio.com/")
                myRef.child("todos").child(taskTitle).setValue(newTaskObject)
                tvtitle.setText("")
                tvdescription.setText("")
            }
        }
        taskAdapter = TaskAdapter(this)
        rvTasks.layoutManager = LinearLayoutManager(this)
        rvTasks.adapter = taskAdapter

    }

    override fun onStart() {
        super.onStart()
        taskAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        taskAdapter.stopListening()
    }
}
