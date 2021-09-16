package tw.ysky.codenotes.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostItem (
    @Json(name = "userId")
    val userId: Int = 0,
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "title")
    val title: String?,
    @Json(name = "body")
    val body: String?,
) : Parcelable