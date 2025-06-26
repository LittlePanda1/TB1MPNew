package com.example.crudadmin

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crudadmin.databinding.ActivityDetailDeleteBinding
import com.google.firebase.database.FirebaseDatabase

class DetailDeleteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailDeleteBinding
    private lateinit var user: UserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getParcelableExtra("userData") ?: return

        // Tampilkan detail
        binding.showName.text = user.name
        binding.showStore.text = user.storeName
        binding.showOperator.text = user.operator
        binding.showLocation.text = user.location
        binding.showPhone.text = user.phone

        binding.btnConfirmDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Hapus Pesanan")
                .setMessage("Yakin ingin menghapus pesanan “${user.name}” dari toko “${user.storeName}”?")
                .setPositiveButton("Ya") { _, _ ->
                    FirebaseDatabase.getInstance()
                        .getReference("Groceries")
                        .child(user.id)
                        .removeValue()
                        .addOnSuccessListener {
                            Toast.makeText(this, "Berhasil dihapus", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Gagal menghapus", Toast.LENGTH_SHORT).show()
                        }
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }
}