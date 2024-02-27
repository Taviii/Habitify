package pl.owiczlin.habitify.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import pl.owiczlin.habitify.data.models.HabitifyQuest

@Dao
interface HabitifyDao {

    @Query("SELECT * FROM habitify_table ORDER BY id ASC")
    fun getAllQuests(): Flow<List<HabitifyQuest>>

    @Query("SELECT * FROM habitify_table WHERE isCompleted = true")
    fun getCompletedQuests(): Flow<List<HabitifyQuest>>

    @Query("SELECT * FROM habitify_table WHERE isCompleted = false")
    fun getToDoQuests(): Flow<List<HabitifyQuest>>

    @Query("SELECT * FROM habitify_table WHERE id=:questId")
    fun getSelectedQuest(questId: Int): Flow<HabitifyQuest>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addQuest(habitifyQuest: HabitifyQuest)

    @Update
    suspend fun updateQuest(habitifyQuest: HabitifyQuest)

    @Delete
    suspend fun deleteQuest(habitifyQuest: HabitifyQuest)

    @Query("DELETE FROM habitify_table")
    suspend fun deleteAllQuests()

    @Query("SELECT * FROM habitify_table WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<HabitifyQuest>>

    @Query("SELECT * FROM habitify_table ORDER BY CASE WHEN priority LIKE 'LOW' THEN 1 WHEN priority LIKE 'MEDIUM' THEN 2 WHEN priority LIKE 'HIGH' THEN 3 END")
    fun sortByLowPriority(): Flow<List<HabitifyQuest>>

    @Query("SELECT * FROM habitify_table ORDER BY CASE WHEN priority LIKE 'HIGH' THEN 1 WHEN priority LIKE 'MEDIUM' THEN 2 WHEN priority LIKE 'LOW' THEN 3 END")
    fun sortByHighPriority(): Flow<List<HabitifyQuest>>
}