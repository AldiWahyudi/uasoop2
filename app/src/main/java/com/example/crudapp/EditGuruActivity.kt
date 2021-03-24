package com.example.crudapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crudapp.Database.AppRoomDB
import com.example.crudapp.Database.Constant
import com.example.crudapp.Database.Guru
import kotlinx.android.synthetic.main.activity_edit_guru.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditGuruActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    private var guruId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_guru)
        setupListener()
        setupView()
    }

    fun setupListener(){
        btn_saveGuru.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().addUser(
                    Guru(0, txt_namaL.text.toString(), txt_jabatan.text.toString())
                )
                finish()
            }
        }
        btn_updateGuru.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().updateUser(
                    Guru(guruId, txt_namaL.text.toString(), txt_jabatan.text.toString())
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
                btn_updateGuru.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_saveGuru.visibility = View.GONE
                btn_updateGuru.visibility = View.GONE
                getUser()
            }
            Constant.TYPE_UPDATE -> {
                btn_saveGuru.visibility = View.GONE
                getUser()
            }
        }
    }

    fun getUser() {
        guruId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val gurus =  db.userDao().getUser( guruId )[0]
            txt_namaL.setText( gurus.nama )
            txt_jabatan.setText( gurus.jabatan )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}