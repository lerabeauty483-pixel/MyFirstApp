package ru.sobol.myfirstapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.sobol.myfirstapp.db.PostContract.Columns

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "myfirstapp.db"
        private const val DATABASE_VERSION = 1

        // SQL для создания таблицы
        private const val SQL_CREATE_POSTS =
            "CREATE TABLE ${PostContract.TABLE_NAME} (" +
                    "${Columns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${Columns.AUTHOR} TEXT NOT NULL," +
                    "${Columns.AUTHOR_ID} INTEGER NOT NULL," +
                    "${Columns.CONTENT} TEXT NOT NULL," +
                    "${Columns.PUBLISHED} TEXT NOT NULL," +
                    "${Columns.LIKED_BY_ME} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.LIKES} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.SHARES} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.VIEWS} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.VIDEO} TEXT" +
                    ")"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Создаем таблицу при первом запуске
        db.execSQL(SQL_CREATE_POSTS)

        // Здесь можно добавить начальные данные
        insertInitialData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // При обновлении версии удаляем старую таблицу и создаем новую
        // В реальном проекте здесь должна быть миграция данных
        db.execSQL("DROP TABLE IF EXISTS ${PostContract.TABLE_NAME}")
        onCreate(db)
    }

    private fun insertInitialData(db: SQLiteDatabase) {
        // Вставляем начальные посты для демонстрации
        val contentValues = android.content.ContentValues().apply {
            put(Columns.AUTHOR, "LIBRARY и РИНЦ")
            put(Columns.AUTHOR_ID, 2)
            put(
                Columns.CONTENT,
                "Все научные издания разделяются на те, которые включены в eLIBRARY и те, которые включены РИНЦ. Публикации, входящие в РИНЦ - более авторитетная часть публикаций на eLIBRARY. Публикации на eLIBRARY и в РИНЦ учитываются как результат научной работы автора и в показателях его цитируемости. Разница в том, что у авторов существует индекс Хирша по всем публикациям на eLIBRARY.RU, а также индекс Хирша по публикациям в РИНЦ. На данный момент все издания издательства «Проблемы науки» входят в eLIBRARY."
            )
            put(Columns.PUBLISHED, "11 мая в 18:36")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 969)
            put(Columns.SHARES, 25)
            put(Columns.VIEWS, 500)
            putNull(Columns.VIDEO)
        }
        db.insert(PostContract.TABLE_NAME, null, contentValues)

        // Второй пост с видео
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Android Dev")
            put(Columns.AUTHOR_ID, 3)
            put(
                Columns.CONTENT,
                "Подытожим: context‑параметры позволяют вынести общие зависимости (сервисы, менеджеры и тому подобное) из сигнатур функций, сделав код чище и короче. Они автоматически подставляются по типу, а сама механика упрощает внедрение зависимостей и дизайн DSL. Фича пока доступна через EAP, но обещано, что в следующих версиях Kotlin она будет стабилизирована.Kotlin 2.0.0 released! Что нового в языке? Смотрим обновления компилятора и стандартной библиотеки."
            )
            put(Columns.PUBLISHED, "22 мая в 11:15")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 342)
            put(Columns.SHARES, 89)
            put(Columns.VIEWS, 2300)
            put(Columns.VIDEO, "https://www.youtube.com/watch?v=WhWc3b3KhnY")
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Google I/O")
            put(Columns.AUTHOR_ID, 3)
            put(
                Columns.CONTENT,
                "Google I/O 2025, прошедшая 20 мая 2025 года, ожидаемо утонула в анонсах, связанных с искусственным интеллектом. Нового железа не показали, зато Gemini, Imagen 4 с Veo 3 и платформа Android XR заняли центральное место. Двухчасовая презентация раскрыла планы Google по интеграции ИИ в поиск, приложения и устройства. Пробежимся по ключевым моментам.Анонсированы новые возможности для разработчиков: Compose UI, Wear OS 5, Android 15 Beta."
            )
            put(Columns.PUBLISHED, "22 мая в 10:15")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 342)
            put(Columns.SHARES, 89)
            put(Columns.VIEWS, 2300)
            put(Columns.VIDEO, "https://www.youtube.com/watch?v=WhWc3b3KhnY")
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Научные статьи")
            put(Columns.AUTHOR_ID, 4)
            put(
                Columns.CONTENT,
                "Задача РИНЦ состоит не только в том, чтобы собрать в единой базе все научные публикации, но и в том, чтобы создать систему объективной оценки актуальности и полезности этих публикаций. По аналогии с международными наукометрическими базами РИНЦ ввел показатель научной цитируемости авторов. Показатели цитируемости, которые рассчитываются по определенной формуле, свидетельствуют о научной продуктивности исследователя, что также учитывается в рейтингах как конкретных научно-педагогических работников или структурных подразделений, так и самих ВУЗов.."
            )
            put(Columns.PUBLISHED, "22 мая в 10:15")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 342)
            put(Columns.SHARES, 89)
            put(Columns.VIEWS, 2300)
            put(Columns.VIDEO, "https://www.youtube.com/watch?v=WhWc3b3KhnY")
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Биологические науки")
            put(Columns.AUTHOR_ID, 5)
            put(
                Columns.CONTENT,
                "Изучение биологии началось в трудах древнегреческих философов Аристотеля и Галена. Эпоха Возрождения, с её распространением книгопечатания, способствовала распространению научных знаний. Великие географические открытия привели к обнаружению множества новых видов растений и животных. Впоследствии Карл Линней и Жорж-Луи Бюффон классифицировали формы живых и ископаемых организмов, заложив основу современной систематики."
            )
            put(Columns.PUBLISHED, "22 мая в 11:15")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 342)
            put(Columns.SHARES, 89)
            put(Columns.VIEWS, 2300)
            put(Columns.VIDEO, "https://www.youtube.com/watch?v=WhWc3b3KhnY")
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "Позвоночные животные")
            put(Columns.AUTHOR_ID, 6)
            put(
                Columns.CONTENT,
                " Ботаника — это наука, изучающая растения, является одним из разделов биологии. Изучение растительного мира на молекулярном уровне осуществляется с помощью биохимии и биофизики, которые раскрывают сложные процессы, происходящие внутри растительных клеток"
            )
            put(Columns.PUBLISHED, "22 мая в 13:15")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 342)
            put(Columns.SHARES, 89)
            put(Columns.VIEWS, 2300)
            put(Columns.VIDEO, "https://www.youtube.com/watch?v=WhWc3b3KhnY")
            db.insert(PostContract.TABLE_NAME, null, this)
        }
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "ИDesign Review")
            put(Columns.AUTHOR_ID, 7)
            put(
                Columns.CONTENT,
                " При разработке или рефакторинге большой фичи обычным ревью или созвоном не обойдёшься. Сначала нужно посидеть и нарисовать схематично то, как будет выглядеть фича верхнеуровнево."
            )
            put(Columns.PUBLISHED, "22 мая в 10:12")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 342)
            put(Columns.SHARES, 89)
            put(Columns.VIEWS, 2300)
            put(Columns.VIDEO, "https://www.youtube.com/watch?v=WhWc3b3KhnY")
            db.insert(PostContract.TABLE_NAME, null, this)
        }


            android.content.ContentValues().apply {
                put(Columns.AUTHOR, "Позвоночные животные")
                put(Columns.AUTHOR_ID, 8)
                put(
                    Columns.CONTENT,
                    "Зоология — это наука, изучающая разнообразие животного мира, их строение, функционирование, взаимодействие с окружающей средой, закономерности индивидуального развития и эволюционные преобразования[4]"
                )
                put(Columns.PUBLISHED, "20 мая в 11:15")
                put(Columns.LIKED_BY_ME, 0)
                put(Columns.LIKES, 342)
                put(Columns.SHARES, 89)
                put(Columns.VIEWS, 2300)
                put(Columns.VIDEO, "https://www.youtube.com/watch?v=WhWc3b3KhnY")
                db.insert(PostContract.TABLE_NAME, null, this)
            }
        }
    }
