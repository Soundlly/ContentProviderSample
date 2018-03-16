package io.bitsound.contentprovidersample

import android.database.sqlite.SQLiteException
import io.bitsound.contentprovidersample.models.SwitchModel
import io.bitsound.contentprovidersample.models.toSwitchModel
import io.bitsound.contentprovidersample.tables.SwitchTable
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class SampleDatabaseUnitTest {
    private lateinit var database : SampleDatabase
    private lateinit var switchModelArray: Array<SwitchModel>

    @Rule @JvmField
    val watcher = object : TestWatcher() {
        override fun starting(description: Description) {
            println("- Running ${description.methodName} ...")
        }

        override fun succeeded(description: Description) {
            println("> Result : Success\n")
        }
    }

    @Before
    fun setUp() {
        database = SampleDatabase(RuntimeEnvironment.application)
        switchModelArray = arrayOf(
            SwitchModel(null, "true", true),
            SwitchModel(null, "false", false)
        )
    }

    @Test
    @Throws(Exception::class)
    fun testDatabaseName() {
        assertThat(database.databaseName).isEqualTo(SampleDatabase.DATABASE_NAME)
    }

    @Test
    @Throws(Exception::class)
    fun testInsertElements() {
        switchModelArray.forEach {
            database.writableDatabase.insert(SwitchTable.NAME, null, it.toContentValues())
        }

        val cursor = database.readableDatabase.query(
            SwitchTable.NAME,
            SwitchTable.Columns.all,
            null,
            null,
            null,
            null,
            null
        )

        assertThat(cursor.count).isEqualTo(switchModelArray.size)
        switchModelArray.forEachIndexed { index, namedSwitch ->
            cursor.moveToPosition(index)
            assertThat(cursor.position).isEqualTo(index)
            assertThat(cursor.toSwitchModel().key).isEqualTo(namedSwitch.key)
            assertThat(cursor.toSwitchModel().value).isEqualTo(namedSwitch.value)
        }

        cursor.close()
    }

    @Test(expected = SQLiteException::class)
    @Throws(Exception::class)
    fun testDropTable() {
        assertThat(database.readableDatabase
            .query(SwitchTable.NAME, SwitchTable.Columns.all, null, null, null, null, null)
            .count
        ).isZero()

        SwitchTable.drop(database.writableDatabase)
        database.readableDatabase.query(SwitchTable.NAME, SwitchTable.Columns.all, null, null, null, null, null).count
    }

    @After
    fun tearDown() {
        database.destroy()
    }
}
