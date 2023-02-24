package com.romany.newspaperapp_seena.ui.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.romany.newspaperapp_seena.databinding.ActivityShowImageBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class ShowImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowImageBinding.inflate(LayoutInflater.from(this), null, false)
        setContentView(binding.root)

        val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        val url = intent.extras!!.getString("url").toString()

        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    binding.imageToView.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Do something when the image is no longer available.
                }
            })

        binding.imageBackIcon.setOnClickListener {
            onBackPressed()
        }

        binding.imageShareIcon.setOnClickListener {

            val bitmap = (binding.imageToView.drawable as BitmapDrawable).bitmap

            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(Date())
            val imageFileName = "JPEG_" + timeStamp + "_"

            saveImage(bitmap, imageFileName)
        }
    }


    private fun shareImage(filePath: String) {
        val file = File(filePath)
        val uri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            FileProvider.getUriForFile(
                this,
                this.packageName.toString() + ".provider",
                file
            )
        } else {
            Uri.fromFile(file)
        }
        val intentShare = Intent(Intent.ACTION_SEND)
        intentShare.type = "image/jpeg"
        intentShare.putExtra(Intent.EXTRA_STREAM, uri)
        intentShare.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivity(Intent.createChooser(intentShare, "Share File.."))
    }

    private fun saveImage(bitmap: Bitmap?, imageName:String?) {
        val file = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS
            ), "$imageName.jpg"
        )
        if (file.exists())
            file.delete()
        try {
            val out = FileOutputStream(file)
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()

            out.close()
            shareImage(file.path)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }
}