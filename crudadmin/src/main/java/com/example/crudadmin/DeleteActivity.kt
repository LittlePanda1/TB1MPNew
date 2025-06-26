package com.example.crudadmin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crudadmin.databinding.ActivityDeleteBinding

class DeleteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.deleteButton.setOnClickListener {
            val name = binding.deleteName.text.toString().trim()
            if (name.isNotEmpty()) {
                startActivity(Intent(this, ListDeleteActivity::class.java)
                    .putExtra("searchName", name))
            } else {
                Toast.makeText(this, "Masukkan nama barang untuk dihapus", Toast.LENGTH_SHORT).show()
            }
        }
    }
}