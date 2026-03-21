package ru.sobol.myfirstapp.repository

import androidx.lifecycle.LiveData
import ru.sobol.myfirstapp.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun increaseViews(id: Long)
    fun save(post: Post)        // для создания и обновления
    fun removeById(id: Long)     // для удаления

}
