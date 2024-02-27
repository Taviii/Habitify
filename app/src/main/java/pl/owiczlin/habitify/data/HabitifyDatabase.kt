package pl.owiczlin.habitify.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.owiczlin.habitify.data.models.HabitifyQuest
import pl.owiczlin.habitify.util.Converters

@Database(
    entities = [HabitifyQuest::class],
    version = 6,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class HabitifyDatabase : RoomDatabase() {

    abstract fun habitifyDao(): HabitifyDao

}