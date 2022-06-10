package es.iesnervion.jmmata.blinder.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.iesnervion.jmmata.blinder.BlinderApplication
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.businessObject.UserMatchBO
import es.iesnervion.jmmata.blinder.dataaccess.local.room.datasourceImplementation.UserLocalDataSourceImpl
import es.iesnervion.jmmata.blinder.dataaccess.remote.datasourceImplementation.UserRemoteDataSourceImpl
import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository
import es.iesnervion.jmmata.blinder.dataaccess.useCase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFriendsVM : ViewModel() {

    //Variables
    private var auth: FirebaseAuth = Firebase.auth

    //Repositoy
    private val userRepository = UsersRepository(
        UserRemoteDataSourceImpl(),
        UserLocalDataSourceImpl(BlinderApplication.instance)
    )

    //Usercase
    private val getUserByIdUseCase = GetUserByIdUseCase(userRepository)
    private val getFriendsFromActualUserUseCase = GetFriendsFromActualUserUseCase(userRepository) //TODO: Cambiar por UserMatchBO

    //Public functions
    fun loadFriendProfile() {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    //Private functions


}