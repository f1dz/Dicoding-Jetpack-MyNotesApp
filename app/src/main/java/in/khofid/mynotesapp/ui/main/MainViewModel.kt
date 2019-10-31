package `in`.khofid.mynotesapp.ui.main

import `in`.khofid.mynotesapp.database.Note
import `in`.khofid.mynotesapp.repository.NoteRepository
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MainViewModel(val application: Application): ViewModel() {

    val repository = NoteRepository(application)

    fun getAllNotes(): LiveData<List<Note>> {
        return repository.getAllNotes()
    }

}