package es.iesnervion.jmmata.blinder.dataaccess.useCase

import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository

/*Caso de uso que llama al metodo del repositorio.
* Elimina una amistad de usuario q esté registrado con sus datos guardados en la babdd*/
class DeleteFriendshipUseCase(
    private val usersRepository: UsersRepository
){
    suspend operator fun invoke(userId: String) = usersRepository.deleteFriendShip(userId)
}