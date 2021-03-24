package com.example.crudapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudapp.Database.AppRoomDB
import com.example.crudapp.Database.Constant
import com.example.crudapp.Database.Siswa
import kotlinx.android.synthetic.main.activity_siswa.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SiswaActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    lateinit var siswaAdapter: SiswaAdapter
    //menampilkan semua data //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_siswa)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadHelm()
    }

    fun loadHelm(){
        CoroutineScope(Dispatchers.IO).launch {
            val allSiswa = db.helmDao().getAllHelm()
            Log.d("SiswaActivity", "dbResponse: $allSiswa")
            withContext(Dispatchers.Main) {
                siswaAdapter.setData(allSiswa)
            }
        }
    }

    fun setupListener() {
        btn_createSiswa.setOnClickListener {
           intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun setupRecyclerView() {
        siswaAdapter = SiswaAdapter(arrayListOf(), object: SiswaAdapter.OnAdapterListener {
            override fun onClick(siswa: Siswa) {
                // read detail
                intentEdit(siswa.id, Constant.TYPE_READ)
            }

            override fun onDelete(siswa: Siswa) {
                // delete data
                deleteDialog(siswa)
            }

            override fun onUpdate(siswa: Siswa) {
                // edit data
                intentEdit(siswa.id, Constant.TYPE_UPDATE)
            }

        })
        list_siswa.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = siswaAdapter
        }
    }

    fun intentEdit(siswaId: Int, intentType: Int ) {
        startActivity(
            Intent(applicationContext, EditSiswaActivity::class.java)
                .putExtra("intent_id", siswaId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun deleteDialog(siswa: Siswa) {
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
                    db.helmDao().deleteHelm(siswa)
                    loadHelm()
                }
            }
        }
        alertDialog.show()
    }
}