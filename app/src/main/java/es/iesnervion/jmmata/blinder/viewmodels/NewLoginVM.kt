package es.iesnervion.jmmata.blinder.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.iesnervion.jmmata.blinder.BlinderApplication
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.dataaccess.local.room.datasourceImplementation.UserLocalDataSourceImpl
import es.iesnervion.jmmata.blinder.dataaccess.remote.datasourceImplementation.UserRemoteDataSourceImpl
import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository
import es.iesnervion.jmmata.blinder.dataaccess.useCase.CreateUserAuthUseCase
import es.iesnervion.jmmata.blinder.dataaccess.useCase.InsertUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewLoginVM : ViewModel() {

    //Repositoy
    private val userRepository = UsersRepository(
        UserRemoteDataSourceImpl(),
        UserLocalDataSourceImpl(BlinderApplication.instance)
    )


    //Usecases
    private val createUserAuthUseCase = CreateUserAuthUseCase(userRepository)
    private val insertUserUseCase = InsertUserUseCase(userRepository)

    //public functions
    fun createUserAuth(email: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            createUserAuthUseCase.invoke(email, password)
        }
    }

    fun insertUser(user: UserBO) {
        viewModelScope.launch(Dispatchers.IO) {
            insertUserUseCase.invoke(user)
        }
    }

}