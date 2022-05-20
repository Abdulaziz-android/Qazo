package uz.mnsh.qazo.domain.repository

import uz.mnsh.qazo.domain.model.User

interface UserRepository {

    fun getUser():User?

    fun insertUser(user: User)


}