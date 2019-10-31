package `in`.khofid.mynotesapp.ui.main

import `in`.khofid.mynotesapp.R
import `in`.khofid.mynotesapp.database.Note
import `in`.khofid.mynotesapp.helper.ViewModelFactory
import `in`.khofid.mynotesapp.ui.insert.*
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var viewModel = obtainViewModel(this)
        viewModel.getAllNotes().observe(this, noteObserver)

        adapter = NoteAdapter(this)

        rv_notes.layoutManager = LinearLayoutManager(this)
        rv_notes.setHasFixedSize(true)
        rv_notes.adapter = adapter

        fab_add.setOnClickListener {
            if(it.id == R.id.fab_add) {
                val intent = Intent(this, NoteAddUpdateActivity::class.java)
                startActivityForResult(intent, REQUEST_ADD)
            }
        }
    }

    var noteObserver: Observer<List<Note>> = object : Observer<List<Note>>{
        override fun onChanged(list: List<Note>?) {
            if(list != null) {
                adapter.setListNotes(list)
            }
        }
    }

    fun showSnackbarMessage(msg: String) {
        Snackbar.make(rv_notes, msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null) {
            if(requestCode == REQUEST_ADD) {
                if(resultCode == RESULT_ADD)
                    showSnackbarMessage(getString(R.string.added))
            } else if (requestCode == REQUEST_UPDATE){
                if(resultCode == RESULT_UPDATE)
                    showSnackbarMessage(getString(R.string.changed))
                else if (resultCode == RESULT_DELETE)
                    showSnackbarMessage(getString(R.string.deleted))
            }
        }
    }

    companion object {
        fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)
            return ViewModelProviders.of(activity, factory).get(MainViewModel::class.java)
        }
    }
}
