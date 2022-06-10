package es.iesnervion.jmmata.blinder.dataaccess.useCase

import com.google.firebase.firestore.QuerySnapshot
import es.iesnervion.jmmata.blinder.businessObject.FriendBO
import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository

class GetFriendFromUseCase  (private val usersRepository: UsersRepository){
    suspend operator fun invoke(id: String, afterAction:(FriendBO)->Unit) = usersRepository.getFriendFrom(id, afterAction)
}