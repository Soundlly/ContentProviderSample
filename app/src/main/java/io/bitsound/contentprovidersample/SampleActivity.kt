package io.bitsound.contentprovidersample

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import io.bitsound.contentprovidersample.models.SwitchModel
import io.bitsound.contentprovidersample.switch.SwitchAdapter
import kotlinx.android.synthetic.main.activity_sample.*

class SampleActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_storage_local -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_storage_shared -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        LinearLayoutManager(this).let { llVertical ->
            llVertical.orientation = LinearLayoutManager.VERTICAL
            recyclerview_switch.apply {
                adapter = SwitchAdapter(arrayListOf(
                    SwitchModel(null, "true", true),
                    SwitchModel(null, "false", false)
                ))
                layoutManager = llVertical
                setHasFixedSize(true)
            }
        }
    }
}
