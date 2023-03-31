package com.example.projectalzheimer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.BitmapCompat
import com.example.projectalzheimer.databinding.ActivityDetailsBinding
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream
import java.io.IOException
import kotlin.Exception

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    var selectedBitmap : Bitmap? = null
    private lateinit var database: SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        database = this.openOrCreateDatabase("Kisiler", MODE_PRIVATE,null)

        registerLauncher()

        val intent =intent
        val info = intent.getStringExtra("info")
        if (info.equals("new")){
            binding.memoryNameText.setText("")
            binding.memoryNameText2.setText("")
            binding.memoryPhoneText.setText("")
            binding.button3.visibility = View.VISIBLE
            binding.imageView2.setImageResource(R.drawable.image)
        }else {
            binding.button3.visibility = View.INVISIBLE
            val selectedId = intent.getIntExtra("id",1)

            val cursor = database.rawQuery("SELECT * FROM persons WHERE id = ?", arrayOf(selectedId.toString()))

            val memoryNameIx = cursor.getColumnIndex("memoryname")
            val memoryLakapIx = cursor.getColumnIndex("memorylakap")
            val memoryPhoneIx = cursor.getColumnIndex("memoryphone")
            val memoryImageIx = cursor.getColumnIndex("image")

            while (cursor.moveToNext()){
                binding.memoryNameText.setText(cursor.getString(memoryNameIx))
                binding.memoryNameText2.setText(cursor.getString(memoryLakapIx))
                binding.memoryPhoneText.setText(cursor.getString(memoryPhoneIx))

                val byteArray = cursor.getBlob(memoryImageIx)
                val bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
                binding.imageView2.setImageBitmap(bitmap)
            }
            cursor.close()
        }
    }

    fun saveButtonClicked(view: View){
        val memoryName = binding.memoryNameText.text.toString()
        val memoryLakap = binding.memoryNameText2.text.toString()
        val memoryPhone = binding.memoryPhoneText.text.toString()

        if (selectedBitmap != null){
            val smallBitmap = makeSmallerBitmap(selectedBitmap!!,300)

            val outputStream = ByteArrayOutputStream()
            smallBitmap.compress(Bitmap.CompressFormat.PNG,50,outputStream)
            val byteArray = outputStream.toByteArray()

            try {
                //val database = this.openOrCreateDatabase("Kisiler", MODE_PRIVATE,null)
                database.execSQL("CREATE TABLE IF NOT EXISTS persons (id INTEGER PRIMARY KEY, memoryname VARCHAR, memorylakap VARCHAR, memoryphone VARCHAR,image BLOB)")
                val sqlString = "INSERT INTO persons (memoryname, memorylakap,memoryphone,image) VALUES (?,?,?,?)"
                val statement = database.compileStatement(sqlString)
                statement.bindString(1,memoryName)
                statement.bindString(2,memoryLakap)
                statement.bindString(3,memoryPhone)
                statement.bindBlob(4,byteArray)
                statement.execute()

            }catch (e: Exception){
                e.printStackTrace()
            }

            val intent = Intent(this@DetailsActivity,MemoryActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

    }

    private fun makeSmallerBitmap(image : Bitmap, maximumSize: Int) :Bitmap{
        var width = image.width
        var height = image.height

        val bitmapRatio : Double = width.toDouble() / height.toDouble()

        if(bitmapRatio > 1) {
            width = maximumSize
            val scaledHeight = width / bitmapRatio
            height = scaledHeight.toInt()
        } else {
            height = maximumSize
            val scaledWidth = height * bitmapRatio
            width = scaledWidth.toInt()
        }
        return Bitmap.createScaledBitmap(image,100,100,true)
    }

    fun selectImage(view: View){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(view, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",
                    View.OnClickListener {
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }).show()
            } else {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)
        }


    }

    private fun registerLauncher() {
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val intentFromResult = result.data
                if (intentFromResult != null) {
                    val imageData = intentFromResult.data
                    try {
                        if (Build.VERSION.SDK_INT >= 28) {
                            val source = ImageDecoder.createSource(this@DetailsActivity.contentResolver, imageData!!)
                            selectedBitmap = ImageDecoder.decodeBitmap(source)
                            binding.imageView2.setImageBitmap(selectedBitmap)
                        } else {
                            selectedBitmap = MediaStore.Images.Media.getBitmap(this@DetailsActivity.contentResolver, imageData)
                            binding.imageView2.setImageBitmap(selectedBitmap)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                //permission granted
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            } else {
                //permission denied
                Toast.makeText(this@DetailsActivity, "Permisson needed!", Toast.LENGTH_LONG).show()
            }
        }

}}