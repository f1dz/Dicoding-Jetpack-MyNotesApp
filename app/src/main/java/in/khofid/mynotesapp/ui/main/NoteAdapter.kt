package `in`.khofid.mynotesapp.ui.main

import `in`.khofid.mynotesapp.R
import `in`.khofid.mynotesapp.database.Note
import `in`.khofid.mynotesapp.helper.NoteDiffCallback
import `in`.khofid.mynotesapp.ui.insert.EXTRA_NOTE
import `in`.khofid.mynotesapp.ui.insert.EXTRA_POSITION
import `in`.khofid.mynotesapp.ui.insert.NoteAddUpdateActivity
import `in`.khofid.mynotesapp.ui.insert.REQUEST_UPDATE
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter(val activity: Activity): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var listNotes: ArrayList<Note> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount() = listNotes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindView(listNotes.get(position))
        holder.itemView.cv_item_note.setOnClickListener {
            val intent = Intent(activity, NoteAddUpdateActivity::class.java)
            intent.putExtra(EXTRA_POSITION, holder.adapterPosition)
            intent.putExtra(EXTRA_NOTE, listNotes.get(holder.adapterPosition))
            activity.startActivityForResult(intent, REQUEST_UPDATE)
        }
    }

    fun setListNotes(notes: List<Note>) {
        val noteDiffCallback = NoteDiffCallback(this.listNotes, listNotes)
        val diffResult = DiffUtil.calculateDiff(noteDiffCallback)

        listNotes.clear()
        listNotes.addAll(notes)
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    class NoteViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bindView(note: Note){
            itemView.tv_item_title.text = note.title
            itemView.tv_item_description.text = note.description
            itemView.tv_item_date.text = note.date
        }

    }
}