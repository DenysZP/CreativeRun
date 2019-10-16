package com.dm.creativerun.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.dm.creativerun.R
import com.dm.creativerun.constant.Const
import com.dm.creativerun.databinding.FragmentSearchBinding
import com.dm.creativerun.ui.category.CategoryDialogFragment
import com.dm.creativerun.ui.common.recycler.OnCategoryClickListener
import com.dm.creativerun.ui.common.recycler.ProjectListAdapter
import com.dm.creativerun.ui.extension.waitForTransition
import com.dm.creativerun.ui.view.ColorSeekBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(), OnCategoryClickListener, ColorSeekBar.OnColorSelectListener {

    companion object {
        private const val DELAY_TIME = 700L
        private const val KEYBOARD_COLLAPSE_TIME = 200L
    }

    private lateinit var binding: FragmentSearchBinding
    private lateinit var actionBarIconAnimation: ActionBarIconAnimation
    private lateinit var projectListAdapter: ProjectListAdapter

    private val viewModel: SearchViewModel by viewModel()
    private val args: SearchFragmentArgs by navArgs()
    private var frontLayerBehavior: BottomSheetBehavior<View>? = null
    private var tuneMenuItem: MenuItem? = null
    private var searchMenuItem: MenuItem? = null
    private var searchView: SearchView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackLayer(view)
        setupFrontLayer()
        binding.backLayerLayout.selectedCategory.setOnClickListener { showCategoryDialog() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.elevation = 0f

        viewModel.args = args

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.projectsData.observe(viewLifecycleOwner) { projectListAdapter.submitList(it) }
        viewModel.emptyData.observe(viewLifecycleOwner) { binding.isNoResults = it }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.tune, menu)
        tuneMenuItem = menu.findItem(R.id.tune)
        searchMenuItem = menu.findItem(R.id.search)
        searchView = searchMenuItem?.actionView as? SearchView
        val searchListener = object : DebouncingQueryTextListener(viewLifecycleOwner, DELAY_TIME) {
            override fun onSearchQueryChange(query: String) {
                viewModel.query = query
            }
        }
        searchView?.setOnQueryTextListener(searchListener)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.tune -> {
                changeFrontLayerState()
                true
            }
            android.R.id.home -> {
                if (frontLayerBehavior?.state == BottomSheetBehavior.STATE_COLLAPSED) {
                    changeFrontLayerState()
                } else {
                    activity?.onBackPressed()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CategoryDialogFragment.CATEGORY_REQUEST_CODE) {
            viewModel.categoryData.value = data?.getStringExtra(Const.Extra.CATEGORY)
        }
    }

    override fun onCategoryClick(category: String) {
        viewModel.setCategory(category)
    }

    override fun onColorChange(color: Int) {
        viewModel.colorData.value = color
    }

    override fun onColorSelect(color: Int) {
        viewModel.colorData.value = color
    }

    private fun setupBackLayer(view: View) {
        binding.colorSelectListener = this
        actionBarIconAnimation = ActionBarIconAnimation(view.context)
        actionBarIconAnimation.actionBar = (activity as? AppCompatActivity)?.supportActionBar
    }

    private fun setupFrontLayer() {
        frontLayerBehavior = BottomSheetBehavior.from(binding.frontLayerLayout.frontLayer)
        frontLayerBehavior?.state = BottomSheetBehavior.STATE_EXPANDED

        projectListAdapter = ProjectListAdapter()
        projectListAdapter.onCategoryClickListener = this
        binding.frontLayerLayout.projectList.adapter = projectListAdapter
        binding.frontLayerLayout.projectList.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                binding.listScrollOffset = recyclerView.computeVerticalScrollOffset()
            }
        })

        waitForTransition(binding.frontLayerLayout.projectList)
    }

    private fun changeFrontLayerState() {
        frontLayerBehavior?.also {
            when (it.state) {
                BottomSheetBehavior.STATE_EXPANDED -> {
                    val delay = if (searchMenuItem?.isActionViewExpanded == true) {
                        searchMenuItem?.collapseActionView()
                        KEYBOARD_COLLAPSE_TIME
                    } else {
                        0L
                    }
                    binding.root.postDelayed({
                        it.peekHeight =
                            binding.root.height - binding.backLayerLayout.backLayer.height
                        it.state = BottomSheetBehavior.STATE_COLLAPSED

                        searchMenuItem?.isVisible = false
                        tuneMenuItem?.isVisible = false
                        actionBarIconAnimation.animateToClose()
                        binding.isFrontLayerCollapsed = true
                    }, delay)
                }
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    it.state = BottomSheetBehavior.STATE_EXPANDED
                    searchMenuItem?.isVisible = true
                    tuneMenuItem?.isVisible = true
                    actionBarIconAnimation.animateToArrowBack()
                    viewModel.refresh()
                    binding.isFrontLayerCollapsed = false
                }
            }
        }
    }

    private fun showCategoryDialog() {
        fragmentManager?.let {
            val dialog = CategoryDialogFragment()
            dialog.setTargetFragment(this, CategoryDialogFragment.CATEGORY_REQUEST_CODE)
            dialog.show(it, CategoryDialogFragment.TAG)
        }
    }
}