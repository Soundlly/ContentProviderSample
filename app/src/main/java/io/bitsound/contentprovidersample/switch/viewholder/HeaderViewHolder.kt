package io.bitsound.contentprovidersample.switch.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import io.bitsound.contentprovidersample.App


class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(resid: Int) {
        itemView.setBackgroundColor(App.color(resid))
    }
}
