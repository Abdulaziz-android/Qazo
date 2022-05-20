package uz.mnsh.qazo.data.repository

import uz.mnsh.qazo.data.local.dao.UserDao
import uz.mnsh.qazo.domain.model.User
import uz.mnsh.qazo.domain.repository.UserRepository

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {

    override  fun getUser(): User? {
        return userDao.getUser()
    }

    override fun insertUser(user: User) {
        userDao.insert(user)
    }
}