package com.homework.mynotes

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.homework.mynotes.adapters.NoteRecyclerAdapter

class SwipeToDeleteCallback(private val adapter: NoteRecyclerAdapter) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val iconDel = ContextCompat.getDrawable(
        adapter.context,
        R.mipmap.ic_delete_item_round
    )


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        viewHolder.itemView.resources
        adapter.deleteItem(viewHolder.adapterPosition)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView: View = viewHolder.itemView

        val iconMargin = (itemView.height - iconDel!!.intrinsicHeight) / 2
        val iconTop = itemView.top + (itemView.height - iconDel.intrinsicHeight) / 2
        val iconBottom = iconTop + iconDel.intrinsicHeight

        if (dX < 0) {
            val iconLeft = itemView.right - iconMargin - iconDel.intrinsicWidth
            val iconRight = itemView.right - iconMargin
            iconDel.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            iconDel.draw(c)

        }
    }
}