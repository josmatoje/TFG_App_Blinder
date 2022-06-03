package es.iesnervion.jmmata.blinder.dataaccess.converters

import androidx.room.TypeConverter

class RoomListConverter {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        val stringValues = value?.substring(1,value.length-1) ?: ""
        return stringValues.split(", ")
    }

    @TypeConverter
    fun fromArrayList(list: List<String>?): String {
        var stringList = ""
        if (list != null) {
            stringList = "["
            for (word in list){
                stringList += "$word, "
            }
            stringList = "]"
        }
        return stringList
    }
}