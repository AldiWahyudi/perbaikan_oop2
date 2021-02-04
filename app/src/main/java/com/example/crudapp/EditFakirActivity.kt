package com.example.crudapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crudapp.Database.AppRoomDB
import com.example.crudapp.Database.Constant
import com.example.crudapp.Database.Fakir
import kotlinx.android.synthetic.main.activity_edit_fakir.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditFakirActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    private var fakirId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_fakir)
        setupListener()
        setupView()
    }

    fun setupListener(){
        btn_saveFakir.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.FakirDao().addFakir(
                    Fakir(0, txt_nama.text.toString(), txt_alamat.text.toString(), Integer.parseInt(txt_jumlah.text.toString()) )
                )
                finish()
            }
        }
        btn_updateFakir.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.FakirDao().updateFakir(
                    Fakir(fakirId, txt_nama.text.toString(), txt_alamat.text.toString(), Integer.parseInt(txt_jumlah.text.toString()) )
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
                btn_updateFakir.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_saveFakir.visibility = View.GONE
                btn_updateFakir.visibility = View.GONE
                getFakir()
            }
            Constant.TYPE_UPDATE -> {
                btn_saveFakir.visibility = View.GONE
                getFakir()
            }
        }
    }

    fun getFakir() {
        fakirId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
           val fakirs =  db.FakirDao().getFakir( fakirId )[0]
            txt_nama.setText( fakirs.nama )
            txt_alamat.setText( fakirs.alamat )
            txt_jumlah.setText( fakirs.no.toString() )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}