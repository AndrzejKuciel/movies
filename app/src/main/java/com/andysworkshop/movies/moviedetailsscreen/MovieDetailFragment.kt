package com.andysworkshop.movies.moviedetailsscreen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.andysworkshop.movies.R
import com.andysworkshop.movies.databinding.FragmentMovieDetailsBinding
import com.andysworkshop.movies.moviedetailsscreen.di.MovieDetailScope
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    @MovieDetailScope
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MovieDetailViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentMovieDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        binding.detailsScreen.setOnClickListener { viewModel.onScreenClick() }
        return binding.root

    }

    override fun onAttach(context: Context) {

        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()

        observeDetailData()
        observeRequestError()
        observeNavigation()

        arguments?.let {
            viewModel.onFragmentResumed(
                MovieDetailFragmentArgs.fromBundle(it).movieId
            )

            Picasso.get()
                .load(MovieDetailFragmentArgs.fromBundle(it).posterPath)
                .placeholder(R.color.cardview_dark_background)
                .into(binding.detailsBackground)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeDetailData() {
        viewModel.movieDetailSharedFlow.onEach {
            println("Movie detail fragment got detail data: $it")
            binding.titleTextview.text = it.title
            binding.overviewTextview.text = it.overview
            binding.textHomepage.text = it.homepage
            binding.textReleaseDate.text = it.releaseDate
            binding.textVoteAverage.text = it.voteAverage.toString()
        }.launchIn(lifecycleScope)
    }

    private fun observeRequestError() {
        viewModel.detailsRequestError.onEach {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }.launchIn(lifecycleScope)
    }

    private fun observeNavigation() {
        viewModel.navigationFlow.onEach {
            findNavController().popBackStack()
        }.launchIn(lifecycleScope)
    }
}