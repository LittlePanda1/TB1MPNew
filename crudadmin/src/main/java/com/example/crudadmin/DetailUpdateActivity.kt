package com.example.crudadmin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crudadmin.databinding.ActivityDetailUpdateBinding
import com.google.firebase.database.FirebaseDatabase

class DetailUpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUpdateBinding
    private lateinit var userData: UserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data dari intent
        userData = intent.getParcelableExtra("userData") ?: return

        // Tampilkan data awal di field
        binding.editStore.setText(userData.storeName)
        binding.editOperator.setText(userData.operator)
        binding.editLocation.setText(userData.location)
        binding.editPhone.setText(userData.phone)

        binding.saveUpdateButton.setOnClickListener {
            val updated = userData.copy(
                storeName = binding.editStore.text.toString(),
                operator = binding.editOperator.text.toString(),
                location = binding.editLocation.text.toString(),
                phone = binding.editPhone.text.toString(),
                timestamp = System.currentTimeMillis()
            )

            val database = FirebaseDatabase.getInstance().getReference("Groceries")
            database.child(updated.id).setValue(updated)
                .addOnSuccessListener {
                    Toast.makeText(this, "Berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal memperbarui", Toast.LENGTH_SHORT).show()
                }
        }
    }
}