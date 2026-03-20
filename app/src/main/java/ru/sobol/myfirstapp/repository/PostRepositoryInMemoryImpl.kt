package ru.sobol.myfirstapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.sobol.myfirstapp.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {

    // Исходные данные
    private var post = Post(
        id = 1,
        author = "Мир животных. Удивительное путешевствие;",
        content = "Животные — удивительные существа, которые нас окружают. Они бывают разными — от милых домашних питомцев до диких зверей. Каждый вид играет важную роль в экосистеме, и забота о них помогает сохранить баланс природы. Изучая животных, мы лучше понимаем окружающий мир и учимся уважать других существ.",
        published = "11 мая в 11:11",
        likedByMe = false,
        likes = 999,
        shares = 25,
        views = 5700
    )

    // MutableLiveData, который можно изменять
    private val _data = MutableLiveData(post)

    // Внешний доступ только для чтения (LiveData, а не MutableLiveData)
    override fun get(): LiveData<Post> = _data

    override fun like() {
        // Меняем состояние лайка на противоположное
        post = post.copy(
            likedByMe = !post.likedByMe,
            likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
        )
        // Оповещаем подписчиков об изменении
        _data.value = post
    }

    override fun share() {
        post = post.copy(
            shares = post.shares + 1
        )
        _data.value = post
    }

    override fun increaseViews() {
        // Можно будет реализовать позже
        post = post.copy(
            views = post.views + 1
        )
        _data.value = post
    }
}


