package uz.mnsh.qazo.domain.use_case

import uz.mnsh.qazo.domain.model.User
import uz.mnsh.qazo.domain.repository.UserRepository

class AddUser(
    private val repository: UserRepository
) {
    operator fun invoke(user: User ){
        repository.insertUser(user)
    }
}