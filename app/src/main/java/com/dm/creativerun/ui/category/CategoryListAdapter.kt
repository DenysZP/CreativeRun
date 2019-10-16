package com.dm.creativerun.ui.category

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dm.creativerun.domain.entity.Category
import com.dm.creativerun.ui.common.recycler.OnItemClickListener
import com.google.android.material.chip.Chip

class CategoryListAdapter(private val onItemClickListener: OnItemClickListener<Category>) :
    RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>() {

    private val list = mutableListOf<Category>()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CategoryViewHolder(
            Chip(parent.context),
            onItemClickListener
        )

    override fun getItemCount() = list.size

    override fun getItemId(position: Int) = list[position].id

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val obj = list[position]
        holder.bind(obj)
    }

    fun submitList(list: List<Category>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class CategoryViewHolder(
        private val chip: Chip,
        private val onItemClickListener: OnItemClickListener<Category>
    ) : RecyclerView.ViewHolder(chip) {

        fun bind(obj: Category) {
            chip.text = obj.name
            chip.setOnClickListener { onItemClickListener.onItemClick(obj) }
        }
    }
}