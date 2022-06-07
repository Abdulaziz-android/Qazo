package uz.mnsh.qazo.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.mnsh.qazo.domain.common.Resource
import uz.mnsh.qazo.domain.model.Prayer
import uz.mnsh.qazo.domain.repository.PrayerRepository

class UpdatePrayers(
    private val repository: PrayerRepository
) {
    operator fun invoke(list: List<Prayer>): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading<String>("loading"))
            list.forEach { prayer ->
                repository.insert(prayer)
            }
            emit(Resource.Success<String>("success"))
        } catch (e: Exception) {
            emit(Resource.Error<String>(e.localizedMessage ?: e.message ?: "хатолик!", "error"))
        }
    }
}