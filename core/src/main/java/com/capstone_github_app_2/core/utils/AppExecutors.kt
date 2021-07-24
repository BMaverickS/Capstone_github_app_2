package com.capstone_github_app_2.core.utils

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors(private val diskIO: Executor) {

    constructor() : this(
            Executors.newSingleThreadExecutor()
    )

    fun diskIO() : Executor = diskIO
}