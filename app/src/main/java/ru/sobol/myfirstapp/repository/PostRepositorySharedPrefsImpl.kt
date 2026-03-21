package ru.sobol.myfirstapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.sobol.myfirstapp.dto.Post
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PostRepositorySharedPrefsImpl(
    private val context: Context
) : PostRepository {

    private val gson = Gson()
    private val prefs = context.getSharedPreferences("posts_repo", Context.MODE_PRIVATE)
    private val type = object : TypeToken<List<Post>>() {}.type
    private val key = "posts"

    private var nextId = 1L
    private val currentUserId = 1L
    private val currentUserName = "Я"

    private var posts = emptyList<Post>()
    private val _data = MutableLiveData(posts)

    init {
        loadData()
    }

    override fun getAll(): LiveData<List<Post>> = _data

    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    likedByMe = !post.likedByMe,
                    likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
                )
            } else {
                post
            }
        }
        _data.value = posts
        saveData()
    }

    override fun shareById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(shares = post.shares + 1)
            } else {
                post
            }
        }
        _data.value = posts
        saveData()
    }

    override fun increaseViews(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(views = post.views + 1)
            } else {
                post
            }
        }
        _data.value = posts
        saveData()
    }

    override fun save(post: Post): Post {
        posts = if (post.id == 0L) {
            val newPost = post.copy(
                id = nextId++,
                author = currentUserName,
                authorId = currentUserId,
                published = formatDate(Date()),
                likedByMe = false,
                likes = 0,
                shares = 0,
                views = 0
            )
            listOf(newPost) + posts
        } else {
            posts.map { existingPost ->
                if (existingPost.id == post.id) {
                    existingPost.copy(content = post.content)
                } else {
                    existingPost
                }
            }
        }
        _data.value = posts
        saveData()
        return TODO("Provide the return value")
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        _data.value = posts
        saveData()
    }

    private fun loadData() {
        val json = prefs.getString(key, null)
        if (json != null) {
            try {
                val loadedPosts: List<Post> = gson.fromJson(json, type)
                if (loadedPosts.isNotEmpty()) {
                    posts = loadedPosts
                    nextId = (posts.maxOfOrNull { it.id } ?: 0) + 1
                    _data.value = posts
                    return
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        createInitialData()
        saveData()
    }

    private fun saveData() {
        prefs.edit().putString(key, gson.toJson(posts)).apply()
    }

    private fun createInitialData() {
        // Аналогично файловой реализации
        posts = listOf(
            Post(
                id = nextId++,
                author = "LIBRARY и РИНЦ.",
                authorId = 2,
                content = "Все научные издания разделяются на те, которые включены в eLIBRARY и те, которые включены РИНЦ. Публикации, входящие в РИНЦ - более авторитетная часть публикаций на eLIBRARY. Публикации на eLIBRARY и в РИНЦ учитываются как результат научной работы автора и в показателях его цитируемости. Разница в том, что у авторов существует индекс Хирша по всем публикациям на eLIBRARY.RU, а также индекс Хирша по публикациям в РИНЦ. На данный момент все издания издательства «Проблемы науки» входят в eLIBRARY.",
                published = "22 мая в 11:36",
                likedByMe = false,
                likes = 999,
                shares = 56,
                views = 800,
                video = null
            ),
            Post(
                id = nextId++,
                author = "Android Dev",
                authorId = 3,
                content = "азработчик, вопреки стереотипам, работает в команде. Есть вероятность, что вы будете делать проект один, но в начале карьерного пути я советую избегать этого. Чем больше вокруг вас крутых специалистов, тем быстрее вы растёте как профессионал. Вышел новый релиз Android Studio! Теперь с поддержкой Gemini AI и улучшенным композером.",
                published = "22 мая в 10:15",
                likedByMe = false,
                likes = 342,
                shares = 89,
                views = 200,
                video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
            ),
            Post(
                id = nextId++,
                author = "Kotlin Weekly",
                authorId = 4,
                content = "Подытожим: context‑параметры позволяют вынести общие зависимости (сервисы, менеджеры и тому подобное) из сигнатур функций, сделав код чище и короче. Они автоматически подставляются по типу, а сама механика упрощает внедрение зависимостей и дизайн DSL. Фича пока доступна через EAP, но обещано, что в следующих версиях Kotlin она будет стабилизирована.Kotlin 2.0.0 released! Что нового в языке? Смотрим обновления компилятора и стандартной библиотеки.",
                published = "23 мая в 09:42",
                likedByMe = true,
                likes = 125,
                shares = 420,
                views = 300,
                video = "https://yandex.ru/video/preview/17751654075652601240?from=tabbar&parent-reqid=1774436593109622-5204875762822155445-balancer-l7leveler-kubr-yp-vla-243-BAL&reqid=1774436586076249-419125037190209298-balancer-l7leveler-kubr-yp-vla-37-BAL&suggest_reqid=287332787176492340664888938803367&text=видео+про+животных+знай+тв"
            ),
            Post(
                id = nextId++,
                author = "Google I/O",
                authorId = 5,
                content = "Google I/O 2025, прошедшая 20 мая 2025 года, ожидаемо утонула в анонсах, связанных с искусственным интеллектом. Нового железа не показали, зато Gemini, Imagen 4 с Veo 3 и платформа Android XR заняли центральное место. Двухчасовая презентация раскрыла планы Google по интеграции ИИ в поиск, приложения и устройства. Пробежимся по ключевым моментам.Анонсированы новые возможности для разработчиков: Compose UI, Wear OS 5, Android 15 Beta",
                published = "20 мая в 20:00",
                likedByMe = false,
                likes = 678,
                shares = 134,
                views = 45000,
                video = "https://yandex.ru/video/preview/11501431293735083015?from=tabbar&parent-reqid=1774436593109622-5204875762822155445-balancer-l7leveler-kubr-yp-vla-243-BAL&reqid=1774436586076249-419125037190209298-balancer-l7leveler-kubr-yp-vla-37-BAL&suggest_reqid=287332787176492340664888938803367&text=видео+про+животных+знай+тв"
            ),
            Post(
                id = nextId++,
                author = "Научные статьи",
                authorId = 6,
                content = "Задача РИНЦ состоит не только в том, чтобы собрать в единой базе все научные публикации, но и в том, чтобы создать систему объективной оценки актуальности и полезности этих публикаций. По аналогии с международными наукометрическими базами РИНЦ ввел показатель научной цитируемости авторов. Показатели цитируемости, которые рассчитываются по определенной формуле, свидетельствуют о научной продуктивности исследователя, что также учитывается в рейтингах как конкретных научно-педагогических работников или структурных подразделений, так и самих ВУЗов.",
                published = "19 мая в 14:00",
                likedByMe = false,
                likes = 578,
                shares = 124,
                views = 45000,
                video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
            ),
            Post(
                id = nextId++,
                author = "История биологии",
                authorId = 7,
                content = "Изучение биологии началось в трудах древнегреческих философов Аристотеля и Галена. Эпоха Возрождения, с её распространением книгопечатания, способствовала распространению научных знаний. Великие географические открытия привели к обнаружению множества новых видов растений и животных. Впоследствии Карл Линней и Жорж-Луи Бюффон классифицировали формы живых и ископаемых организмов, заложив основу современной систематики. ",
                published = "20 мая в 20:00",
                likedByMe = false,
                likes = 678,
                shares = 234,
                views = 45000,
                video = "https://yandex.ru/video/preview/8512442904764963623?from=tabbar&parent-reqid=1774436593109622-5204875762822155445-balancer-l7leveler-kubr-yp-vla-243-BAL&reqid=1774436586076249-419125037190209298-balancer-l7leveler-kubr-yp-vla-37-BAL&suggest_reqid=287332787176492340664888938803367&text=видео+про+животных+знай+тв"
            ),
            Post(
                id = nextId++,
                author = "Биологические науки",
                authorId = 8,
                content = "Ботаника — это наука, изучающая растения, является одним из разделов биологии. Изучение растительного мира на молекулярном уровне осуществляется с помощью биохимии и биофизики, которые раскрывают сложные процессы, происходящие внутри растительных клеток[3].\n" +
                        "Зоология — это наука, изучающая разнообразие животного мира, их строение, функционирование, взаимодействие с окружающей средой, закономерности индивидуального развития и эволюционные преобразования[4].",

                published = "13 мая в 20:00",
                likedByMe = false,
                likes = 567,
                shares = 124,
                views = 600,
                video = "https://yandex.ru/video/preview/4904424542841648512?from=tabbar&parent-reqid=1774436593109622-5204875762822155445-balancer-l7leveler-kubr-yp-vla-243-BAL&reqid=1774436586076249-419125037190209298-balancer-l7leveler-kubr-yp-vla-37-BAL&suggest_reqid=287332787176492340664888938803367&text=видео+про+животных+знай+тв"
            ),
            Post(
                id = nextId++,
                author = "Позвоночные животные",
                authorId = 9,
                content = "Позвоночные животные — это крупная группа, которая объединяет всех живых существ, обладающих позвоночником или позвоночным столбом. Этот классический признак позволяет отличить их от беспозвоночных, которые не имеют такой структуры[2].\n" +
                        "\n" +
                        "Позвоночник обеспечивает поддержку тела, защиту нервной системы и позволяет двигаться более эффективно. Позвоночные включают в себя пять основных групп: млекопитающие, птицы, рептилии, амфибии и рыбы.",
                published = "20 мая в 20:00",
                likedByMe = false,
                likes = 67,
                shares = 23,
                views = 45000,
                video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
            ),
            Post(
                id = nextId++,
                author = "История биологии",
                authorId = 10,
                content = "Изучение биологии началось в трудах древнегреческих философов Аристотеля и Галена. Эпоха Возрождения, с её распространением книгопечатания, способствовала распространению научных знаний. Великие географические открытия привели к обнаружению множества новых видов растений и животных. Впоследствии Карл Линней и Жорж-Луи Бюффон классифицировали формы живых и ископаемых организмов, заложив основу современной систематики. ",
                published = "20 мая в 20:00",
                likedByMe = false,
                likes = 58,
                shares = 34,
                views = 45000,
                video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
            ),
            Post(
                id = nextId++,
                author = "ИDesign Review",
                authorId = 11,
                content = "При разработке или рефакторинге большой фичи обычным ревью или созвоном не обойдёшься. Сначала нужно посидеть и нарисовать схематично то, как будет выглядеть фича верхнеуровнево. Если вы думаете, что архитектурой занимаются только сеньоры — это не так. Даже начинающим специалистам приходится думать над тем, как будут выглядеть их фичи в коде. Так, например, выглядела схема в первой итерации на одном из Design Review:" ,
                published = "20 мая в 20:00",
                likedByMe = false,
                likes = 568,
                shares = 12,
                views = 45000,
                video = "https://yandex.ru/video/preview/7212566471978338392?from=tabbar&parent-reqid=1774436593109622-5204875762822155445-balancer-l7leveler-kubr-yp-vla-243-BAL&reqid=1774436586076249-419125037190209298-balancer-l7leveler-kubr-yp-vla-37-BAL&suggest_reqid=287332787176492340664888938803367&text=видео+про+животных+знай+тв"
            )
        )

        _data.value = posts
    }

    private fun formatDate(date: Date): String {
        val format = SimpleDateFormat("d MMM в HH:mm", Locale("ru"))
        return format.format(date)
    }
}
