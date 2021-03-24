package com.example.crudapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crudapp.Database.AppRoomDB
import com.example.crudapp.Database.Constant
import com.example.crudapp.Database.Siswa
import kotlinx.android.synthetic.main.activity_edit_siswa.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditSiswaActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    private var helmId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_siswa)
        setupListener()
        setupView()
    }

    fun setupListener(){
        btn_saveSiswa.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.helmDao().addHelm(
                    Siswa(0, txt_nama.text.toString(), txt_alamat.text.toString(), Integer.parseInt(txt_kelas.text.toString()) )
                )
                finish()
            }
        }
        btn_updateSiswa.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.helmDao().updateHelm(
                    Siswa(helmId, txt_nama.text.toString(), txt_alamat.text.toString(), Integer.parseInt(txt_kelas.text.toString()) )
                )
                finish()
            }
        }
    }

    fun setupView() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                btn_updateSiswa.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_saveSiswa.visibility = View.GONE
                btn_updateSiswa.visibility = View.GONE
                getHelm()
            }
            Constant.TYPE_UPDATE -> {
                btn_saveSiswa.visibility = View.GONE
                getHelm()
            }
        }
    }

    fun getHelm() {
        helmId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
           val siswas =  db.helmDao().getHelm( helmId )[0]
            txt_nama.setText( siswas.nama )
            txt_alamat.setText( siswas.alamat.toString() )
            txt_kelas.setText( siswas.kelas.toString() )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}