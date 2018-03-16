package io.bitsound.contentprovidersample.switch.viewholder

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import io.bitsound.contentprovidersample.models.SwitchModel
import kotlinx.android.synthetic.main.viewholder_switch.view.*


class SwitchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    @SuppressLint("SetTextI18n")
    fun bind(data: SwitchModel, listener: (View) -> Unit) {
        itemView.switch_key.text = "#${data.id ?: "N/A"} : ${data.key}"
        itemView.switch_value.isChecked = data.value
        itemView.btn_delete_switch.setOnClickListener(listener)
    }
}
