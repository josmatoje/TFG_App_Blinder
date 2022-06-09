package es.iesnervion.jmmata.blinder.dataaccess.useCase

import es.iesnervion.jmmata.blinder.businessObject.UserMatchBO
import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository

class GetMatchesUseCase (private val usersRepository: UsersRepository){
    suspend operator fun invoke(id: String): List<UserMatchBO> = usersRepository.getMatchessFrom(id)
}