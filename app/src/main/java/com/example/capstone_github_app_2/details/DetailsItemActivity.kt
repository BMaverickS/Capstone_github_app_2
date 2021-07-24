package com.example.capstone_github_app_2.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.capstone_github_app_2.R
import com.capstone_github_app_2.core.data.Resource
import com.capstone_github_app_2.core.domain.model.User
import com.example.capstone_github_app_2.databinding.ActivityDetailsItemBinding
import com.example.capstone_github_app_2.main.SearchViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsItemActivity : AppCompatActivity() {

    companion object
    {
        const val USNAME_DATA = "usname_data"
        const val FAV_FLAG = "fav_flag"
    }

    private var detailsItemBinding : ActivityDetailsItemBinding? = null
    private val binding get() = detailsItemBinding!!

    private val detailsViewModel : DetailsViewModel by viewModel()
    private val searchViewModel : SearchViewModel by viewModel()

    private lateinit var dataIntent : User
    private var tempGet : Boolean? = null
    private var detailFavorite : Int = 0

    private var menu : Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailsItemBinding = ActivityDetailsItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.details_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        detailFavorite = intent.getIntExtra(FAV_FLAG, 0)

        if (detailFavorite == 1)
        {
            getFavUserData ()
        }
        else if (detailFavorite == 0)
        {
            getUserData ()
        }
    }

    private fun getFavUserData ()
    {
        dataIntent = intent.getParcelableExtra(USNAME_DATA)!!
        tempGet = dataIntent.favorite

        binding.detailView.visibility = View.VISIBLE

        Glide.with(this@DetailsItemActivity).load(dataIntent.avatarUrl).into(binding.detailsImg)
        binding.detailsUsname.text = dataIntent.login
        binding.detailsName.text = dataIntent.name
        binding.detailsFollower.text = dataIntent.followers
        binding.detailsFollowing.text = dataIntent.following
        binding.detailsCompany.text = dataIntent.company
        binding.detailsLocation.text = dataIntent.location
        binding.detailsRepository.text = dataIntent.publicRepos
    }

    private fun getUserData ()
    {
        dataIntent = intent.getParcelableExtra(USNAME_DATA)!!

        searchViewModel.setUsername(dataIntent.login.toString())
        searchViewModel.user.observe(this, { user ->
            if (user != null)
            {
                when (user)
                {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE

                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.detailView.visibility = View.VISIBLE

                        Glide.with(this@DetailsItemActivity).load(user.data?.avatarUrl)
                                .into(binding.detailsImg)
                        binding.detailsUsname.text = user.data?.login
                        binding.detailsName.text = user.data?.name
                        binding.detailsFollower.text = user.data?.followers
                        binding.detailsFollowing.text = user.data?.following
                        binding.detailsCompany.text = user.data?.company
                        binding.detailsLocation.text = user.data?.location
                        binding.detailsRepository.text = user.data?.publicRepos

                        tempData(user.data!!)
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.detailView.visibility = View.GONE
                        binding.errorView.root.visibility =
                        if (user.data?.equals(null) == false) View.GONE else View.VISIBLE
                    }
                }
            }
        })
    }

    private fun tempData(user: User)
    {
        tempGet = user.favorite
        dataIntent = user
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite_only, menu)
        this.menu = menu

        searchViewModel.setUsername(dataIntent.login.toString())
        searchViewModel.user.observe(this, { user ->
            if (user != null)
            {
                when (user)
                {
                    is Resource.Success -> {
                        val state = user.data?.favorite
                        if (state != null)
                        {
                            setMenuButton(state)
                            dataIntent.favorite = state
                        }
                    }
                }
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_favorite_only)
        {
            searchViewModel.user.observe(this, { user ->
                if (user != null)
                {
                    when (user)
                    {
                        is Resource.Success -> {
                            tempGet = user.data?.favorite
                        }
                    }
                }
            })

            val userState = tempGet?.not()
            detailsViewModel.setFavoriteUser(dataIntent, userState!!)

            setMenuButton(userState)

            Toast.makeText(this, "Success !", Toast.LENGTH_SHORT).show()
            return true
        }

        if (item.itemId == android.R.id.home)
        {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setMenuButton (state : Boolean)
    {
        if (menu == null) return

        val menuItem = menu?.findItem(R.id.nav_favorite_only)
        if (state)
        {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24)
        }
        else
        {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24)
        }
    }
}