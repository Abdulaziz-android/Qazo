package uz.mnsh.qazo.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.mnsh.qazo.domain.common.Resource
import uz.mnsh.qazo.domain.model.Prayer
import uz.mnsh.qazo.domain.repository.PrayerRepository

class GetPrayers(
    private val repository: PrayerRepository
) {
    operator fun invoke(): Flow<Resource<List<Prayer>>> = flow {
        try {
            emit(Resource.Loading<List<Prayer>>())
            val prayers = repository.getAllPrayers()
            if (prayers != null) {
                emit(Resource.Success<List<Prayer>>(prayers))
            } else {
                emit(Resource.Error<List<Prayer>>("Ma'lumotlar mavjud emas!"))

            }
        } catch (e: Exception) {
            emit(Resource.Error<List<Prayer>>(e.localizedMessage ?: "Error!"))
        }
    }
}