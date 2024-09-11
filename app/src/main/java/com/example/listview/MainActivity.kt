package com.example.listview

import android.os.Bundle
import android.widget.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var editTextItem: EditText
    private lateinit var addButton: Button
    private lateinit var adapter: TodoAdapter
    private val itemList = mutableListOf<TodoItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initializing views
        listView = findViewById(R.id.todo_list_view)
        editTextItem = findViewById(R.id.edit_text_item)
        addButton = findViewById(R.id.add_button)

        // Adapter initialization
        adapter = TodoAdapter(itemList)
        listView.adapter = adapter

        // Adding new item on button click
        addButton.setOnClickListener {
            val newItemText = editTextItem.text.toString()
            if (newItemText.isNotEmpty()) {
                // Randomly select one of the three images
                val images = listOf(R.drawable.img, R.drawable.img_1, R.drawable.picture)
                val randomImage = images.random()

                val newItem = TodoItem(newItemText, imageResId = randomImage)
                itemList.add(newItem)
                adapter.notifyDataSetChanged() // Notify adapter about data changes
                editTextItem.text.clear() // Clear EditText after adding item
            }
        }

        // Setting an item click listener for editing/deleting items
        listView.setOnItemClickListener { _, _, position, _ ->
            val item = itemList[position]
            showOptionsDialog(item, position)
        }
    }

    // Function to show options dialog (Edit or Delete)
    private fun showOptionsDialog(item: TodoItem, position: Int) {
        AlertDialog.Builder(this).apply {
            setTitle("Choose Action")
            setItems(arrayOf("Edit", "Delete")) { _, which ->
                when (which) {
                    0 -> showEditDialog(item, position) // Edit action
                    1 -> {
                        itemList.removeAt(position) // Delete action
                        adapter.notifyDataSetChanged()
                    }
                }
            }
            show()
        }
    }

    // Function to show edit dialog
    private fun showEditDialog(item: TodoItem, position: Int) {
        val editText = EditText(this)
        editText.setText(item.text)

        AlertDialog.Builder(this).apply {
            setTitle("Edit Item")
            setView(editText)
            setPositiveButton("Save") { _, _ ->
                item.text = editText.text.toString()
                adapter.notifyDataSetChanged() // Update the item in the list
            }
            setNegativeButton("Cancel", null)
            show()
        }
    }

    // Custom adapter class to handle list items
    inner class TodoAdapter(private val items: List<TodoItem>) : BaseAdapter() {

        override fun getCount(): Int = items.size

        override fun getItem(position: Int): Any = items[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: LayoutInflater.from(this@MainActivity).inflate(
                R.layout.todo_list_item, parent, false
            )

            val item = items[position]
            val checkBox = view.findViewById<CheckBox>(R.id.check_box)
            val textView = view.findViewById<TextView>(R.id.item_text)
            val imageView = view.findViewById<ImageView>(R.id.item_image)

            textView.text = item.text
            checkBox.isChecked = item.isChecked

            // Set the random image for this item
            imageView.setImageResource(item.imageResId)

            // Set click listener for the entire list item (checkbox, text, image)
            view.setOnClickListener {
                showOptionsDialog(item, position)
            }

            // Update the checked status of the item
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                item.isChecked = isChecked
            }

            return view
        }
    }

    // Data class representing a to-do item
    data class TodoItem(var text: String, var isChecked: Boolean = false, var imageResId: Int)
}
