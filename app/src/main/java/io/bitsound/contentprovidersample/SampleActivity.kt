package io.bitsound.contentprovidersample

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import io.bitsound.contentprovidersample.switch.SwitchAdapter
import kotlinx.android.synthetic.main.activity_sample.*

class SampleActivity : AppCompatActivity() {

    private val localSwitchAdapter by lazy { SwitchAdapter(this@SampleActivity, BuildConfig.LOCAL_PROVIDER_AUTHORITY) }
    private val sharedSwitchAdapter by lazy { SwitchAdapter(this@SampleActivity, BuildConfig.SHARED_PROVIDER_AUTHORITY) }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_storage_local -> {
                recyclerview_switch.adapter = localSwitchAdapter
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_storage_shared -> {
                recyclerview_switch.adapter = sharedSwitchAdapter
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        LinearLayoutManager(this).let { llVertical ->
            llVertical.orientation = LinearLayoutManager.VERTICAL
            recyclerview_switch.apply {
                layoutManager = llVertical
                addItemDecoration(DividerItemDecoration(this@SampleActivity, llVertical.orientation))
                setHasFixedSize(true)
            }
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_storage_local
    }
}
