package com.example.crudadmin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudadmin.databinding.ActivityListBinding
import com.google.firebase.database.*

class ListDeleteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private lateinit var adapter: UserAdapter
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("searchName") ?: ""
        adapter = UserAdapter { user ->
            // Kirim ke DetailDeleteActivity
            startActivity(Intent(this, DetailDeleteActivity::class.java)
                .putExtra("userData", user))
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        db = FirebaseDatabase.getInstance().getReference("Groceries")
        db.orderByChild("name").equalTo(name)
            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = snapshot.children.mapNotNull { it.getValue(UserData::class.java) }
                    if (list.isEmpty()) {
                        Toast.makeText(this@ListDeleteActivity, "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
                        finish()
                    } else adapter.submitList(list)
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ListDeleteActivity, "Gagal: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}