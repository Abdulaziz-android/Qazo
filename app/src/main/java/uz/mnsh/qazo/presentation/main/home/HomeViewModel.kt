package uz.mnsh.qazo.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uz.mnsh.qazo.domain.common.Resource
import uz.mnsh.qazo.domain.model.User
import uz.mnsh.qazo.domain.use_case.UserUseCases
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userUseCases: UserUseCases
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<User>>(Resource.Loading<User>())
    val state : StateFlow<Resource<User>> get() = _state

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            userUseCases.getUser.invoke().collect {
                _state.value = it
            }
        }
    }

    fun update(user: User){
        userUseCases.addUser.invoke(user)
    }


}