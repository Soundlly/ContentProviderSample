package io.bitsound.contentprovidersample.switch

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.util.Log
import android.view.ViewGroup
import android.widget.EditText
import io.bitsound.contentprovidersample.SampleContentProviderClient
import io.bitsound.contentprovidersample.models.SwitchModel
import io.bitsound.contentprovidersample.models.toSwitchModel
import io.bitsound.contentprovidersample.switch.viewholder.FooterViewHolder
import io.bitsound.contentprovidersample.switch.viewholder.HeaderViewHolder
import io.bitsound.contentprovidersample.switch.viewholder.SwitchViewHolder
import io.bitsound.contentprovidersample.switch.viewholder.ViewHolderFactory
import io.bitsound.contentprovidersample.tables.SwitchTable


class SwitchAdapter(private val context: Context, private val authority: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var switches: ArrayList<SwitchModel>
    private var iHeader: Int = 0 // First Header Index
    private var iSwitch: Int = 0 // First Switch Index
    private var iFooter: Int = 0 // First Footer Index

    init {
        reconfigure()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderFactory.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(position) {
            in iHeader until iSwitch -> (holder as HeaderViewHolder).bind()
            in iSwitch until iFooter -> (holder as SwitchViewHolder).bind(switches[position - iSwitch])
            iFooter -> (holder as FooterViewHolder).bind({ view ->
                val input = EditText(context).apply {
                    inputType = InputType.TYPE_CLASS_TEXT
                }

                AlertDialog.Builder(context)
                    .setTitle("Insert Switch Label")
                    .setView(input)
                    .setPositiveButton(android.R.string.ok, { _, _ ->
                        this.add(SwitchModel(null, input.text.toString(), false))
                    })
                    .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                        dialog.cancel()
                    }
                    .show()
            })
        }
    }

    override fun getItemCount(): Int = iFooter + 1

    override fun getItemViewType(position: Int): Int = when(position) {
        in iHeader until iSwitch -> ViewHolderFactory.ViewType.HEADER
        in iSwitch until iFooter -> ViewHolderFactory.ViewType.SWITCH
        iFooter -> ViewHolderFactory.ViewType.FOOTER
        else -> ViewHolderFactory.ViewType.NONE
    }

    private fun reconfigure() {
        switches = ArrayList()
        context.contentResolver.query(
            SampleContentProviderClient.dirUriOf(authority),
            SwitchTable.Columns.all,
            null,
            null,
            null
        )?.let { cursor ->
            if (cursor.moveToFirst()) {
                do switches.add(cursor.toSwitchModel())
                while(cursor.moveToNext())
            }
            cursor.close()
        }

        iHeader = 0
        iSwitch = iHeader + 1
        iFooter = switches.size + iSwitch
        notifyDataSetChanged()
    }

    private fun add(data: SwitchModel) {
        synchronized(SwitchAdapter::class.java) {
            val returned = context.contentResolver.insert(SampleContentProviderClient.dirUriOf(authority), data.toContentValues())
            Log.d("SwitchAdapter.add", returned.toString())
            data.id = returned.pathSegments[1].toLongOrNull()
            switches.add(data)
            reconfigure()
        }
    }

    fun removeAt(index: Int) {
        synchronized(SwitchAdapter::class.java) {
            switches.removeAt(index)
            reconfigure()
        }
    }

    fun clear() {
        synchronized(SwitchAdapter::class.java) {
            switches.clear()
            reconfigure()
        }
    }
}
