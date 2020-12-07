package com.example.app_kotlin.data.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.app_kotlin.data.model.Note
import com.example.app_kotlin.data.model.User
import com.example.app_kotlin.errors.NoAuthException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

private const val NOTES_COLLECTION = "Notes"
private const val USERS_COLLECTION = "Users"
private const val TAG = "FireStoreDatabase"

class FireStoreDatabaseProvider : DatabaseProvider {
    private val db = FirebaseFirestore.getInstance()
    private val result = MutableLiveData<List<Note>>()
    private val currentUser: FirebaseUser?
        get() = FirebaseAuth.getInstance().currentUser

    private var subscribedOnDb = false

    override fun observeNotes(): LiveData<List<Note>> {
        if (!subscribedOnDb) subscribeForDbChanging()
        return result
    }

    override fun getCurrentUser() = currentUser?.run { User(displayName, email) }

    override fun addOrReplaceNote(newNote: Note): LiveData<Result<Note>> {
        val result = MutableLiveData<Result<Note>>()

        handleNotesReference(
            {
                getUserNotesCollection()
                    .document(newNote.id.toString())
                    .set(newNote)
                    .addOnSuccessListener {
                        Log.e(TAG, "Note $newNote is saved")
                        result.value = Result.success(newNote)
                    }
                    .addOnFailureListener {
                        Log.e(TAG, "Error saving note $newNote, message: ${it.message}")
                        result.value = Result.failure(it)
                    }
            }, {
                Log.e(TAG, "Error getting reverence note $newNote, message: ${it.message}")
                result.value = Result.failure(it)
            })
        return result
    }

    override fun deleteNote(noteId: String): LiveData<Result<Unit>> =
        MutableLiveData<Result<Unit>>().apply {
            handleNotesReference(
                {
                    getUserNotesCollection()
                        .document(noteId)
                        .delete()
                        .addOnSuccessListener {
                            value = Result.success(Unit)
                        }.addOnFailureListener {
                            value = Result.failure(it)
                        }
                }, {
                    Log.e(TAG, "Error")
                    value = Result.failure(it)
                })
        }


    private fun subscribeForDbChanging() {
        handleNotesReference(
            {
                getUserNotesCollection().addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Log.e(TAG, "observe note exception: $error")
                    } else if (snapshot != null) {
                        val notes = mutableListOf<Note>()

                        for (doc: QueryDocumentSnapshot in snapshot) {
                            notes.add(doc.toObject(Note::class.java))
                        }
                        result.value = notes
                    }
                }
                subscribedOnDb = true
            }, {
                Log.e(TAG, "Error getting reverence while subscribed for notes")
            }
        )
    }

    private fun getUserNotesCollection() = currentUser?.let {
        db.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
    } ?: throw NoAuthException()

    private inline fun handleNotesReference(
        referenceHandler: (CollectionReference) -> Unit,
        exceptionHandler: (Throwable) -> Unit = {}
    ) {
        kotlin.runCatching {
            getUserNotesCollection()
        }
            .fold(referenceHandler, exceptionHandler)
    }
}