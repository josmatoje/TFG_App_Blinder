package es.iesnervion.jmmata.blinder.dataaccess.remote.datasourceImplementation

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import es.iesnervion.jmmata.blinder.businessObject.UserBO
import es.iesnervion.jmmata.blinder.dataaccess.datatsource.UserRemoteDataSource
import es.iesnervion.jmmata.blinder.dataaccess.remote.firebase.dto.UserDTO
import es.iesnervion.jmmata.blinder.dataaccess.remote.toUserDTO

class UserRemoteDataSourceImplementation: UserRemoteDataSource {

    private var db = Firebase.firestore
    private var auth: FirebaseAuth = Firebase.auth

    override suspend fun getRemoteUsers(): List<UserBO> {
        val userList: MutableList<UserBO> = listOf<UserBO>() as MutableList<UserBO>
        db.collection("Users")
            .get()
            .addOnSuccessListener { document ->
                for (user in document){
                    userList.add(user.toObject<UserBO>())
                }
            }
            .addOnFailureListener {
                Log.d("ERROR", "ERROR")
            }
        return userList
    }

    override suspend fun insertRemoteUser(email: String, password: String, user: UserBO) {
        auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                val user = auth.currentUser
            }
        }
        auth.uid?.let {
            db.collection("Users")
                .document(it)
                .set(
                    user.toUserDTO()
                )
        }
    }

    override suspend fun updateRemoteUser(user: UserBO) {
        TODO("Not yet implemented")
    }

    override suspend fun getRemoteUsersFilterBy(likes: List<String>?) {
        TODO("Not yet implemented")
    }
}