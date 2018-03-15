package io.bitsound.contentprovidersample.switch.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import io.bitsound.contentprovidersample.models.SwitchModel
import kotlinx.android.synthetic.main.viewholder_switch.view.*


class SwitchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(data: SwitchModel) {
        itemView.switch_key.text = data.key
        itemView.switch_value.isChecked = data.value
    }
}
