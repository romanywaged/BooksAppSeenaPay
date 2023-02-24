package com.romany.newspaperapp_seena.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.romany.newspaperapp_seena.R
import com.romany.newspaperapp_seena.data.models.BookData
import com.romany.newspaperapp_seena.databinding.FragmentHomeBinding
import com.romany.newspaperapp_seena.ui.adapters.BooksAdapter
import com.romany.newspaperapp_seena.ui.listeners.IBookClickedListener
import com.romany.newspaperapp_seena.utlis.CustomProgress
import com.romany.newspaperapp_seena.utlis.DataState
import com.romany.newspaperapp_seena.viewmodels.DataViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), IBookClickedListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var bookList: ArrayList<BookData>
    private lateinit var booksAdapter: BooksAdapter
    private lateinit var customProgress: CustomProgress
    private val dataViewModel: DataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookList = ArrayList()
    }

    override fun onResume() {
        super.onResume()

        if (internetIsConnected()) {
            customProgress =
                CustomProgress.show(
                    requireContext(),
                    getString(R.string.pleaseWait),
                    true,
                    null
                )
            customProgress.setCancelable(false)
            getBooksData()
        } else {
            binding.dataRecycler.visibility = View.GONE
            binding.noInternetConnection.visibility = View.VISIBLE
        }

    }

    private fun getBooksData() {
        dataViewModel.getSchedules()
        lifecycleScope.launchWhenStarted {
            dataViewModel.stateFlowData.collect {
                when (it) {
                    is DataState.Loading -> {
                        customProgress.show()
                    }
                    is DataState.Success -> {
                        customProgress.cancel()
                        bookList = it.dataResponse.results.lists[0].books as ArrayList<BookData>
                        setUpRecyclerData()
                    }
                    is DataState.Failure -> {
                        customProgress.cancel()
                        showSnackBar(it.msg.message!!)
                    }
                    is DataState.Empty -> {
                        customProgress.cancel()
                        showSnackBar(getString(R.string.noDataFound))
                    }
                }
            }
        }
    }

    private fun setUpRecyclerData() {

        binding.dataRecycler.visibility = View.VISIBLE
        binding.noInternetConnection.visibility = View.GONE

        booksAdapter = BooksAdapter(bookList, this)

        binding.dataRecycler.setHasFixedSize(true)
        binding.dataRecycler.layoutManager = LinearLayoutManager(context)
        binding.dataRecycler.adapter = booksAdapter
    }

    private fun showSnackBar(
        text: String,
        @BaseTransientBottomBar.Duration length: Int = Snackbar.LENGTH_SHORT
    ) {
        view?.run { Snackbar.make(this, text, length).show() }
    }

    override fun onBookClicked(bookData: BookData) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToBookDetailsFragment(
                bookData
            )
        )
    }

    private fun internetIsConnected(): Boolean {
        return try {
            val command = "ping -c 1 google.com"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }

}