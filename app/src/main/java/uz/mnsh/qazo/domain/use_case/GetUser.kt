package uz.mnsh.qazo.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.mnsh.qazo.domain.common.Resource
import uz.mnsh.qazo.domain.model.User
import uz.mnsh.qazo.domain.repository.PrayerRepository

class GetUser(
    private val repository: PrayerRepository
) {
    operator fun invoke(): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading<User>())
            val user = repository.getUser()
            emit(Resource.Success<User>(user))
        } catch (e: Exception) {
            emit(Resource.Error<User>(e.localizedMessage?:"Error!"))
        }
    }
}