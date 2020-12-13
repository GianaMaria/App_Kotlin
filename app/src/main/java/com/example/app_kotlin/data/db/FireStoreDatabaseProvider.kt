package com.example.app_kotlin.data.db

import android.util.Log
import com.example.app_kotlin.data.model.Note
import com.example.app_kotlin.data.model.User
import com.example.app_kotlin.errors.NoAuthException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlin.coroutines.suspendCoroutine

private const val NOTES_COLLECTION = "Notes"
private const val USERS_COLLECTION = "Users"
private const val TAG = "FireStoreDatabase"

class FireStoreDatabaseProvider(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : DatabaseProvider {
    private val result = MutableStateFlow<List<Note>?>(null)
    private val currentUser: FirebaseUser?
        get() = auth.currentUser

    private var subscribedOnDb = false

    override fun observeNotes(): Flow<List<Note>> {
        if (!subscribedOnDb) subscribeForDbChanging()
        return result.filterNotNull()
    }

    override fun getCurrentUser() = currentUser?.run { User(displayName, email) }

    override suspend fun addOrReplaceNote(newNote: Note) {
        suspendCoroutine<Note> { continuation ->
            handleNotesReference(
                {
                    getUserNotesCollection()
                        .document(newNote.id.toString())
                        .set(newNote)
                        .addOnSuccessListener {
                            Log.e(TAG, "Note $newNote is saved")
                            continuation.resumeWith(Result.success(newNote))
                        }
                        .addOnFailureListener {
                            Log.e(TAG, "Error saving note $newNote, message: ${it.message}")
                            continuation.resumeWith(Result.failure(it))
                        }
                }, {
                    Log.e(TAG, "Error getting reverence note $newNote, message: ${it.message}")
                    continuation.resumeWith(Result.failure(it))
                })
        }
    }

    override suspend fun deleteNote(noteId: String) {
        suspendCoroutine<Unit> { continuation ->
            getUserNotesCollection()
                .document(noteId)
                .delete()
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(Unit))
                }
                .addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }
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