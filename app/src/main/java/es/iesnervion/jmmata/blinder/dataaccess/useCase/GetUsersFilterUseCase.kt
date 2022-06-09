package es.iesnervion.jmmata.blinder.dataaccess.useCase

import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository

class GetUsersFilterUseCase (
    private val usersRepository: UsersRepository
){
    suspend operator fun invoke(gender: String?,
                                sexuality: String?,
                                proximity: Int?,
                                older: Int?,
                                younger: Int?,
                                likes: List<String>?): List<UserBO>? = usersRepository.getUsersFilterBy(gender, sexuality, proximity, older, younger, likes)
}