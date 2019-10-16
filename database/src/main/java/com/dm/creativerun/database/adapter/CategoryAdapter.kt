package com.dm.creativerun.database.adapter

import com.dm.creativerun.database.entity.CategoryEntity
import com.dm.creativerun.domain.entity.Category

object CategoryAdapter {

    fun toStorage(adaptee: Category) =
        CategoryEntity(adaptee.id, adaptee.name, adaptee.isPopular)

    fun fromStorage(adaptee: CategoryEntity) = Category(adaptee.id, adaptee.name, adaptee.isPopular)
}