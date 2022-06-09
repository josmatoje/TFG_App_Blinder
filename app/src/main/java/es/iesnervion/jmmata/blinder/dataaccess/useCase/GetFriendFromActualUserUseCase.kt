package es.iesnervion.jmmata.blinder.dataaccess.useCase

import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository

class GetFriendFromActualUserUseCase (private val usersRepository: UsersRepository){
    suspend operator fun invoke(userId: String): UserBO = usersRepository.getFriendFromActualUser(userId)
}