package com.example.calcvault

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class ImageAdapter(private val context: Context, private val images: List<File>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private var selectedImage: File? = null

    fun getSelectedImage(): File? {
        return selectedImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageFile = images[position]
        val bitmap: Bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
        holder.imageView.setImageBitmap(bitmap)

        // Highlight selected image
        holder.imageView.setBackgroundColor(
            if (imageFile == selectedImage) 0xFFCCCCCC.toInt() else 0x00000000
        )

        holder.imageView.setOnClickListener {
            selectedImage = imageFile
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = images.size

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}
