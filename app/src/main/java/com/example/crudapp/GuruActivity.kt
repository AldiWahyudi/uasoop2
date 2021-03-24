package com.example.crudapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudapp.Database.AppRoomDB
import com.example.crudapp.Database.Constant
import com.example.crudapp.Database.Guru
import kotlinx.android.synthetic.main.activity_guru.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GuruActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    lateinit var guruAdapter: GuruAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guru)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadUser()
    }
//menampilkan semua data //
    fun loadUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val allGuru = db.userDao().getAllUser()
            Log.d("GuruActivity", "dbResponse: $allGuru")
            withContext(Dispatchers.Main) {
                guruAdapter.setData(allGuru)
            }
        }
    }

    fun setupListener() {
        btn_createGuru.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun setupRecyclerView() {
        guruAdapter = GuruAdapter(arrayListOf(), object: GuruAdapter.OnAdapterListener {
            override fun onClick(guru: Guru) {
                intentEdit(guru.id, Constant.TYPE_READ)
            }
            override fun onDelete(guru: Guru) {
                deleteDialog(guru)
            }
            override fun onUpdate(guru: Guru) {
                // edit data
                intentEdit(guru.id, Constant.TYPE_UPDATE)
            }

        })
        list_guru.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = guruAdapter
        }
    }

    fun intentEdit(userId: Int, intentType: Int ) {
        startActivity(
            Intent(applicationContext, EditGuruActivity::class.java)
                .putExtra("intent_id", userId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun deleteDialog(guru: Guru) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin ingin menghapus data ini?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.userDao().deleteUser(guru)
                    loadUser()
                }
            }
        }
        alertDialog.show()
    }
}