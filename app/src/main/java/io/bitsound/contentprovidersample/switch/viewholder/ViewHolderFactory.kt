package io.bitsound.contentprovidersample.switch.viewholder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.bitsound.contentprovidersample.App
import io.bitsound.contentprovidersample.R


object ViewHolderFactory {

    object ViewType {
        const val NONE = 0x00
        const val HEADER = 0x10
        const val SWITCH = 0x20
        const val FOOTER = 0x30
    }

    fun create(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(App.context())
        return when (viewType) {
            ViewType.HEADER -> HeaderViewHolder(inflater.inflate(R.layout.viewholder_header, parent, false))
            ViewType.SWITCH -> SwitchViewHolder(inflater.inflate(R.layout.viewholder_switch, parent, false))
            ViewType.FOOTER -> FooterViewHolder(inflater.inflate(R.layout.viewholder_footer, parent, false))
            else -> throw RuntimeException("Cannot find any ViewHolder with ViewType#$viewType. Check if you put correct ViewType.")
        }
    }
}
