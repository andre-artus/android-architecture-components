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

package com.android.example.githubkotlin.ui.repo

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.support.v7.recyclerview.extensions.DiffCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.example.githubkotlin.R
import com.android.example.githubkotlin.databinding.ContributorItemBinding
import com.android.example.githubkotlin.ui.common.DataBoundListAdapter
import com.android.example.githubkotlin.util.Objects
import com.android.example.githubkotlin.vo.Contributor

class ContributorAdapter(private val dataBindingComponent: DataBindingComponent,
                         private val callback: ContributorClickCallback) : DataBoundListAdapter<Contributor, ContributorItemBinding>(object : DiffCallback<Contributor>() {
    override fun areItemsTheSame(oldItem: Contributor,
                                 newItem: Contributor): Boolean {
        return Objects.equals(oldItem.login, newItem.login)
    }

    override fun areContentsTheSame(oldItem: Contributor,
                                    newItem: Contributor): Boolean {
        return oldItem == newItem
    }
}) {

    override fun createBinding(parent: ViewGroup): ContributorItemBinding {
        val binding = DataBindingUtil
                .inflate<ContributorItemBinding>(LayoutInflater.from(parent.context),
                                                 R.layout.contributor_item, parent, false,
                                                 dataBindingComponent)
        binding.root.setOnClickListener {
            val contributor = binding.contributor
            if (contributor != null) {
                callback.onClick(contributor)
            }
        }
        return binding
    }

    override fun bind(binding: ContributorItemBinding, item: Contributor) {
        binding.contributor = item
    }

    interface ContributorClickCallback {
        fun onClick(contributor: Contributor)
    }
}
