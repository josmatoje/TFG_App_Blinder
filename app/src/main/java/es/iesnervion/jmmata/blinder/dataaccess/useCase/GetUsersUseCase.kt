package es.iesnervion.jmmata.blinder.dataaccess.useCase

import com.google.firebase.firestore.QuerySnapshot
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository

class GetUsersUseCase (private val usersRepository: UsersRepository){
    suspend operator fun invoke(afterAction:(QuerySnapshot)->Unit) = usersRepository.getUsers(afterAction)
}