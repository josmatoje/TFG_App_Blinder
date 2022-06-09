package es.iesnervion.jmmata.blinder.dataaccess.useCase

import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository

class UpdateUserDescription (
    private val usersRepository: UsersRepository
){
    suspend operator fun invoke(user: UserBO) = usersRepository.updateUserDescription(user)
}