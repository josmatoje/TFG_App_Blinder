package es.iesnervion.jmmata.blinder.dataaccess.useCase

import com.google.firebase.firestore.QuerySnapshot
import es.iesnervion.jmmata.blinder.businessObject.FriendBO
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.businessObject.UserMatchBO
import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository

class GetFriendsFromActualUserUseCase (private val usersRepository: UsersRepository){
    suspend operator fun invoke(afterAction:(QuerySnapshot)->Unit) = usersRepository.getFriendsFromActualUser(afterAction)
}