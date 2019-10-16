package com.dm.creativerun.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.dm.creativerun.R
import com.dm.creativerun.databinding.FragmentHomeBinding
import com.dm.creativerun.ui.common.recycler.OnCategoryClickListener
import com.dm.creativerun.ui.common.recycler.ProjectListAdapter
import com.dm.creativerun.ui.extension.waitForTransition
import com.dm.creativerun.ui.search.SearchFragmentDirections
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), OnCategoryClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: ProjectListAdapter
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ProjectListAdapter()
        adapter.onCategoryClickListener = this

        binding.popularProjectList.adapter = adapter
        waitForTransition(binding.popularProjectList)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.projectListData.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController())
    }

    override fun onCategoryClick(category: String) {
        val action = SearchFragmentDirections.actionGlobalSearchFragment().setCategory(category)
        view?.findNavController()?.navigate(action)
    }
}