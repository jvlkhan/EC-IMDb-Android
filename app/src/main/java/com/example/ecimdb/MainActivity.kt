package com.example.ecimdb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecimdb.adapter.PostAdapter
import com.example.ecimdb.model.PostModel
import com.example.ecimdb.network.ApiService
import com.example.ecimdb.network.ServiceGen
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val changeViewBtn = findViewById<Button>(R.id.submitBtn)
        val recyclerView = findViewById<RecyclerView>(R.id.myRecyclerView)

        val serviceGen = ServiceGen.buildService(ApiService::class.java)
        val call = serviceGen.getPosts()

        call.enqueue(object : retrofit2.Callback<MutableList<PostModel>> {
            override fun onResponse(
                call: Call<MutableList<PostModel>>,
                response: Response<MutableList<PostModel>>
            ) {
                if (response.isSuccessful) {
                    Log.e("success", response.body().toString())
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = PostAdapter(response.body()!!)
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<PostModel>>, t: Throwable) {
                t.printStackTrace()
                Log.e("fail", t.message.toString())
            }

        })

        changeViewBtn.setOnClickListener {
            val intent = Intent(this, ButtonPageActivity::class.java)
            startActivity(intent)
        }
    }
}
