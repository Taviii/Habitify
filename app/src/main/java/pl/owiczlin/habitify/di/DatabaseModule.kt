package pl.owiczlin.habitify.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.owiczlin.habitify.data.HabitifyDatabase
import pl.owiczlin.habitify.util.Constants.DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context,
        HabitifyDatabase::class.java,
        DATABASE_NAME
    )

//        .fallbackToDestructiveMigration()
        .addMigrations(MIGRATION_5_6)
        .build()

    @Singleton
    @Provides
    fun provideDao(database: HabitifyDatabase) = database.habitifyDao()

    private val MIGRATION_5_6: Migration = object : Migration(5, 6) {
        override fun migrate(db: SupportSQLiteDatabase) {

            // Tworzenie nowej tabeli habitify_table_new
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS habitify_table_new (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "title TEXT NOT NULL," +
                        "description TEXT NOT NULL," +
                        "priority TEXT NOT NULL," +
                        "isCompleted INTEGER NOT NULL," +
                        "category TEXT NOT NULL," +
                        "difficulty TEXT NOT NULL," +
                        "dueDate TEXT NOT NULL," +
                        "dueTime TEXT NOT NULL)"
            )

            // Usunięcie starej tabeli habitify_table
            db.execSQL("DROP TABLE IF EXISTS habitify_table")

            // Zmiana nazwy nowej tabeli na habitify_table
            db.execSQL("ALTER TABLE habitify_table_new RENAME TO habitify_table")

        }
    }
}
