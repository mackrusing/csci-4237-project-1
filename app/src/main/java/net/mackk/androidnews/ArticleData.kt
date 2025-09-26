package net.mackk.androidnews

data class ArticleData(
    val sourceId: String,
    val sourceName: String,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val imageUrl: String,
    val publishDate: String,
    val content: String,
)

val sampleArticleData = ArticleData(
    sourceId = "techcrunch",
    sourceName = "TechCrunch",
    author = "Maxwell Zeff",
    title = "OpenAI launches ChatGPT Pulse to proactively write you morning briefs | TechCrunch",
    description = "Subscribers to ChatGPT's \$200-a-month Pro plan will get to try out Pulse, OpenAI's latest agent product.",
    url = "https://techcrunch.com/2025/09/25/openai-launches-chatgpt-pulse-to-proactively-write-you-morning-briefs/",
    imageUrl = "https://techcrunch.com/wp-content/uploads/2025/09/ChatGPT_Pulse.png?resize=1200,675",
    publishDate = "2025-09-25T17:02:31Z",
    content = "OpenAI is launching a new feature inside of ChatGPT called Pulse, which generates personalized reports for users while they sleep. Pulse offers users five to ten briefs that can get them up to speed … [+4500 chars]",
)
