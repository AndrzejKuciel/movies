package com.andysworkshop.movies.popularmoviesscreen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andysworkshop.movies.databinding.FragmentPopularMoviesBinding
import com.andysworkshop.movies.popularmoviesscreen.data.PopularMoviesUIData
import com.andysworkshop.movies.popularmoviesscreen.di.PopularMoviesScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class PopularMoviesFragment : Fragment() {

    @PopularMoviesScope
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var _binding: FragmentPopularMoviesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: PopularMoviesViewModel by viewModels { viewModelFactory }


    private val moviesListData: MutableList<PopularMoviesUIData> = mutableListOf()

    @VisibleForTesting
    val moviesListDataVisibleForTest get() = moviesListData

    private val moviesViewAdapter = MoviesRecyclerAdapter(moviesListData) { movieData, pos ->
        viewModel.onPosterClicked(movieData, pos)
        shouldSavePositionOnViewDestroy = false
    }

    private var shouldSavePositionOnViewDestroy = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeViewModelMoviesData()
        observeNavigationEvent()
        observeViewModelMoviesRequestError()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)
        val view = binding.root
        view.layoutManager = LinearLayoutManager(context)
        (view.layoutManager as LinearLayoutManager).scrollToPosition(viewModel.recyclerViewPosition)
        view.adapter = moviesViewAdapter

        return view
    }

    override fun onDestroyView() {
        if(shouldSavePositionOnViewDestroy) {
            saveRecyclerPositionInViewModel()
        }
        super.onDestroyView()
        _binding = null
    }

    private fun saveRecyclerPositionInViewModel() {
        _binding?.root?.layoutManager?.let {
            val view = binding.root
            var position =
                (view.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
            if (position == RecyclerView.NO_POSITION) {
                position =
                    (view.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            }
            viewModel.saveRecyclerPosition(position)
        }
    }

    private fun observeViewModelMoviesData() {
        viewModel.moviesSharedFlow.onEach {
            moviesListData.clear()
            moviesListData.addAll(it)
            println("Fragment successfully got movies data: ")
            moviesViewAdapter.notifyDataSetChanged()
        }.launchIn(lifecycleScope)

    }

    private fun observeViewModelMoviesRequestError() {
        viewModel.moviesRequestError.onEach {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }.launchIn(lifecycleScope)
    }

    private fun observeNavigationEvent() {
        viewModel.navigateMovieDetails.onEach { movieData ->
            movieData.let {
                val action = PopularMoviesFragmentDirections
                    .actionPopularMoviesFragmentToMovieDetailFragment(it.id, it.posterPath)
                findNavController().navigate(action)
            }
        }.launchIn(lifecycleScope)
    }
}
