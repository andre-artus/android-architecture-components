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

package com.android.example.githubkotlin.ui.common

import android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_CLOSE
import com.android.example.githubkotlin.MainActivity
import com.android.example.githubkotlin.OpenClassOnDebug
import com.android.example.githubkotlin.R
import com.android.example.githubkotlin.ui.repo.RepoFragment
import com.android.example.githubkotlin.ui.search.SearchFragment
import com.android.example.githubkotlin.ui.user.UserFragment
import javax.inject.Inject

/**
 * A utility class that handles navigation in [MainActivity].
 */
@OpenClassOnDebug
class NavigationController @Inject
constructor(mainActivity: MainActivity) {
    private val containerId = R.id.container
    private val fragmentManager = mainActivity.supportFragmentManager

    fun navigateToSearch() {
        val searchFragment = SearchFragment()
        fragmentManager.beginTransaction()
                .setTransition(TRANSIT_FRAGMENT_CLOSE)
                .replace(containerId, searchFragment)
                .commitAllowingStateLoss()
    }

    fun navigateToRepo(owner: String, name: String) {
        val fragment = RepoFragment.create(owner, name)
        val tag = "repo/$owner/$name"
        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(containerId, fragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss()
    }

    fun navigateToUser(login: String) {
        val tag = "user" + "/" + login
        val userFragment = UserFragment.create(login)
        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(containerId, userFragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss()
    }
}
