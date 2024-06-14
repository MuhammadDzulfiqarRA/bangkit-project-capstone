package com.dicoding.abai.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class StoriesResponse(
	val code: Int? = null,
	val data: List<DataItem?>? = null,
	val message: String? = null,
	val status: String? = null
)


@Parcelize
data class DataItem(
	val overview: String? = null,
	val thumbnail: String? = null,
	val updatedAt: String? = null,
	val author: String? = null,
	val origin: String? = null,
	val genre: String? = null,
	val createdAt: String? = null,
	val id: Int? = null,
	val title: String? = null
) : Parcelable

