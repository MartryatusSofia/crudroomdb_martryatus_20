package com.example.roomdb_martryatussofia_20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.roomdb_martryatussofia_20.room.Buku
import com.example.roomdb_martryatussofia_20.room.BukuDb
import com.example.roomdb_martryatussofia_20.room.Constant
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EditActivity : AppCompatActivity() {

    val db by lazy {BukuDb(this)}
    private  var bukuId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        setupListener()

    }

    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type",0)
        when (intentType){
            Constant.TYPE_CREATE -> {
                button_update.visibility = View.GONE

            }
            Constant.TYPE_READ -> {
                button_save.visibility = View.GONE
                button_update.visibility = View.GONE
                getbuku()
            }
            Constant.TYPE_UPDATE -> {
                button_save.visibility = View.GONE
                getbuku()
            }

        }
    }
     fun setupListener(){
         button_save.setOnClickListener{
             CoroutineScope(Dispatchers.IO).launch{
                 db.bukuDao().addBuku(
                     Buku (0, edit_title.text.toString(), edit_book.text.toString())
                 )
                 finish()
             }
         }
         button_update.setOnClickListener{
             CoroutineScope(Dispatchers.IO).launch{
                 db.bukuDao().updateBuku(
                     Buku (bukuId, edit_title.text.toString(), edit_book.text.toString())
                 )
                 finish()
             }
         }
     }

    fun getbuku(){
        bukuId= intent.getIntExtra("intent_id",0)
            CoroutineScope(Dispatchers.IO).launch {
                val bukuu = db.bukuDao().getbuku(bukuId)[0]
                edit_title.setText(bukuu.title)
                edit_book.setText(bukuu.desc)
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}