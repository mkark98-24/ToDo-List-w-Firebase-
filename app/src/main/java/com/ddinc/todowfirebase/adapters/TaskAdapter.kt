package com.ddinc.todowfirebase.adapters

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import com.ddinc.todowfirebase.R
import com.ddinc.todowfirebase.Task
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.list_task_view.view.*


var query = FirebaseDatabase.getInstance()
        .reference
        .child("todos")

var options = FirebaseRecyclerOptions.Builder<Task>()
        .setQuery(query, Task::class.java)
        .build()

class TaskAdapter(val context: Context) : FirebaseRecyclerAdapter<Task, TaskAdapter.TaskViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.TaskViewHolder {
        val li = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = li.inflate(viewType, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int, model: Task) {
        holder.checkbox.isChecked = model.done
        holder.tvTask.text = model.name
        holder.tvDescription.text = model.description
        if (model.done) {
            holder.cardView.setCardBackgroundColor(Color.argb(70, 0, 255, 0))
        } else {
            holder.cardView.setCardBackgroundColor(Color.argb(255, 196, 196, 196))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.list_task_view
    }


    override fun onDataChanged() {
        // Called each time there is a new data snapshot. You may want to use this method
        // to hide a loading spinner or check for the "no documents" state and update your UI.
        // ...
    }

    override fun onError(e: DatabaseError) {
        // Called when there is an error getting data. You may want to update
        // your UI to display an error message to the user.
        // ...
    }


    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnDelete: Button = itemView.btnDelete
        val checkbox: CheckBox = itemView.checkbox
        val tvDescription: TextView = itemView.tvDescription
        val tvTask: TextView = itemView.tvTask
        val cardView: CardView = itemView.card

        init {
            checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
                val myRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://todowfirebase.firebaseio.com/todos/")
                myRef.child(getItem(this.adapterPosition).name).child("done").setValue(isChecked)
            }

            btnDelete.setOnClickListener {
                val myRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://todowfirebase.firebaseio.com/todos/")
                myRef.child(getItem(this.adapterPosition).name).removeValue()
            }
        }
    }
}