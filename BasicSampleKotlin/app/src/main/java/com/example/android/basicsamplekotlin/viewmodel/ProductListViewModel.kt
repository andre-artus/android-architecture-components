/*
 * Copyright 2017, The Android Open Source Project
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

package com.example.android.basicsamplekotlin.viewmodel

import android.app.Application
import android.arch.lifecycle.*
import com.example.android.basicsamplekotlin.db.DatabaseCreator
import com.example.android.basicsamplekotlin.db.entity.ProductEntity

class ProductListViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * Expose the LiveData Products query so the UI can observe it.
     */
    val products: LiveData<List<ProductEntity>>

    init {

        ABSENT.value = null
    }

    init {

        val databaseCreator = DatabaseCreator.getInstance(this.getApplication())

        val databaseCreated = databaseCreator.isDatabaseCreated
        products = Transformations.switchMap(databaseCreated) {
            // Not needed here, but watch out for null
            when {
                it -> databaseCreator.database!!.productDao().loadAllProducts()
                else -> ABSENT
            }
        }
        databaseCreator.createDb(this.getApplication())
    }

    companion object {

        private val ABSENT = MutableLiveData<List<ProductEntity>>()
    }
}
