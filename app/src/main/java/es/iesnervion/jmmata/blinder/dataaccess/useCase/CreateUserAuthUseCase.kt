package es.iesnervion.jmmata.blinder.dataaccess.useCase

import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository

class CreateUserAuthUseCase (
    private val usersRepository: UsersRepository
){
    suspend operator fun invoke(email: String, password: String) = usersRepository.createtUserAuth(email, password)
}