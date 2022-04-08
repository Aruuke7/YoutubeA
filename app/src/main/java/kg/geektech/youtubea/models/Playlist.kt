package kg.geektech.youtubea.models

data class Playlist(
    private var part: String? = null,
    var etag:String,
    var items:ArrayList<Items>
)
data class Items(
    var kind: String,
    var etag: String,
    var id: String,
    var snippet: Snippet,
    var contentDetails: ContentDetails
)

data class Snippet(
    var publishedAt: String,
    var channelId: String,
    var title: String,
    var description: String,
    var thumbnails: Thumbnails,
    var channelTitle: String,
    var tags: List<String>,
    var categoryId: String,
    var liveBroadcastContent: String,
)

data class Thumbnails(
    var default: Default,
    var medium: Medium,
    var high: High
)
data class High(
    var url: String,
    var width: Int,
    var height: Int
)

data class Default(
    var url: String,
    var width: Int,
    var height: Int
)
data class Medium(
    var url: String,
    var width: Int,
    var height: Int
)

data class ContentDetails(
    var duration: String,
    var dimension: String,
    var definition: String,
    var caption: String,
    var licensedContent: Boolean,
    var contentRating: ContentRating,
    var projection: String,
    var itemCount: Int,
)
data class ContentRating(val name: String = "")