package com.dm.creativerun.ui.gallery

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.core.app.SharedElementCallback
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import androidx.viewpager2.widget.ViewPager2
import com.dm.creativerun.R
import com.dm.creativerun.databinding.FragmentGalleryBinding
import com.dm.creativerun.manager.ImageDownloadManager
import kotlinx.android.synthetic.main.fragment_gallery.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import kotlin.collections.set

class GalleryFragment : Fragment() {

    private lateinit var binding: FragmentGalleryBinding
    private lateinit var adapter: GalleryAdapter

    private val imageDownloadManager: ImageDownloadManager by inject()
    private val args: GalleryFragmentArgs by navArgs()
    private val sharedViewModel: GallerySharedViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.image_shared_element_transition)
        prepareTransitions()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = GalleryAdapter(object : GalleryAdapter.OnImageReadyListener {
            override fun onImageReady(position: Int) {
                if (position == args.position) {
                    startPostponedEnterTransition()
                }
            }
        })
        adapter.submitList(args.images.toList())
        adapter.targetPosition = args.position
        binding.galleryViewPager.adapter = adapter
        postponeEnterTransition()
        binding.galleryViewPager.doOnPreDraw {
            binding.galleryViewPager.setCurrentItem(args.position, false)
        }
        binding.galleryViewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    sharedViewModel.galleryPositionData.value = position
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.download, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.download) {
            imageDownloadManager.downloadImage(args.images[galleryViewPager.currentItem].src)
            return true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun prepareTransitions() {
        setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(
                names: MutableList<String>,
                sharedElements: MutableMap<String, View>
            ) {
                val position = sharedViewModel.galleryPositionData.value ?: -1
                binding.galleryViewPager.findViewWithTag<ViewGroup>(position)
                    ?.findViewById<ImageView>(R.id.image)?.let { sharedElements[names[0]] = it }
            }
        })
    }
}