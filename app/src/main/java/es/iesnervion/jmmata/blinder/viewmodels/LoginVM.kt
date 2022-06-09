package es.iesnervion.jmmata.blinder.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.iesnervion.jmmata.blinder.BlinderApplication
import es.iesnervion.jmmata.blinder.dataaccess.local.room.datasourceImplementation.UserLocalDataSourceImpl
import es.iesnervion.jmmata.blinder.dataaccess.remote.datasourceImplementation.UserRemoteDataSourceImpl
import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository
import es.iesnervion.jmmata.blinder.dataaccess.useCase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginVM : ViewModel() {

    //Repositoy
    private val userRepository = UsersRepository(
        UserRemoteDataSourceImpl(),
        UserLocalDataSourceImpl(BlinderApplication.instance)
    )

    //Usecases
    private val getFriendsFromActualUserUseCase = GetFriendsFromActualUserUseCase(userRepository)
    private val insertFriendsOnLocal = InsertFriendsOnLocal(userRepository)
    private val deleteLocalDatasUseCase = DeleteLocalDatasUseCase(userRepository)

    //Public functions
    /*fun loadLocalDatasFrom() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteLocalDatasUseCase.invoke()
            insertFriendsOnLocal.invoke(getFriendsFromActualUserUseCase.invoke())
        }
    }*/
}