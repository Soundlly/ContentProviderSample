package io.bitsound.contentprovidersample.switch.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.viewholder_footer.view.*


class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(listener: (View) -> Unit) {
        itemView.btn_insert_switch.setOnClickListener(listener)
    }
}
