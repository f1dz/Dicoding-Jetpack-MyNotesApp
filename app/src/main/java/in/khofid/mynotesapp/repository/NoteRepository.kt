package `in`.khofid.mynotesapp.repository

import `in`.khofid.mynotesapp.database.Note
import `in`.khofid.mynotesapp.database.NoteDatabase
import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.Executors

class NoteRepository(application: Application) {

    private var executorService = Executors.newSingleThreadExecutor()
    var db = NoteDatabase.getDatabase(application)
    private var noteDao = db.noteDao()

    fun getAllNotes(): LiveData<List<Note>> {
        return noteDao.getAllNotes()
    }

    fun insert(note: Note) {
        executorService.execute { noteDao.insert(note) }
    }

    fun delete(note: Note) {
        executorService.execute { noteDao.delete(note) }
    }

    fun update(note: Note) {
        executorService.execute { noteDao.update(note) }
    }
}