package com.project.test_3ss.koin

import com.project.test_3ss.data.local.LocalDataSource
import com.project.test_3ss.data.network.ApiInterface
import com.project.test_3ss.data.network.RemoteDataSource
import com.project.test_3ss.data.repository.Repository
import com.project.test_3ss.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object AppModules {

    private val retrofitModule: Module = module {
        single {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
            client.addInterceptor(interceptor)

            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client.build())
                .build()
                .create(ApiInterface::class.java)
        }
    }

    private val remoteDataSourceModule: Module = module {
        single { RemoteDataSource() }
    }

    private val localDataSourceModule: Module = module {
        single { LocalDataSource() }
    }

    private val repoModule: Module = module {
        single { Repository() }
    }

//    private val notificationHandlerModule = module {
//        single { NotificationHandler() }
//    }

    val appModules =
        listOf(retrofitModule, repoModule, remoteDataSourceModule, localDataSourceModule)
}