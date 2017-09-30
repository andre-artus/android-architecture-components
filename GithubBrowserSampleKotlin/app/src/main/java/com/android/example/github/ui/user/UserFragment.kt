/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.example.github.ui.user

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.example.github.R
import com.android.example.github.binding.FragmentDataBindingComponent
import com.android.example.github.databinding.UserFragmentBinding
import com.android.example.github.di.Injectable
import com.android.example.github.ui.common.NavigationController
import com.android.example.github.ui.common.RepoListAdapter
import com.android.example.github.util.AutoClearedValue
import javax.inject.Inject

class UserFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var navigationController: NavigationController

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    lateinit var userViewModel: UserViewModel
    lateinit var binding: AutoClearedValue<UserFragmentBinding>
    lateinit var adapter: AutoClearedValue<RepoListAdapter>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val dataBinding = DataBindingUtil.inflate<UserFragmentBinding>(inflater, R.layout.user_fragment,
                                                                       container, false, dataBindingComponent)
        dataBinding.setRetryCallback { userViewModel.retry() }
        binding = AutoClearedValue(this, dataBinding)
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)
        userViewModel.setLogin(arguments.getString(LOGIN_KEY))
        userViewModel.user.observe(this, Observer { userResource ->
            binding.get().user = userResource?.data
            binding.get().userResource = userResource
            // this is only necessary because espresso cannot read data binding callbacks.
            binding.get().executePendingBindings()
        })
        val rvAdapter = RepoListAdapter(dataBindingComponent, false) {
            navigationController.navigateToRepo(it.owner.login, it.name)
        }
        binding.get().repoList.adapter = rvAdapter
        this.adapter = AutoClearedValue(this, rvAdapter)
        initRepoList()
    }

    private fun initRepoList() {
        userViewModel.repositories.observe(this, Observer { repos ->
            // no null checks for adapter.get() since LiveData guarantees that we'll not receive
            // the event if fragment is now show.
            if (repos == null) {
                adapter.get().setList(null)
            } else {
                adapter.get().setList(repos.data)
            }
        })
    }

    companion object {
        private val LOGIN_KEY = "login"

        fun create(login: String): UserFragment {
            val userFragment = UserFragment()
            val bundle = Bundle()
            bundle.putString(LOGIN_KEY, login)
            userFragment.arguments = bundle
            return userFragment
        }
    }
}
