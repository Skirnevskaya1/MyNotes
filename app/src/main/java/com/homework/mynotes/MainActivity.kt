package com.homework.mynotes

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.homework.mynotes.adapters.NoteRecyclerAdapter
import com.homework.mynotes.dataNotes.CardData
import com.homework.mynotes.dataNotes.CardsSourceFirebaseImpl
import com.homework.mynotes.notes.NotesFragment
import com.homework.mynotes.notes.NotesListMainFragment

class MainActivity : AppCompatActivity(), NoteRecyclerAdapter.Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMainFragment()
        initToolbarDrawer()
    }

    override fun onBackPressed() {
        val currentFrag = supportFragmentManager.findFragmentById(R.id.main_frag) as Fragment
        if (currentFrag is NotesFragment) {
            if (currentFrag.view != null) {
                val cardData = CardData(currentFrag.getTitle(), currentFrag.getDescription())
                cardData.id = currentFrag.cardId

                CardsSourceFirebaseImpl().replaceNote(cardData)
            }
        }
        super.onBackPressed()
    }

    override fun notesListItemClicked(id: Int, idCard: String) {
        val fragDetails = NotesFragment()
        fragDetails.setNoteId(id)
        fragDetails.setIdCard(idCard)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frag, fragDetails)
            .addToBackStack(null)
            .commit()
    }

    private fun initMainFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.main_frag, NotesListMainFragment())
            .commit()
    }

    fun test() {
        Snackbar.make(findViewById(R.id.main_frag), "Here's a Snackbar", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }

    private fun initToolbarDrawer() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        initDrawer(toolbar)
    }

    private fun initDrawer(toolbar: Toolbar) {

        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)

        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView: NavigationView = findViewById(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_drawer_about -> {
                    Snackbar.make(
                        findViewById(R.id.main_frag),
                        "Это кнопка ради кнопки",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("Понял", {})
                        .show()
                }
                R.id.action_drawer_exit -> {
                    finish()
                }
                R.id.add_item -> {
                    openNewNotes()
                    drawer.close()
                }
            }
            return@setNavigationItemSelectedListener false
        }
    }

    fun openNewNotes() {
        val newFragDetail = NotesFragment()
        newFragDetail.setNoteId(NotesFragment.getNewID())

        this.supportFragmentManager.beginTransaction()
            .replace(R.id.main_frag, newFragDetail)
            .addToBackStack(null)
            .commit()

    }
}
