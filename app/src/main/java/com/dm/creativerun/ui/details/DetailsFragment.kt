package com.dm.creativerun.ui.details

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.TransitionInflater
import com.dm.creativerun.R
import com.dm.creativerun.databinding.FragmentDetailsBinding
import com.dm.creativerun.ui.common.recycler.OnCategoryClickListener
import com.dm.creativerun.ui.common.recycler.StaggeredGridItemDecoration
import com.dm.creativerun.ui.extension.waitForTransition
import com.dm.creativerun.ui.gallery.GallerySharedViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.collections.set

class DetailsFragment : Fragment(), OnCategoryClickListener, View.OnClickListener {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var adapter: ImageListAdapter

    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModel()
    private val sharedViewModel: GallerySharedViewModel by sharedViewModel()
    private var shareIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.categoryClickListener = this
        binding.colorClickListener = this

        val divider = resources.getDimensionPixelSize(R.dimen.item_divider)
        val spanCount =
            (binding.imageList.layoutManager as? StaggeredGridLayoutManager)?.spanCount ?: 1
        val width = Resources.getSystem().displayMetrics.widthPixels / spanCount - divider

        adapter = ImageListAdapter(width)
        binding.imageList.adapter = adapter
        binding.imageList.addItemDecoration(StaggeredGridItemDecoration(divider))
        prepareTransitions()
        waitForTransition(binding.imageList)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.args = args
        viewModel.projectDetailsData.observe(viewLifecycleOwner) {
            adapter.submitList(it.images)
            setShareIntent(it.url)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.share, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.share -> {
                shareIntent?.let {
                    startActivity(Intent.createChooser(it, getString(R.string.share_to)))
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCategoryClick(category: String) {
        val action = DetailsFragmentDirections.actionGlobalSearchFragment().setCategory(category)
        view?.findNavController()?.navigate(action)
    }

    override fun onClick(view: View) {
        viewModel.projectDetailsData.value?.color?.let {
            val action = DetailsFragmentDirections.actionGlobalSearchFragment().setColor(it)
            view.findNavController().navigate(action)
        }
    }

    private fun setShareIntent(projectUrl: String) {
        shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, projectUrl)
            type = "text/plain"
        }
    }

    private fun prepareTransitions() {
        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(
                names: MutableList<String>,
                sharedElements: MutableMap<String, View>
            ) {
                val position = sharedViewModel.galleryPositionData.value ?: -1
                val selectedViewHolder =
                    binding.imageList.findViewHolderForAdapterPosition(position)
                selectedViewHolder?.itemView?.findViewById<ImageView>(R.id.image)?.let {
                    sharedElements[names[0]] = it
                }
            }
        })
    }
}