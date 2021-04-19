package com.maliatecpharm.activity.mainmenu.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friends")

data class FriendEntity(
    val firstName: String,
    val lastName: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
{
    fun toUserUiModel() = FriendUiModel(
        id = id,
        firstName = firstName,
        lastName = lastName
    )
}