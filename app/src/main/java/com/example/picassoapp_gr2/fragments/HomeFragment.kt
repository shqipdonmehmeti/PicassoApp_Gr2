package com.example.picassoapp_gr2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.picassoapp_gr2.adapters.FlagAdapter
import com.example.picassoapp_gr2.databinding.FragmentHomeBinding
import com.example.picassoapp_gr2.helpers.Helpers
import com.example.picassoapp_gr2.helpers.Helpers.provideRetrofitInstance
import com.example.picassoapp_gr2.models.Country
import com.example.picassoapp_gr2.models.Flag
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressBar()
        getFlagsFromApi()
    }

    private fun getFlagsFromApi() {
        provideRetrofitInstance().getFlags().enqueue(object : Callback<List<Country>?> {
            override fun onResponse(call: Call<List<Country>?>, response: Response<List<Country>?>) {
                if (response.isSuccessful && response.body() != null) {
                    val listOfCountry = response.body()
                    val listOfFlags = listOfCountry?.map { it.flags }
                    val flagAdapter = FlagAdapter(listOfFlags!!)
                    binding.rvFlags.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                    binding.rvFlags.adapter = flagAdapter

                    flagAdapter.itemClick = {flag ->
                        val actions = HomeFragmentDirections.actionNavHomeToHomeDetailsFragment(flag.description)
                        findNavController().navigate(actions)
                    }

                }
                hideProgressBar()
            }

            override fun onFailure(call: Call<List<Country>?>, t: Throwable) {
                Snackbar.make(binding.root,"Something went wrong while displaying data",Snackbar.LENGTH_LONG).show()
                hideProgressBar()
            }
        })
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}