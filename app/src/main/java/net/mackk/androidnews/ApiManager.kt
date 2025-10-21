package net.mackk.androidnews

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject

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

data class SourceData(
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String,
)

class ApiManager {

    val okHttpClient: OkHttpClient

    init {
        // create builder
        val builder = OkHttpClient.Builder()

        // create logger
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)

        // set client
        okHttpClient = builder.build()
    }

    fun parseArticleData(json: JSONObject): ArticleData {
        // sub objects
        val sourceObj = json.getJSONObject("source")

        // values from sub objs
        val sourceId = sourceObj.getString("id")
        val sourceName = sourceObj.getString("name")

        // top level values
        val author = json.getString("author")
        val title = json.getString("title")
        val description = json.getString("description")
        val url = json.getString("url")
        val imageUrl = json.getString("urlToImage")
        val publishDate = json.getString("publishedAt")
        val content = json.getString("content")

        val article = ArticleData(
            sourceId = sourceId,
            sourceName = sourceName,
            author = author,
            title = title,
            description = description,
            url = url,
            imageUrl = imageUrl,
            publishDate = publishDate,
            content = content,
        )

        return article
    }

    fun parseSourceData(json: JSONObject): SourceData {
        // extract data from object
        val id = json.getString("id")
        val name = json.getString("name")
        val description = json.getString("description")
        val url = json.getString("url")
        val category = json.getString("category")
        val language = json.getString("language")
        val country = json.getString("country")

        // construct class
        val source = SourceData(
            id = id,
            name = name,
            description = description,
            url = url,
            category = category,
            language = language,
            country = country
        )

        return source
    }

    fun getArticles(
        apikey: String,
        search: String,
        page: Int,
    ): List<ArticleData> {
        // create request
        val request = Request.Builder()
            .url("https://newsapi.org/v2/everything?apiKey=$apikey&page=$page&language=en&q=$search")
            .get()
            .build()

        // make req
        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody = response.body?.string()

        // parse req
        if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
            val json = JSONObject(responseBody)
            val jsonArticles = json.getJSONArray("articles")
            val articles = mutableListOf<ArticleData>()
            for (i in 0 until jsonArticles.length()) {
                // get current obj
                val curr = jsonArticles.getJSONObject(i)
                articles.add(parseArticleData(curr))
            }
            return articles
        }

        return listOf()
    }

    fun getArticlesWithSource(
        apikey: String,
        search: String,
        source: String,
        page: Int,
    ): List<ArticleData> {
        // create request
        val request = Request.Builder()
            .url("https://newsapi.org/v2/everything?apiKey=$apikey&page=$page&language=en&q=$search&sources=${source}")
            .get()
            .build()

        // make req
        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody = response.body?.string()

        // parse req
        if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
            val json = JSONObject(responseBody)
            val jsonArticles = json.getJSONArray("articles")
            val articles = mutableListOf<ArticleData>()
            for (i in 0 until jsonArticles.length()) {
                // get current obj
                val curr = jsonArticles.getJSONObject(i)
                articles.add(parseArticleData(curr))
            }
            return articles
        }

        return listOf()
    }

    fun getLocalArticles(
        apikey: String,
        locale: String,
        page: Int,
    ): List<ArticleData> {
        // create request
        val request = Request.Builder()
            .url("https://newsapi.org/v2/everything?apiKey=$apikey&page=$page&language=en&searchIn=title&q=$locale")
            .get()
            .build()

        // make req
        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody = response.body?.string()

        // parse req
        if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
            val json = JSONObject(responseBody)
            val jsonArticles = json.getJSONArray("articles")
            val articles = mutableListOf<ArticleData>()
            for (i in 0 until jsonArticles.length()) {
                // get current obj
                val curr = jsonArticles.getJSONObject(i)
                articles.add(parseArticleData(curr))
            }
            return articles
        }

        return listOf()
    }

    fun getTopHeadlines(
        apikey: String,
        page: Int,
    ): List<ArticleData> {
        // create request
        val request = Request.Builder()
            .url("https://newsapi.org/v2/top-headlines?apiKey=$apikey&page=$page&language=en")
            .get()
            .build()

        // make req
        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody = response.body?.string()

        // parse req
        if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
            val json = JSONObject(responseBody)
            val jsonArticles = json.getJSONArray("articles")
            val articles = mutableListOf<ArticleData>()
            for (i in 0 until jsonArticles.length()) {
                // get current obj
                val curr = jsonArticles.getJSONObject(i)
                articles.add(parseArticleData(curr))
            }
            return articles
        }

        return listOf()
    }

    fun getSources(
        apikey: String,
        category: String,
    ): List<SourceData> {
        // create request
        val request = Request.Builder()
            .url("https://newsapi.org/v2/top-headlines/sources?apiKey=$apikey&language=en&country=us&category=$category")
            .get()
            .build()

        // make req
        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody = response.body?.string()

        // parse req
        if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
            val json = JSONObject(responseBody)
            val jsonSources = json.getJSONArray("sources")
            val sources = mutableListOf<SourceData>()
            for (i in 0 until jsonSources.length()) {
                // get current obj
                val curr = jsonSources.getJSONObject(i)
                sources.add(parseSourceData(curr))
            }
            return sources
        }

        return listOf()
    }
}
