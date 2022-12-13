package com.binar.gosky.presentation.ui.account

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.gosky.R
import com.binar.gosky.data.network.model.auth.user.CurrentUserData
import com.binar.gosky.databinding.FragmentAccountBinding
import com.binar.gosky.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.apply {
            tvEditProfile.setOnClickListener {
                findNavController().navigate(R.id.action_accountFragment_to_editProfileFragment)
            }
            tvLogOut.setOnClickListener {
                viewModel.setUserLogin(false)
                viewModel.setUserAccessToken("null")
                //findNavController().navigate(R.id.action_accountFragment_to_loginFragment)
            }
        }
    }

    private fun observeData() {
        viewModel.getUserAccessToken().observe(viewLifecycleOwner) {
            viewModel.getCurrentUser("Bearer $it")
            Log.d("accessToken", it)
        }
        viewModel.currentUserResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.payload?.data?.let { currentUserData -> bindDataIntoForm(currentUserData) }
                }
                else -> {}
            }
        }
    }

    private fun bindDataIntoForm(currentUserData: CurrentUserData) {
        binding.apply {
            tvProfileName.text = currentUserData.name
            tvEmail.text = currentUserData.email
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}