package com.example.capstone_github_app_2.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone_github_app_2.R
import com.capstone_github_app_2.core.data.Resource
import com.capstone_github_app_2.core.domain.model.Search
import com.capstone_github_app_2.core.domain.model.User
import com.capstone_github_app_2.core.ui.SearchAdapter
import com.example.capstone_github_app_2.databinding.ActivityMainBinding
import com.example.capstone_github_app_2.details.DetailsItemActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private var mainBinding : ActivityMainBinding? = null
    private val binding get() = mainBinding!!

    private val searchViewModel : SearchViewModel by viewModel()

    private lateinit var srchAdapter : SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getSearchData()
    }

    private fun getSearchData()
    {
        srchAdapter = SearchAdapter()

        srchAdapter.onItemClick = { selData ->
            val detailsIntent = Intent(this@MainActivity, DetailsItemActivity::class.java)
            detailsIntent.putExtra(DetailsItemActivity.USNAME_DATA, selData)
            startActivity(detailsIntent)
        }

        searchViewModel.setUsname("BMaverickS")
        searchViewModel.search.observe(this, { search ->
            if (search != null)
            {
                when (search) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE

                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE

                        if (search.data?.isEmpty() == true)
                        {
                            binding.rvSearch.visibility = View.GONE
                            binding.errorTxt.text = getString(R.string.empty_no_data)
                            binding.errorTxt.visibility = View.VISIBLE
                        }
                        else
                        {
                            binding.rvSearch.visibility = View.VISIBLE
                            binding.errorTxt.visibility = View.GONE
                        }

                        val data = search.data?.let { convertData(it) }

                        srchAdapter.putData(data)
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.rvSearch.visibility = View.GONE
                        binding.errorTxt.visibility = View.VISIBLE
                    }
                }
            }
        })

        with (binding.rvSearch)
        {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = srchAdapter
        }
    }

    private fun convertData(input: List<Search>): List<User>
    {
        val conv = ArrayList<User>()

        input.map {
            val data = User(login = it.login, avatarUrl = it.avatarUrl)
            conv.addAll(listOf(data))
        }

        return conv
    }

    private fun loadProgress(state : Boolean)
    {
        if (state)
        {
            binding.progressBar.visibility = View.VISIBLE
        }
        else
        {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchfield = menu?.findItem(R.id.search)?.actionView as SearchView

        searchfield.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchfield.queryHint = resources.getString(R.string.search)
        searchfield.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                searchViewModel.setUsname(query)
                srchAdapter.notifyDataSetChanged()
                loadProgress(true)

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.nav_favorite)
        {
            startActivity(Intent(this, Class.forName("com.capstone_github_app_2.favorite.FavoriteListActivity")))
        }

        return super.onOptionsItemSelected(item)
    }
}