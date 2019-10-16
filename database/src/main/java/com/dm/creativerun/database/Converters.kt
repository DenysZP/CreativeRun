package com.dm.creativerun.database

import androidx.room.TypeConverter
import java.util.*

internal object Converters {

    private const val LIST_SEPARATOR = ", "

    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long) = Date(value)

    @TypeConverter
    @JvmStatic
    fun toTimestamp(date: Date) = date.time

    @TypeConverter
    @JvmStatic
    fun stringListToString(value: List<String>) = value.joinToString(LIST_SEPARATOR) { it }

    @TypeConverter
    @JvmStatic
    fun stringToStringList(value: String) = value.split(LIST_SEPARATOR)

    @TypeConverter
    @JvmStatic
    fun intListToString(value: List<Int>) = value.joinToString(LIST_SEPARATOR) { it.toString() }

    @TypeConverter
    @JvmStatic
    fun stringToIntList(value: String) = value.split(LIST_SEPARATOR).mapNotNull { it.toIntOrNull() }
}