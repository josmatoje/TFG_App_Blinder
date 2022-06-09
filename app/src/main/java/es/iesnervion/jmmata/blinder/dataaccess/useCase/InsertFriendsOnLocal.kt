package es.iesnervion.jmmata.blinder.dataaccess.useCase

import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository

class InsertFriendsOnLocal (
    private val usersRepository: UsersRepository
){
    //suspend operator fun invoke(usersList: List<UserBO>) = usersRepository.insertFriendsOnLocal(usersList)
}