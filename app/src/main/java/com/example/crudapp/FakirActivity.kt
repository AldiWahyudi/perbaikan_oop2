package com.example.crudapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crudapp.Database.AppRoomDB
import com.example.crudapp.Database.Constant
import com.example.crudapp.Database.Fakir
import kotlinx.android.synthetic.main.activity_fakir.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FakirActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    lateinit var fakirAdapter: FakirAdapter
    //menampilkan semua data //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fakir)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadFakir()
    }

    fun loadFakir(){
        CoroutineScope(Dispatchers.IO).launch {
            val allFakir = db.FakirDao().getAllFakir()
            Log.d("FakirActivity", "dbResponse: $allFakir")
            withContext(Dispatchers.Main) {
                fakirAdapter.setData(allFakir)
            }
        }
    }

    fun setupListener() {
        btn_createFakir.setOnClickListener {
           intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun setupRecyclerView() {
        fakirAdapter = FakirAdapter(arrayListOf(), object: FakirAdapter.OnAdapterListener {
            override fun onClick(fakir: Fakir) {
                // read detail
                intentEdit(fakir.id, Constant.TYPE_READ)
            }

            override fun onDelete(fakir: Fakir) {
                // delete data
                deleteDialog(fakir)
            }

            override fun onUpdate(fakir: Fakir) {
                // edit data
                intentEdit(fakir.id, Constant.TYPE_UPDATE)
            }

        })
        list_fakir.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = fakirAdapter
        }
    }

    fun intentEdit(siswaId: Int, intentType: Int ) {
        startActivity(
            Intent(applicationContext, EditFakirActivity::class.java)
                .putExtra("intent_id", siswaId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun deleteDialog(fakir: Fakir) {
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
                    db.FakirDao().deleteFakir(fakir)
                    loadFakir()
                }
            }
        }
        alertDialog.show()
    }
}