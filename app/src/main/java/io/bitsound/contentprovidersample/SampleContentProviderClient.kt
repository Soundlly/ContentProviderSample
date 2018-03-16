package io.bitsound.contentprovidersample

import android.net.Uri
import io.bitsound.contentprovidersample.models.SwitchModel
import io.bitsound.contentprovidersample.tables.SwitchTable

object SampleContentProviderClient {
    fun dirUriOf(authority: String) : Uri?
        = Uri.parse("content://$authority/${SwitchTable.NAME}")
    fun itemUriOf(authority: String, switchModel: SwitchModel) : Uri?
        = Uri.parse("content://$authority/${SwitchTable.NAME}/${switchModel.id}")
}
