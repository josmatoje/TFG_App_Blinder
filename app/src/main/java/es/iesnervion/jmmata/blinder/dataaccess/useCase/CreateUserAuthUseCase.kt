package es.iesnervion.jmmata.blinder.dataaccess.useCase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository

/*Caso de uso que llama al metodo del repositorio.
* Crea una autentificación de usuario con sus datos guardados en la babdd*/
class CreateUserAuthUseCase (
    private val usersRepository: UsersRepository
){
    suspend operator fun invoke(email: String, password: String, afterAction:(Task<AuthResult>)->Unit)  = usersRepository.createtUserAuth(email, password, afterAction)
}