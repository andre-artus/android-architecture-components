package com.android.example.githubkotlin.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.annotation.VisibleForTesting

import com.android.example.githubkotlin.ui.repo.RepoViewModel
import com.android.example.githubkotlin.ui.search.SearchViewModel
import com.android.example.githubkotlin.ui.user.UserViewModel
import com.android.example.githubkotlin.viewmodel.GithubViewModelFactory

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindUserViewModel(userViewModel: UserViewModel): ViewModel

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    @Binds
    @IntoMap
    @ViewModelKey(RepoViewModel::class)
    abstract fun bindRepoViewModel(repoViewModel: RepoViewModel): ViewModel

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    @Binds
    abstract fun bindViewModelFactory(factory: GithubViewModelFactory): ViewModelProvider.Factory
}
