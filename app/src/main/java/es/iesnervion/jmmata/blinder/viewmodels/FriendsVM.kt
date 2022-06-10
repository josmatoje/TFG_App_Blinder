package es.iesnervion.jmmata.blinder.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import es.iesnervion.jmmata.blinder.BlinderApplication
import es.iesnervion.jmmata.blinder.businessObject.FriendBO
import es.iesnervion.jmmata.blinder.dataaccess.local.room.datasourceImplementation.UserLocalDataSourceImpl
import es.iesnervion.jmmata.blinder.dataaccess.remote.datasourceImplementation.UserRemoteDataSourceImpl
import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository
import es.iesnervion.jmmata.blinder.dataaccess.useCase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FriendsVM : ViewModel() {

    //Variables
    private var auth: FirebaseAuth = Firebase.auth

    //Repositoy
    private val userRepository = UsersRepository(
        UserRemoteDataSourceImpl(),
        UserLocalDataSourceImpl(BlinderApplication.instance)
    )

    //Usercase
    private val getUserUseCase = GetUsersUseCase(userRepository)
    private val getFriendFromUseCase = GetFriendFromUseCase(userRepository)
    private val getFriendshipUseCase = GetFriendship(userRepository)
    private val getFriendsFromActualUserUseCase = GetFriendsFromActualUserUseCase(userRepository)

    //Live data
    private val _friendsList = MutableLiveData<List<FriendBO>>()
    val friendsList: LiveData<List<FriendBO>> get() = _friendsList

    //Public functions
    /*Carga el listado de amigos del usuario (amigo es que ambos usuarios se hayan dado like)*/
    fun loadFriends(){
        viewModelScope.launch(Dispatchers.IO) {
            getFriendsFromActualUserUseCase() { usermatches ->
                val auxFriend: MutableList<FriendBO> = ArrayList()
                viewModelScope.launch(Dispatchers.IO) {
                    usermatches.filter { getFriendshipUseCase(it.id)}
                        .map { getFriendFromUseCase(it.id){ friend ->
                                                        auxFriend.add(friend)
                                                    }
                                _friendsList.postValue(auxFriend)
                            }
                }
            }
        }
        //
    }

}