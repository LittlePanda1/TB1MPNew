package com.example.crudadmin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.crudadmin.databinding.ActivityUpdateBinding
import com.google.firebase.database.DatabaseReference
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.updateButton.setOnClickListener {
            val name = binding.updateName.text.toString()
            if (name.isNotEmpty()) {
                val intent = Intent(this, ListUpdateActivity::class.java)
                intent.putExtra("searchName", name)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Masukkan nama barang untuk diubah", Toast.LENGTH_SHORT).show()
            }
        }
    }
}