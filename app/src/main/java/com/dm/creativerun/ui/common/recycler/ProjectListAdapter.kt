package com.dm.creativerun.ui.common.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dm.creativerun.BR
import com.dm.creativerun.databinding.ListItemProjectBinding
import com.dm.creativerun.domain.entity.Project
import com.dm.creativerun.ui.details.DetailsFragmentDirections
import com.dm.creativerun.ui.extension.toTransitionGroup

class ProjectListAdapter :
    PagedListAdapter<Project, ProjectListAdapter.ProjectViewHolder>(ProjectListDiffCallback()) {

    var onCategoryClickListener: OnCategoryClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, position: Int) =
        ProjectViewHolder(
            ListItemProjectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onCategoryClickListener
        )

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class ProjectViewHolder(
        private val binding: ListItemProjectBinding,
        private val onCategoryClickListener: OnCategoryClickListener?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(obj: Project) {
            binding.setVariable(BR.obj, obj)
            binding.setVariable(BR.categoryClickListener, onCategoryClickListener)
            binding.root.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    binding.container.toTransitionGroup(),
                    binding.thumbnailContainer.toTransitionGroup(),
                    binding.thumbnailImage.toTransitionGroup(),
                    binding.projectName.toTransitionGroup(),
                    binding.categoryGroup.toTransitionGroup(),
                    binding.statsView.toTransitionGroup()
                )
                it.findNavController()
                    .navigate(DetailsFragmentDirections.actionGlobalDetailsFragment(obj), extras)
            }
            binding.executePendingBindings()
        }
    }

    private class ProjectListDiffCallback : DiffUtil.ItemCallback<Project>() {

        override fun areItemsTheSame(oldItem: Project, newItem: Project) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Project, newItem: Project) = oldItem == newItem
    }
}