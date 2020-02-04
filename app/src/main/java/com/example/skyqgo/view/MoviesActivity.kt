package com.example.skyqgo.view

import android.app.Application
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.TextView
import com.example.skyqgo.MoviesApplication
import com.example.skyqgo.R
import com.example.skyqgo.model.Data
import com.example.skyqgo.view.adapter.MoviesListAdapter
import com.example.skyqgo.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity() , SwipeRefreshLayout.OnRefreshListener{

    lateinit var viewModel: MoviesViewModel
    private var moviesAdapter : MoviesListAdapter = MoviesListAdapter(arrayListOf())
    var movieItems: List<Data> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        instantiateViewModel()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Here is where we are going to implement the filter logic
                Log.e(" newText ::: ", newText)
                performSearch(newText)
                return true
            }
        })
    }

    private fun instantiateViewModel() {

        viewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        viewModel.refresh()

        moviesList.apply {
          layoutManager = GridLayoutManager(context,3)
            adapter = moviesAdapter
        }

        swipeRefreshLayout.setOnRefreshListener(this)

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.movies.observe(this, Observer { movies->
            movies?.let { list ->
                moviesList.visibility = View.VISIBLE
                val dataList = list.data
                Log.e("Data List ", " :: "+dataList)
                moviesAdapter.updateMovies(dataList)
                //movieItems = dataList
                movieItems = list.data
                moviesAdapter.notifyDataSetChanged()
            }
        })

        viewModel.movieLoadError.observe(this, Observer {isError->
            isError?.let { list_error.visibility = if(it) View.VISIBLE else View.GONE }
        })

        viewModel.loading.observe(this, Observer { isLoading->
            isLoading?.let {
                loading_view.visibility = if(it) View.VISIBLE else View.GONE
                if(it){
                    list_error.visibility = View.GONE
                    moviesList.visibility = View.GONE
                }
            }
        })
    }

    fun performSearch(searchText:String) {
        val filteredMovieList : MutableList<Data> = ArrayList()
        //val searchText = searchView.toString()
        if(searchText.isNotEmpty()) {
            filteredMovieList.clear()
            val search = searchText.toLowerCase()
            moviesAdapter.movies.forEach {
                if(it.title.toLowerCase().contains(search)) {
                    filteredMovieList.add(it)
                }
            }
            moviesAdapter.updateMovies(filteredMovieList)
            moviesAdapter.notifyDataSetChanged()
        } else {
            filteredMovieList.clear()
            moviesAdapter.updateMovies(movieItems)
            moviesAdapter.notifyDataSetChanged()
        }
    }



    override fun onRefresh() {
     swipeRefreshLayout.isRefreshing = false
        viewModel.refresh()
    }
}
