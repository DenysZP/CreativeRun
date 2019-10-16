package com.dm.creativerun.ui.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GallerySharedViewModel : ViewModel() {

    val galleryPositionData = MutableLiveData<Int>()
}