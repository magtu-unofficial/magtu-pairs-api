package ru.magtu.pairs.access.repositories

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import ru.magtu.pairs.access.responses.UserItem
import java.util.*

@Document("users")
data class UserDocument(
    @Id
    val id: String? = null,

    @Field
    val platform: String? = null,

    @Field
    val data: UserData? = null
) {
    fun toUserItem() = UserItem(
        platform = this.platform,
        data = this.data
    )
}


data class UserData(
    val currentCommand: Int? = null,

    val args: List<Any> = Collections.emptyList(),

    val currentArg: Int? = null,

    val lastQuery: UserDataLastQuery? = null
)

data class UserDataLastQuery(
    val group: String? = null,

    val subgroup: String? = null
)
