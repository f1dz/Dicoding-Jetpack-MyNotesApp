package `in`.khofid.mynotesapp.ui.insert

import `in`.khofid.mynotesapp.R
import `in`.khofid.mynotesapp.database.Note
import `in`.khofid.mynotesapp.helper.DateHelper
import `in`.khofid.mynotesapp.helper.ViewModelFactory
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_note_add_update.*

const val EXTRA_NOTE = "extra_note"
const val EXTRA_POSITION = "extra_position"
const val REQUEST_ADD = 100
const val RESULT_ADD = 101
const val REQUEST_UPDATE = 200
const val RESULT_UPDATE = 201
const val RESULT_DELETE = 301

class NoteAddUpdateActivity : AppCompatActivity() {

    private var isEdit = false
    private var ALERT_DIALOG_CLOSE = 10
    private var ALERT_DIALOG_DELETE = 20

    private var note: Note? = null
    private var position = 0

    private lateinit var actionBarTitle: String
    private lateinit var btnTitle: String

    private lateinit var viewModel: NoteAddUpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_add_update)

        viewModel = obtainViewModel(this)

        note = intent.getParcelableExtra(EXTRA_NOTE)
        if (note != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        }

        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)

            if (note != null) {
                edt_title.setText(note?.title)
                edt_description.setText(note?.description)
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        if (supportActionBar != null) {
            supportActionBar?.title = actionBarTitle
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        btn_submit.text = btnTitle

        btn_submit.setOnClickListener {
            val title = edt_title.text.toString().trim()
            val description = edt_description.text.toString().trim()

            if (title.isEmpty()) {
                edt_title.setError(getString(R.string.empty))
            } else if (description.isEmpty()) {
                edt_description.setError(getString(R.string.empty))
            } else {
                if(note == null)
                    note = Note(id = null,title = title, description = description, date = null)
                else {
                    note?.title = title
                    note?.description = description
                }

                val intent = Intent()
                intent.putExtra(EXTRA_NOTE, note)
                intent.putExtra(EXTRA_POSITION, position)

                if (isEdit) {
                    viewModel.update(note!!)
                    setResult(RESULT_UPDATE, intent)
                    finish()
                } else {
                    note?.date = DateHelper.getCurrentDate()
                    viewModel.insert(note!!)
                    setResult(RESULT_ADD, intent)
                    finish()
                }
            }
        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): NoteAddUpdateViewModel {
        var factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProviders.of(activity, factory).get(NoteAddUpdateViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isEdit) menuInflater.inflate(R.menu.menu_form, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        var dialogTitle: String
        var dialogMessage: String

        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogTitle = getString(R.string.message_delete)
            dialogMessage = getString(R.string.delete)
        }

        var alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle(dialogTitle)
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes), { dialogInterface, i ->
                if (isDialogClose) finish()
                else {
                    viewModel.delete(note!!)

                    val intent = Intent()
                    intent.putExtra(EXTRA_POSITION, position)
                    setResult(RESULT_DELETE, intent)
                    finish()
                }
            })
            .setNegativeButton(getString(R.string.no), { dialogInterface, i ->
                dialogInterface.cancel()
            })

        val alertDialog = alertBuilder.create()
        alertDialog.show()
    }


}
