package com.example.crudadmin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crudadmin.databinding.ActivityUploadBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val storeName = binding.uploadStore.text.toString()
            val name = binding.uploadName.text.toString()
            val operator = binding.uploadOperator.text.toString()
            val location = binding.uploadLocation.text.toString()
            val phone = binding.uploadPhone.text.toString()

            if (storeName.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Nama Toko dan Nama Barang wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = UUID.randomUUID().toString()
            val timestamp = System.currentTimeMillis()
            val groceries = UserData(id, storeName, name, operator, location, phone, timestamp)

            database = FirebaseDatabase.getInstance().getReference("Groceries")
            database.child(id).setValue(groceries)
                .addOnSuccessListener {
                    clearInputs()
                    Toast.makeText(this, "Berhasil disimpan", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Gagal menyimpan: ${e.message}", Toast.LENGTH_LONG).show()
                    Log.e("UploadActivity", "Error upload", e)
                }
        }
    }

    private fun clearInputs() {
        binding.uploadStore.text.clear()
        binding.uploadName.text.clear()
        binding.uploadOperator.text.clear()
        binding.uploadLocation.text.clear()
        binding.uploadPhone.text.clear()
    }
}