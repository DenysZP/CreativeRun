package com.dm.creativerun.domain.entity

import java.util.*

data class Comment(val user: User, val text: String, val date: Date)