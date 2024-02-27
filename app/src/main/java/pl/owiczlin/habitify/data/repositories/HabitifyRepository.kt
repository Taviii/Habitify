package pl.owiczlin.habitify.data.repositories

import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import pl.owiczlin.habitify.data.HabitifyDao
import pl.owiczlin.habitify.data.models.HabitifyQuest
import javax.inject.Inject

@ViewModelScoped
class HabitifyRepository @Inject constructor(private val habitifyDao: HabitifyDao) {

    val getAllQuests: Flow<List<HabitifyQuest>> = habitifyDao.getAllQuests()
    val sortByLowPriority: Flow<List<HabitifyQuest>> = habitifyDao.sortByLowPriority()
    val sortByHighPriority: Flow<List<HabitifyQuest>> = habitifyDao.sortByHighPriority()

    fun getSelectedQuest(questId: Int): Flow<HabitifyQuest> {
        return habitifyDao.getSelectedQuest(questId = questId)
    }

    suspend fun addQuest(habitifyQuest: HabitifyQuest) {
        habitifyDao.addQuest(habitifyQuest = habitifyQuest)
    }

    suspend fun updateQuest(habitifyQuest: HabitifyQuest) {
        habitifyDao.updateQuest(habitifyQuest = habitifyQuest)
    }

    suspend fun deleteQuest(habitifyQuest: HabitifyQuest) {
        habitifyDao.deleteQuest(habitifyQuest = habitifyQuest)
    }

    suspend fun deleteAllQuests() {
        habitifyDao.deleteAllQuests()
    }

    fun searchDatabase(searchQuery: String): Flow<List<HabitifyQuest>> {
        return habitifyDao.searchDatabase(searchQuery = searchQuery)
    }
}