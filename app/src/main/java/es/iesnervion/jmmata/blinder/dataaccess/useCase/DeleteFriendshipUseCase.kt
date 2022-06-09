package es.iesnervion.jmmata.blinder.dataaccess.useCase

import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository

class DeleteFriendshipUseCase(
    private val usersRepository: UsersRepository
){
    suspend operator fun invoke(userId: String) = usersRepository.deleteFriendShip(userId)
}