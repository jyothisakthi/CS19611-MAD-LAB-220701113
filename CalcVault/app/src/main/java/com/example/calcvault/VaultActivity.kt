package com.example.calcvault

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.OpenableColumns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class VaultActivity : AppCompatActivity() {

    private lateinit var imageRecyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var uploadButton: Button
    private lateinit var removeButton: Button

    private val imageFiles = mutableListOf<File>()
    private val PICK_IMAGE_REQUEST = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vault)

        uploadButton = findViewById(R.id.uploadButton)
        removeButton = findViewById(R.id.removeButton)
        imageRecyclerView = findViewById(R.id.imageRecyclerView)

        imageRecyclerView.layoutManager = GridLayoutManager(this, 2)
        imageAdapter = ImageAdapter(this, imageFiles)
        imageRecyclerView.adapter = imageAdapter

        loadImages()

        uploadButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST)
        }

        removeButton.setOnClickListener {
            val selected = imageAdapter.getSelectedImage()
            if (selected != null && selected.exists()) {
                val recoveryFolder = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Recovered")
                if (!recoveryFolder.exists()) recoveryFolder.mkdirs()

                val destination = File(recoveryFolder, selected.name)
                selected.copyTo(destination, overwrite = true)
                selected.delete()

                Toast.makeText(this, "Image moved to Recovered folder", Toast.LENGTH_SHORT).show()
                loadImages()
            } else {
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadImages() {
        val vaultFolder = File(filesDir, "vault")
        if (!vaultFolder.exists()) vaultFolder.mkdir()

        imageFiles.clear()
        imageFiles.addAll(vaultFolder.listFiles()?.toList() ?: emptyList())
        imageAdapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            imageUri?.let { uri ->
                saveImageToVault(uri)
                loadImages()
            }
        }
    }
    fun removeImageFromVaultAndRestore(file: File) {
        val restoredFolder = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Restored")
        if (!restoredFolder.exists()) restoredFolder.mkdirs()

        val restoredFile = File(restoredFolder, file.name)

        try {
            file.copyTo(restoredFile, overwrite = true)
            file.delete()
            Toast.makeText(this, "Moved to Restored", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }

        loadImages() // refresh the RecyclerView
    }

    private fun saveImageToVault(uri: Uri) {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val fileName = getFileName(uri)
        val vaultFolder = File(filesDir, "vault")
        if (!vaultFolder.exists()) vaultFolder.mkdir()

        val file = File(vaultFolder, fileName)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)

        inputStream?.close()
        outputStream.close()
        Toast.makeText(this, "Image uploaded to vault", Toast.LENGTH_SHORT).show()
    }

    private fun getFileName(uri: Uri): String {
        var name = "vault_image.jpg"
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                name = it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
            }
        }
        return name
    }
}
