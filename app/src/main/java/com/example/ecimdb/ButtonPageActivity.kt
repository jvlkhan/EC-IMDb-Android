package com.example.ecimdb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecimdb.adapter.MovieAdapter
import com.example.ecimdb.model.MovieModel

class ButtonPageActivity : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var edNote: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqliteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: MovieAdapter? = null
    private var mvn: MovieModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_page)
        val changeViewBtn = findViewById<Button>(R.id.backBtn)

        changeViewBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        initView()
        initRecyclerView()
        sqliteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener { addMovie() }
        btnView.setOnClickListener { (getMovie()) }
        btnUpdate.setOnClickListener { (updateMovie()) }

        adapter?.setOnClickItem {
            edName.setText(it.name)
            edNote.setText(it.note)
            mvn = it
        }

        adapter?.setOnClickDeleteItem {
            deleteMovieById(it.id)
        }
    }

    private fun getMovie() {
        val mvnList = sqliteHelper.getAllMovie()
        Log.e("get get get", "${mvnList.size}")
        adapter?.addItems(mvnList)
    }

    private fun addMovie() {
        val name = edName.text.toString()
        val note = edNote.text.toString()

        if (name.isEmpty() || note.isEmpty()) {
            Toast.makeText(this, "Please enter required field", Toast.LENGTH_SHORT).show()
        } else {
            val movie = MovieModel(name = name, note = note)
            val status = sqliteHelper.insertMovie(movie)
            if (status > -1) {
                Toast.makeText(this, "Movie added...", Toast.LENGTH_SHORT).show()
                clearEditText()
                getMovie()
            } else {
                Toast.makeText(this, "Record not saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateMovie() {
        val name = edName.text.toString()
        val note = edNote.text.toString()

        if (name == mvn?.name && note == mvn?.note) {
            Toast.makeText(this, "Not changed", Toast.LENGTH_SHORT).show()
            return
        }

        if (mvn == null) return

        val mvn = MovieModel(id = mvn!!.id, name = name, note = note)
        val status = sqliteHelper.updateMovie(mvn)

        if (status > -1) {
            clearEditText()
            getMovie()
        } else {
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteMovieById(id: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete the movie?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            sqliteHelper.deleteMovie(id)
            getMovie()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    private fun clearEditText() {
        edName.setText("")
        edNote.setText("")
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MovieAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        edName = findViewById(R.id.editTitle)
        edNote = findViewById(R.id.editNote)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)
    }
}