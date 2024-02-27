package pl.owiczlin.habitify.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pl.owiczlin.habitify.data.models.Category
import pl.owiczlin.habitify.data.models.Difficulty
import pl.owiczlin.habitify.data.models.HabitifyQuest
import pl.owiczlin.habitify.data.models.Priority
import pl.owiczlin.habitify.data.repositories.DataStoreRepository
import pl.owiczlin.habitify.data.repositories.HabitifyRepository
import pl.owiczlin.habitify.util.Action
import pl.owiczlin.habitify.util.Constants.MAX_TITLE_LENGTH
import pl.owiczlin.habitify.util.RequestState
import pl.owiczlin.habitify.util.SearchAppBarState
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: HabitifyRepository,
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    val id: MutableState<Int> = mutableStateOf(0)
    val title: MutableState<String> = mutableStateOf("bleeeh")
    val description: MutableState<String> = mutableStateOf("")
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)
    val category: MutableState<Category> = mutableStateOf(Category.Health)
    val difficulty: MutableState<Difficulty> = mutableStateOf(Difficulty.Hard)
    val isCompleted: MutableState<Boolean> = mutableStateOf(false)
    val dueDate: MutableState<LocalDate> = mutableStateOf(LocalDate.now())  //due Date początkowo jest obecną datą
    val dueTime: MutableState<LocalTime> = mutableStateOf(LocalTime.now())  //due Time początkowo jest północą

    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)

    val searchTextState: MutableState<String> = mutableStateOf("")

    private val _searchedQuests =
        MutableStateFlow<RequestState<List<HabitifyQuest>>>(RequestState.Idle)
    val searchedQuests: StateFlow<RequestState<List<HabitifyQuest>>> = _searchedQuests

    private val _allQuests =
        MutableStateFlow<RequestState<List<HabitifyQuest>>>(RequestState.Idle)
    val allQuests: StateFlow<RequestState<List<HabitifyQuest>>> = _allQuests

    @RequiresApi(Build.VERSION_CODES.O)
    fun searchDatabase(searchQuery: String){
        _searchedQuests.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.searchDatabase(searchQuery = "%$searchQuery%")
                    .collect {searchedQuests ->
                        _searchedQuests.value = RequestState.Success(searchedQuests)
                    }
            }
        } catch (e: Exception){
            _searchedQuests.value = RequestState.Error(e)
        }
        searchAppBarState.value = SearchAppBarState.TRIGGERED
    }


    val lowPriorityQuests: StateFlow<List<HabitifyQuest>> =
        repository.sortByLowPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )

    val highPriorityQuests: StateFlow<List<HabitifyQuest>> =
        repository.sortByHighPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )

    private val _sortState =
        MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState: StateFlow<RequestState<Priority>> = _sortState

    fun readSortState(){
        _sortState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readSortState
                    .map { Priority.valueOf(it)}
                    .collect{
                        _sortState.value = RequestState.Success(it)
                    }
            }
        } catch (e: Exception){
            _sortState.value = RequestState.Error(e)
        }
    }

    fun persistSortState(priority: Priority){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(priority = priority)
        }
    }

    fun getAllQuests(){
        _allQuests.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.getAllQuests.collect{
                    _allQuests.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception){
            _allQuests.value = RequestState.Error(e)
        }
    }

    private val _selectedQuest: MutableStateFlow<HabitifyQuest?> = MutableStateFlow(null)
    val selectedQuest: StateFlow<HabitifyQuest?> = _selectedQuest

    fun getSelectedQuest(questId: Int){
        viewModelScope.launch{
            repository.getSelectedQuest(questId = questId).collect{ quest ->
                _selectedQuest.value = quest
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun addQuest(){
        viewModelScope.launch(Dispatchers.IO) {
            val habitifyQuest = HabitifyQuest(
                title = title.value,
                description = description.value,
                priority = priority.value,
                isCompleted = isCompleted.value,
                category = category.value,
                difficulty = difficulty.value,
                dueDate = dueDate.value,
                dueTime = dueTime.value
            )
            repository.addQuest(habitifyQuest = habitifyQuest)

            Log.d("SharedViewModel", "Quest added to the database: $habitifyQuest")
        }
        searchAppBarState.value = SearchAppBarState.CLOSED
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateQuest(){
        viewModelScope.launch(Dispatchers.IO) {
            val habitifyQuest = HabitifyQuest(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value,
                isCompleted = isCompleted.value,
                category = category.value,
                difficulty = difficulty.value,
                dueDate = dueDate.value,
                dueTime = dueTime.value
            )
            repository.updateQuest(habitifyQuest = habitifyQuest)

            Log.d("SharedViewModel", "Quest updated in the database: $habitifyQuest")
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
fun checkQuest() {
    viewModelScope.launch(Dispatchers.IO) {
        // Tworzymy kopię zadania z aktualizowanym stanem isCompleted
        val updatedQuest = HabitifyQuest(
            id = id.value,
            title = title.value,
            description = description.value,
            priority = priority.value,
            isCompleted = true,
            category = category.value,
            difficulty = difficulty.value,
            dueDate = dueDate.value,
            dueTime = dueTime.value
        )

        // Wywołujemy funkcję repozytorium do aktualizacji zadania
        repository.updateQuest(habitifyQuest = updatedQuest)
    }
}

    @RequiresApi(Build.VERSION_CODES.O)
    private fun deleteQuest(){
        viewModelScope.launch(Dispatchers.IO) {
            val habitifyQuest = HabitifyQuest(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value,
                isCompleted = isCompleted.value,
                category = category.value,
                difficulty = difficulty.value,
                dueDate = dueDate.value,
                dueTime = dueTime.value
            )
            repository.deleteQuest(habitifyQuest = habitifyQuest)
        }
    }

    private fun deleteAllQuests(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllQuests()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADD -> {
                addQuest()
            }
            Action.UPDATE -> {
                updateQuest()
            }
            Action.COMPLETED -> {
                checkQuest()
            }
            Action.DELETE -> {
                deleteQuest()
            }
            Action.DELETE_ALL -> {
                deleteAllQuests()
            }
            Action.UNDO -> {
                addQuest()
            }
            else -> {

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateQuestFields(selectedQuest: HabitifyQuest?) {
        if(selectedQuest != null) {
            id.value = selectedQuest.id
            title.value = selectedQuest.title
            description.value = selectedQuest.description
            priority.value = selectedQuest.priority
            isCompleted.value = selectedQuest.isCompleted
            category.value = selectedQuest.category
            difficulty.value = selectedQuest.difficulty
            dueDate.value = selectedQuest.dueDate
            dueTime.value = selectedQuest.dueTime
        }else {
            id.value = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
            isCompleted.value = false
            category.value = Category.Other
            difficulty.value = Difficulty.Easy
            dueDate.value = LocalDate.now()
            dueTime.value = LocalTime.MIDNIGHT
        }
    }

    fun updateTitle(newTitle: String){
        if(newTitle.length < MAX_TITLE_LENGTH){
            title.value = newTitle
        }
    }

    fun validateFields(): Boolean {
        return title.value.isNotEmpty() && description.value.isNotEmpty()
    }
}