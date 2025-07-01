package com.example.castroparcial2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.castroparcial2.Character


class MainActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private var listCharacters = mutableListOf<Character>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.myToolbar)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.recyclerCharacters) // Si querés que sea igual al profe, poné recyclerQuakes en el XML
        tvTitle = findViewById(R.id.textViewTitle)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter(listCharacters)
        recyclerView.adapter = adapter

        adapter.onItemClickListener = { character ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("character", character)
            startActivity(intent)
        }

        getCharacters()
    }

    private fun getCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getAllCharacters()
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    listCharacters.clear()
                    val characters = response?.results
                    characters?.forEach {
                        listCharacters.add(it)
                    }
                    tvTitle.text = "Que personaje queres elegir?"
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun filterByStatus(status: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getCharactersByStatus(status)
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    val filteredList = response?.results ?: emptyList()
                    adapter = Adapter(filteredList)
                    recyclerView.adapter = adapter

                    adapter.onItemClickListener = { character ->
                        val intent = Intent(this@MainActivity, DetailActivity::class.java)
                        intent.putExtra("character", character)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                showFilterDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showFilterDialog() {
        val options = arrayOf("Alive", "Dead", "Unknown")
        val statuses = arrayOf("alive", "dead", "unknown")

        AlertDialog.Builder(this)
            .setTitle("Filtrá por estado")
            .setItems(options) { _, which ->
                filterByStatus(statuses[which])
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }
}