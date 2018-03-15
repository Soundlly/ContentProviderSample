package io.bitsound.contentprovidersample.switch

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import io.bitsound.contentprovidersample.models.SwitchModel
import io.bitsound.contentprovidersample.switch.viewholder.HeaderViewHolder
import io.bitsound.contentprovidersample.switch.viewholder.SwitchViewHolder
import io.bitsound.contentprovidersample.switch.viewholder.ViewHolderFactory


class SwitchAdapter(private val mTextLogDataList: MutableList<SwitchModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val MAX_TEXT_LOGS = 500
    }

    private var mIndexHeader: Int = 0  // Always at 0-index position
    private var mIndexContent: Int = mIndexHeader + 1 // Comes right after Header
    private var mIndexFooter: Int = mTextLogDataList.size + mIndexContent  // May come after Content

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderFactory.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position <= mIndexHeader) (holder as HeaderViewHolder).bind(android.R.color.white)
        else (holder as SwitchViewHolder).bind(mTextLogDataList[position - mIndexContent])
    }

    override fun getItemCount(): Int {
        return mTextLogDataList.size + mIndexContent
    }

    override fun getItemViewType(position: Int): Int {
        if (position <= mIndexHeader) return ViewHolderFactory.ViewType.HEADER
        else return ViewHolderFactory.ViewType.SWITCH
    }

    private fun reconfigure() {
        mIndexHeader = 0
        mIndexContent = mIndexHeader + 1
        mIndexFooter = mTextLogDataList.size + mIndexContent
        notifyDataSetChanged()
    }

    fun add(data: SwitchModel) {
        synchronized(SwitchAdapter::class.java) {
            mTextLogDataList.add(data)
            while (mTextLogDataList.size > MAX_TEXT_LOGS) mTextLogDataList.removeAt(0)
            reconfigure()
        }
    }

    fun clear() {
        synchronized(SwitchAdapter::class.java) {
            mTextLogDataList.clear()
            reconfigure()
        }
    }
}
