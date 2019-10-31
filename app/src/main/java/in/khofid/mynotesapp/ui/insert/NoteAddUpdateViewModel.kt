package `in`.khofid.mynotesapp.ui.insert

import `in`.khofid.mynotesapp.database.Note
import `in`.khofid.mynotesapp.repository.NoteRepository
import android.app.Application
import androidx.lifecycle.ViewModel

class NoteAddUpdateViewModel(application: Application): ViewModel() {

    var repository = NoteRepository(application)

    fun insert(note: Note) {
        repository.insert(note)
    }

    fun delete(note: Note) {
        repository.delete(note)
    }

    fun update(note: Note) {
        repository.update(note)
    }

}