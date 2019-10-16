package com.dm.creativerun.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.dm.creativerun.databinding.ListItemImageBinding
import com.dm.creativerun.domain.entity.Image
import com.dm.creativerun.ui.extension.toTransitionGroup

class ImageListAdapter(private val itemWidth: Int) :
    RecyclerView.Adapter<ImageListAdapter.ImageViewHolder>() {

    private val list = mutableListOf<Image>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageViewHolder(
            ListItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val clickListener = View.OnClickListener { view ->
            val action = DetailsFragmentDirections.actionDetailsFragmentToGalleryFragment(
                list.toTypedArray(),
                position
            )
            val extras = FragmentNavigatorExtras(view.toTransitionGroup())
            view.findNavController().navigate(action, extras)
        }
        holder.bind(list[position], itemWidth, clickListener)
    }

    fun submitList(list: List<Image>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class ImageViewHolder(private val binding: ListItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(obj: Image, itemWidth: Int, onClickListener: View.OnClickListener) {
            val layoutParams = FrameLayout.LayoutParams(binding.image.layoutParams)
            val ratio = obj.height.toFloat() / obj.width
            layoutParams.width = itemWidth
            layoutParams.height = (itemWidth * ratio).toInt()
            binding.image.layoutParams = layoutParams
            binding.src = obj.src
            binding.position = adapterPosition
            binding.onClickListener = onClickListener
            binding.executePendingBindings()
        }
    }
}