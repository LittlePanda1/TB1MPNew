package com.example.crudadmin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudadmin.databinding.ActivityListBinding
import com.google.firebase.database.*  // untuk DatabaseReference, DataSnapshot, DatabaseError, ValueEventListener

class ListUpdateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private lateinit var adapter: UserAdapter
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("searchName")
        if (name.isNullOrBlank()) {
            Toast.makeText(this, "Nama pencarian kosong", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Siapkan RecyclerView
        adapter = UserAdapter { user ->
            val i = Intent(this, DetailUpdateActivity::class.java)
            i.putExtra("userData", user)
            startActivity(i)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Query Firebase berdasarkan field 'name'
        database = FirebaseDatabase.getInstance().getReference("Groceries")
        database
            .orderByChild("name")
            .equalTo(name)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<UserData>()
                    for (child: DataSnapshot in snapshot.children) {
                        child.getValue(UserData::class.java)?.let { list.add(it) }
                    }
                    if (list.isEmpty()) {
                        Toast.makeText(this@ListUpdateActivity, "Tidak ada data ditemukan", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        adapter.submitList(list)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ListUpdateActivity, "Gagal mengambil data: ${error.message}", Toast.LENGTH_LONG).show()
                }
            })
    }
}