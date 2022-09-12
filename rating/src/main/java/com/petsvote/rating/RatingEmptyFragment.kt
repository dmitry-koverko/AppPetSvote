package com.petsvote.rating

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.petsvote.rating.databinding.FragmentRatingEmptyBinding

class RatingEmptyFragment: Fragment(R.layout.fragment_rating_empty) {

    var binding: FragmentRatingEmptyBinding? = null

    var recyclerViewAdapter: RecyclerViewAdapter? = null
    var rowsArrayList = mutableListOf<String?>()

    var isLoading = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRatingEmptyBinding.bind(view)

        populateData();
        initAdapter();
        initScrollListener();
    }

    private fun populateData() {
        var i = 0
        while (i < 50) {
            rowsArrayList.add("Item $i")
            i++
        }
    }

    private fun initAdapter() {
        recyclerViewAdapter = RecyclerViewAdapter(rowsArrayList)
        binding?.recycler?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recycler?.setAdapter(recyclerViewAdapter)
    }

    private fun initScrollListener() {
        binding?.recycler?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(@NonNull recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager: LinearLayoutManager =
                    recyclerView.getLayoutManager() as LinearLayoutManager
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() === rowsArrayList.size - 25) {
                        //bottom of list!
                        loadMore()
                        isLoading = true
                    }else if(linearLayoutManager != null && linearLayoutManager.findFirstCompletelyVisibleItemPosition() < 25){
                        loadTop()
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun loadTop() {
        val handler = Handler()
        handler.postDelayed(Runnable {
            Log.d("TAG", "offset = ${rowsArrayList.size}")
            populateData()
            recyclerViewAdapter?.notifyDataSetChanged()
            isLoading = false
        }, 500)
    }

    private fun loadMore() {
        val handler = Handler()
        handler.postDelayed(Runnable {
            Log.d("TAG", "offset = ${rowsArrayList.size}")
            populateData()
            recyclerViewAdapter?.notifyDataSetChanged()
            isLoading = false
        }, 500)
    }

}