package com.capstone_github_app_2.core.di

import androidx.room.Room
import com.capstone_github_app_2.core.data.source.remote.SearchDataSource
import com.capstone_github_app_2.core.data.SearchRepository
import com.capstone_github_app_2.core.data.source.local.LocalDataSource
import com.capstone_github_app_2.core.data.source.local.room.UserDatabase
import com.capstone_github_app_2.core.data.source.remote.network.ApiService
import com.capstone_github_app_2.core.domain.repository.ISearchRepository
import com.capstone_github_app_2.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val searchDbModule = module {
    factory {
        get<UserDatabase>().UserDao()
    }

    single {
        val userPphrase : ByteArray = SQLiteDatabase.getBytes("capstone".toCharArray())
        val factory = SupportFactory(userPphrase)
        Room.databaseBuilder(
            androidContext(),
            UserDatabase::class.java,
            "User.db"
        ).fallbackToDestructiveMigration().openHelperFactory(factory).build()
    }
}

val searchNetworkModule = module {
    single {
        val userHostname = "api.github.com"
        val githubCertificatePinner = CertificatePinner.Builder()
                .add(userHostname, "sha256/azE5Ew0LGsMgkYqiDpYay0olLAS8cxxNGUZ8OJU756k=")
                .add(userHostname, "sha256/vnCogm4QYze/Bc9r88xdA6NTQY74p4BAz2w5gxkLG2M=")
                .add(userHostname, "sha256/WoiWRyIOVNa9ihaBciRSC7XHjliYS9VwUGOIud4PB18=")
                .build()

        OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .certificatePinner(githubCertificatePinner)
                .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

val searchRepoModule = module {
    single {
        LocalDataSource(get())
    }

    single {
        SearchDataSource(get())
    }

    factory {
        AppExecutors()
    }

    single<ISearchRepository> {
        SearchRepository(get(), get(), get())
    }
}