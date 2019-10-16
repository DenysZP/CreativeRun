package com.dm.creativerun.ui.category

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.observe
import com.dm.creativerun.R
import com.dm.creativerun.constant.Const
import com.dm.creativerun.databinding.DialogFragmentCategoryBinding
import com.dm.creativerun.domain.entity.Category
import com.dm.creativerun.ui.common.recycler.OnItemClickListener
import com.google.android.flexbox.FlexboxItemDecoration
import com.google.android.flexbox.FlexboxLayoutManager
import org.koin.android.viewmodel.ext.android.viewModel

class CategoryDialogFragment : DialogFragment(), OnItemClickListener<Category> {

    companion object {
        const val TAG = "category_dialog"
        const val CATEGORY_REQUEST_CODE = 300
    }

    private lateinit var binding: DialogFragmentCategoryBinding
    private lateinit var adapter: CategoryListAdapter

    private val viewModel: CategoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = CategoryListAdapter(this)
        binding = DialogFragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = FlexboxLayoutManager(view.context)
        binding.categoriesList.adapter = adapter
        binding.categoriesList.layoutManager = layoutManager
        val decoration = FlexboxItemDecoration(view.context)
        decoration.setDrawable(
            ContextCompat.getDrawable(
                view.context,
                R.drawable.decorator_categories
            )
        )
        binding.categoriesList.addItemDecoration(decoration)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.categoriesData.observe(viewLifecycleOwner) { adapter.submitList(it) }
        viewModel.getCategories()
    }

    override fun onItemClick(obj: Category) {
        val data = Intent().apply { putExtra(Const.Extra.CATEGORY, obj.name) }
        targetFragment?.onActivityResult(CATEGORY_REQUEST_CODE, Activity.RESULT_OK, data)
        dismiss()
    }
}