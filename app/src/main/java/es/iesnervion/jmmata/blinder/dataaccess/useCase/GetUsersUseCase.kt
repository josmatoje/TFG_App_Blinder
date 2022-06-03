package es.iesnervion.jmmata.blinder.dataaccess.useCase

import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository

class GetUsersUseCase (private val usersRepository: UsersRepository){
    suspend operator fun invoke(): List<UserBO> = usersRepository.getUsers()
}