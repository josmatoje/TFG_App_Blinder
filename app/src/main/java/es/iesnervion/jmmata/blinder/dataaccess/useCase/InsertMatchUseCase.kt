package es.iesnervion.jmmata.blinder.dataaccess.useCase

import es.iesnervion.jmmata.blinder.businessObject.UserMatchBO
import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository

class InsertMatchUseCase (
    private val usersRepository: UsersRepository
){
    suspend operator fun invoke(userMatch: UserMatchBO) = usersRepository.inserteMatch(userMatch)
}