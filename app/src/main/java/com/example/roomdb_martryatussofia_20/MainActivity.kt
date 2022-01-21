package com.example.roomdb_martryatussofia_20
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdb_martryatussofia_20.room.Buku
import com.example.roomdb_martryatussofia_20.room.BukuDb
import com.example.roomdb_martryatussofia_20.room.Constant
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy {BukuDb(this)}

    lateinit var bukuAdapter: BukuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
        setupRecyclearView()
    }

    override fun onStart() {
        super.onStart()
        loadBuku()

    }

    fun loadBuku(){
        CoroutineScope(Dispatchers.IO).launch{
            val bukuu = db.bukuDao().getbukuu()
            Log.d("MainActivity", "dbResponse: $bukuu")
            withContext(Dispatchers.Main){
                bukuAdapter.setData((bukuu))
            }
        }
    }

    fun setupListener(){
        button_create.setOnClickListener{
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(bukuId: Int, intentType: Int){
        startActivity(Intent(applicationContext, EditActivity::class.java)
            .putExtra("intent_id", bukuId)
            .putExtra("intent_type", intentType)
        )
    }
    private fun setupRecyclearView(){
        bukuAdapter = BukuAdapter(arrayListOf(), object : BukuAdapter.OnAdapterListener{
            override fun onClick(buku: Buku) {
                //Toast.makeText(applicationContext, buku.title,Toast.LENGTH_SHORT).show()
                //membaca detail buku
                intentEdit(buku.id, Constant.TYPE_READ)
            }

            override fun onUpdate(buku: Buku) {
                intentEdit(buku.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(buku: Buku) {
                deleteDialog(buku)
            }
        })
        list_book.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = bukuAdapter
        }
    }

    private fun deleteDialog(buku: Buku){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin hapus daftar buku ${buku.title}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
           }
            setPositiveButton("Hapus") { dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch{
                    db.bukuDao().deleteBuku(buku)
                    loadBuku()
                }
            }
        }
        alertDialog.show()
    }
}
