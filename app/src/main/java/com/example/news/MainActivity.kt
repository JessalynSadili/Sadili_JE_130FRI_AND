package com.example.news

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val items = listOf(
        MyItem("Government Launches National Health Campaign to Combat Obesity", "The government has initiated a nationwide campaign aimed at reducing obesity rates, particularly focusing on youth and promoting healthier eating habits and physical activity."),
        MyItem("Tech Industry Sees Record Growth Amid Global Economic Challenges", "Despite worldwide economic uncertainty, the local tech industry has reported unprecedented growth, driven by increased investment in innovation and digital services."),
        MyItem("Major Infrastructure Projects Announced to Boost Economic Development", "The government has unveiled new infrastructure plans, including road and rail development projects, to enhance trade routes and stimulate economic activity in underserved regions."),
        MyItem("Environmentalists Urge Stronger Action Against Deforestation", "Conservation groups are calling for immediate government action to address deforestation, which they say is rapidly increasing due to illegal logging and land conversion for agriculture."),
        MyItem("Education Sector Embraces Digital Learning Tools Post-Pandemic", "Schools and universities are increasingly adopting digital learning platforms and tools, as the country continues to modernize its education system following lessons learned from the pandemic.")
    )

    private lateinit var listView: ListView
    private lateinit var fragmentContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        fragmentContainer = findViewById(R.id.fragmentContainer)

        listView.adapter = MyListAdapter(items) { item ->
            val fragment = ItemDetailFragment.newInstance(item)

            if (isPortraitMode()) {
                // In portrait mode, hide the ListView and show the fragment
                listView.visibility = View.GONE
                fragmentContainer.visibility = View.VISIBLE
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                // In landscape mode, replace the fragment in the fragment container
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun onBackPressed() {
        if (isPortraitMode()) {
            // In portrait mode, always show the ListView on back
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
                // Show ListView and hide fragment container
                listView.visibility = View.VISIBLE
                fragmentContainer.visibility = View.GONE
            } else {
                super.onBackPressed() // Call super to exit the app
            }
        } else {
            // In landscape mode, default behavior
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                super.onBackPressed() // Call super to exit the app
            }
        }
    }

    private fun isPortraitMode(): Boolean {
        return resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
    }
}