package com.capstone_github_app_2.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone_github_app_2.core.ui.SearchAdapter
import com.example.Capstone_github_app.favorite.databinding.ActivityFavoriteListBinding
import com.example.capstone_github_app_2.R
import com.example.capstone_github_app_2.details.DetailsItemActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteListActivity : AppCompatActivity() {

    private var favoriteListBinding : ActivityFavoriteListBinding? = null
    private val binding get() = favoriteListBinding!!

    private val favoriteViewModel : FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favoriteListBinding = ActivityFavoriteListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)

        supportActionBar?.title = getString(R.string.favorite_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getFavoriteData ()
    }

    private fun getFavoriteData ()
    {
        val favAdapter = SearchAdapter()

        favAdapter.onItemClick = { selData ->
            val detailsIntent = Intent(this@FavoriteListActivity, DetailsItemActivity::class.java)
            detailsIntent.putExtra(DetailsItemActivity.USNAME_DATA, selData)
            detailsIntent.putExtra(DetailsItemActivity.FAV_FLAG, 1)
            startActivity(detailsIntent)
        }

        favoriteViewModel.favoriteUser.observe(this, { dataFav ->
            if (dataFav != null)
            {
                favAdapter.putData(dataFav)
            }
        })

        with (binding.rvFavorite)
        {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favAdapter
        }
    }
}