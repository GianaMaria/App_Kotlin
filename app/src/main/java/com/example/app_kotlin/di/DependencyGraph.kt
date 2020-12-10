package com.example.app_kotlin.di

import com.example.app_kotlin.data.Repository
import com.example.app_kotlin.data.db.DatabaseProvider
import com.example.app_kotlin.data.db.FireStoreDatabaseProvider
import com.example.app_kotlin.data.model.Note
import com.example.app_kotlin.data.model.NotesRepository
import com.example.app_kotlin.presentation.NoteViewModel
import com.example.app_kotlin.presentation.NotesViewModel
import com.example.app_kotlin.presentation.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

object DependencyGraph {

    private val repositoryModule by lazy {
        module {
            single { Repository(get()) } bind NotesRepository::class
            single { FireStoreDatabaseProvider() } bind DatabaseProvider::class
        }
    }

    private val viewModelModule by lazy {
        module {
            viewModel { NotesViewModel(get()) }
            viewModel { SplashViewModel(get()) }
            viewModel { (note: Note) -> NoteViewModel(get(), note) }
        }
    }

    val modules: List<Module> = listOf(repositoryModule, viewModelModule)
}