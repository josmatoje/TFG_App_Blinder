package es.iesnervion.jmmata.blinder.dataaccess.useCase

import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository

class InsertUserUseCase (
    private val usersRepository: UsersRepository
){
    suspend operator fun invoke(user: UserBO) = usersRepository.insertUser(user)
}