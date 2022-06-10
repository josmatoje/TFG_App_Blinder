package es.iesnervion.jmmata.blinder.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import es.iesnervion.jmmata.blinder.BlinderApplication
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.businessObject.UserMatchBO
import es.iesnervion.jmmata.blinder.dataaccess.local.room.datasourceImplementation.UserLocalDataSourceImpl
import es.iesnervion.jmmata.blinder.dataaccess.remote.datasourceImplementation.UserRemoteDataSourceImpl
import es.iesnervion.jmmata.blinder.dataaccess.remote.firebase.dto.UserDTO
import es.iesnervion.jmmata.blinder.dataaccess.remote.toUserBO
import es.iesnervion.jmmata.blinder.dataaccess.repository.UsersRepository
import es.iesnervion.jmmata.blinder.dataaccess.useCase.GetFriendsFromActualUserUseCase
import es.iesnervion.jmmata.blinder.dataaccess.useCase.GetMatchesUseCase
import es.iesnervion.jmmata.blinder.dataaccess.useCase.GetUsersUseCase
import es.iesnervion.jmmata.blinder.dataaccess.useCase.InsertMatchUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PeopleVM : ViewModel() {

    //Variables
    private var position: Int = 0
    private var users: List<UserBO> = emptyList()
    private var auth: FirebaseAuth = Firebase.auth

    //Repositoy
    private val userRepository = UsersRepository(
        UserRemoteDataSourceImpl(),
        UserLocalDataSourceImpl(BlinderApplication.instance)
    )

    //Usercase
    private val getUsersUseCase = GetUsersUseCase(userRepository)
    private val getFriendsFromActualUserUseCase = GetFriendsFromActualUserUseCase(userRepository)
    private val insertMatchUseCase = InsertMatchUseCase(userRepository)
    private val getMatchesUseCase = GetMatchesUseCase(userRepository)

    //Live data
    private val _userPresented = MutableLiveData<UserBO>()
    val userPresented: LiveData<UserBO> get() = _userPresented

    //Public functions
    fun loadUser() {
        if (position >= users.size) {
            users = emptyList()
        }
        if (users.isEmpty()){ //Si position es 0 pero el listado es empty (No hay mas usuarios)
            getUsers()
        } else {
            _userPresented.postValue(users[position])
        }
    }

    fun isAMatch(match: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            userPresented.value?.let { it ->
                insertMatchUseCase.invoke(UserMatchBO(it.id, it.name, match))
                position++
            }
            loadUser()
        }
    }
    //TODO: delete
/*            do {
                position++
            } while (position<users.size &&
                (getFriendsFromActualUserUseCase.invoke().map { it.id }
                    .contains(users[position].id) ||
                getFriendsFromActualUserUseCase.invoke().map { it.id }
                    .contains(Firebase.auth.uid ?: ""))
            ) //TODO: try to optimize at least in on invoke*/

    //Private functions
    /*Recibe los usuarios con los que no se ha interactuado aun matches y filtra según condiciones*/
    private fun getUsers(){
        val userList: ArrayList<UserBO> = ArrayList()
        var userDocument: UserBO
        var userMatches: List<UserMatchBO>
        viewModelScope.launch(Dispatchers.IO) {
            userMatches = getMatchesUseCase.invoke(auth.uid ?: "")
            getUsersUseCase.invoke{ document ->
                for (user in document){
                    if (!(userMatches.map { it.id }.contains(user.id) && userMatches.map { it.id }.contains(auth.uid))){
                        userDocument = user.toObject<UserDTO>().toUserBO()
                        userDocument.id = user.id
                        userList.add(userDocument)
                    }
                }
                users = userList
                position = 0
                if (userList.isNotEmpty()) {
                    _userPresented.postValue(userList[position])
                }else {
                    _userPresented.postValue(UserBO())
                }
            }
        }
    }

}