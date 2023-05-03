package com.example.roomtodo.Utils.Extensions

import com.example.roomtodo.networks.JsonAdapter.DateJsonAdapter
import com.squareup.moshi.Moshi
import timber.log.Timber
import java.net.URLEncoder
import java.util.*

object MoshiUtils {
    fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(Date::class.java, DateJsonAdapter())
            .build()
    }
}

inline fun <reified T> String?.getObjFromJson(): T? {
    if (this == null) return null

    Timber.e("getObjFromJson: $this")

    val jsonAdapter = MoshiUtils.getMoshi().adapter(T::class.java).lenient()

    return jsonAdapter.fromJson(this)
}

inline fun <reified T> T?.getJsonFromObj(): String? {
    if (this == null) return null

    Timber.e("getJsonFromObj: $this")

    val jsonAdapter = MoshiUtils.getMoshi().adapter(T::class.java).lenient()

    return jsonAdapter.toJson(this).urlEncode()
}

fun String.urlEncode(): String = URLEncoder.encode(this, "utf-8")