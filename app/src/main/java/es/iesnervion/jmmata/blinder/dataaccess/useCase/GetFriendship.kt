package es.iesnervion.jmmata.blinder.dataaccess.useCase

import es.iesnervion.jmmata.blinder.businessObject.UserMatchBO
import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository

class GetFriendship (private val usersRepository: UsersRepository){
    suspend operator fun invoke(friendId: String): Boolean = usersRepository.isFriendship(friendId)
}