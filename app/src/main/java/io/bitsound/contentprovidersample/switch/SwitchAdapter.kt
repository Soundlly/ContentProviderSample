package io.bitsound.contentprovidersample.switch

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import io.bitsound.contentprovidersample.R
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
            in iSwitch until iFooter -> (holder as SwitchViewHolder).bind(switches[position - iSwitch], {
                this.removeAt(position - iSwitch)
            })
            iFooter -> (holder as FooterViewHolder).bind({ _ ->
                AlertDialog.Builder(context)
                    .setTitle("Insert Switch Label")
                    .setView(R.layout.input_switch)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok, { dialog, _ ->
                        val key = (dialog as AlertDialog).findViewById<EditText>(R.id.input_switch)?.text.toString()
                        if (key.isNotBlank()) this.add(SwitchModel(null, key, false))
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
        try {
            context.contentResolver
                .query(SampleContentProviderClient.dirUriOf(authority), SwitchTable.Columns.all, null, null, null)
                ?.let { cursor ->
                    if (cursor.moveToFirst()) {
                        do switches.add(cursor.toSwitchModel())
                        while (cursor.moveToNext())
                    }
                    cursor.close()
                }
        } catch (e: SecurityException) {
            Toast.makeText(context, "Failed to fetch through ${SampleContentProviderClient.dirUriOf(authority)}", Toast.LENGTH_LONG).show()
        }

        iHeader = 0
        iSwitch = iHeader + 1
        iFooter = switches.size + iSwitch
        notifyDataSetChanged()
    }

    private fun add(data: SwitchModel) {
        synchronized(SwitchAdapter::class.java) {
            try {
                val returned = context.contentResolver.insert(SampleContentProviderClient.dirUriOf(authority), data.toContentValues())
                Log.d("SwitchAdapter.add", returned.toString())
                data.id = returned.pathSegments[1].toLongOrNull()
                switches.add(data)
            } catch (e: SecurityException) {
                Toast.makeText(context, "Failed to insert $data through ${SampleContentProviderClient.dirUriOf(authority)}", Toast.LENGTH_LONG).show()
            }
            reconfigure()
        }
    }

    private fun removeAt(index: Int) {
        synchronized(SwitchAdapter::class.java) {
            val switch = switches[index]
            try {
                val returned = context.contentResolver.delete(SampleContentProviderClient.itemUriOf(authority, switch), null, null)
                Log.d("SwitchAdapter.removeAt", returned.toString())
                switches.removeAt(index)
            } catch (e: SecurityException) {
                Toast.makeText(context, "Failed to delete $switch through ${SampleContentProviderClient.dirUriOf(authority)}", Toast.LENGTH_LONG).show()
            }
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
