package `in`.khofid.mynotesapp.helper

import `in`.khofid.mynotesapp.database.Note
import androidx.recyclerview.widget.DiffUtil

class NoteDiffCallback(
    val oldNoteList: List<Note>,
    val newNoteList: List<Note>
): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList.get(oldItemPosition).id == newNoteList.get(newItemPosition).id
    }

    override fun getOldListSize() = oldNoteList.size

    override fun getNewListSize() = newNoteList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList.get(oldItemPosition).title.equals(newNoteList.get(newItemPosition).title)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}