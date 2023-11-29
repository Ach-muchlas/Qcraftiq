package com.am.finalproject.data.lokal.room

//@Database(entities = [PopularCourseEntity::class], version = 1, exportSchema = false)
//abstract class DatabaseDb : RoomDatabase() {
//    abstract fun PopularCourseDao(): PopularCourseDao
//
//    companion object {
//        @Volatile
//        private var instance: DatabaseDb? = null
//        fun getInstance(context: Context): DatabaseDb =
//            instance ?: synchronized(this) {
//                instance ?: Room.databaseBuilder(
//                    context.applicationContext,
//                    DatabaseDb::class.java, "News.db"
//                ).build()
//            }
//    }
//}