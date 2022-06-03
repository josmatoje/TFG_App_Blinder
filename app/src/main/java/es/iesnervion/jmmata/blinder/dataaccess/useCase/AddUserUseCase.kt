package es.iesnervion.jmmata.blinder.dataaccess.useCase

import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository

class AddUserUseCase (
    private val usersRepository: UsersRepository
){
    suspend operator fun invoke(email: String, password: String, user: UserBO) = usersRepository.insertUser(email, password, user)
}