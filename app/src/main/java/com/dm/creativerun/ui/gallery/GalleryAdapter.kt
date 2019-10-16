package com.dm.creativerun.ui.gallery

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dm.creativerun.databinding.ListItemGalleryImageBinding
import com.dm.creativerun.domain.entity.Image

class GalleryAdapter(private val onImageReadyListener: OnImageReadyListener) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    var targetPosition = -1
    private val list = mutableListOf<Image>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGalleryImageBinding.inflate(inflater, parent, false)
        return GalleryViewHolder(binding, onImageReadyListener)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun submitList(list: List<Image>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class GalleryViewHolder(
        private val binding: ListItemGalleryImageBinding,
        private val onImageReadyListener: OnImageReadyListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(obj: Image) {
            binding.position = adapterPosition
            binding.src = obj.src
            binding.requestListener = object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    onImageReadyListener.onImageReady(adapterPosition)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    onImageReadyListener.onImageReady(adapterPosition)
                    return false
                }
            }
            binding.executePendingBindings()
        }
    }

    interface OnImageReadyListener {

        fun onImageReady(position: Int)
    }
}