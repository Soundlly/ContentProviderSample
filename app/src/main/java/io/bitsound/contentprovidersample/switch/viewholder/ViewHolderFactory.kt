package io.bitsound.contentprovidersample.switch.viewholder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.bitsound.contentprovidersample.App
import io.bitsound.contentprovidersample.R


object ViewHolderFactory {

    object ViewType {
        const val HEADER = 0x00
        const val SWITCH = 0x10
    }

    fun create(parent: ViewGroup, viewType: Int, layoutResId: Int? = null): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(App.context())
        return when (viewType) {
            ViewType.HEADER -> HeaderViewHolder(inflater.inflate(layoutResId ?: R.layout.viewholder_header, parent, false))
            ViewType.SWITCH -> SwitchViewHolder(inflater.inflate(layoutResId ?: R.layout.viewholder_switch, parent, false))
            else -> throw RuntimeException("Cannot find any ViewHolder with ViewType#$viewType. Check if you put correct ViewType.")
        }
    }
}
