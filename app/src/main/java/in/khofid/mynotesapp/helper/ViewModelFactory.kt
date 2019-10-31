package `in`.khofid.mynotesapp.helper

import `in`.khofid.mynotesapp.ui.insert.NoteAddUpdateViewModel
import `in`.khofid.mynotesapp.ui.main.MainViewModel
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(val application: Application): ViewModelProvider.NewInstanceFactory() {

    companion object {
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory {
            if(INSTANCE ==  null) {
                synchronized(ViewModelFactory::class.java) {
                    if(INSTANCE == null) {
                        INSTANCE = ViewModelFactory(application)
                    }
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(application) as (T)
            modelClass.isAssignableFrom(NoteAddUpdateViewModel::class.java) -> NoteAddUpdateViewModel(application) as (T)
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName())
        }
    }

}