package com.fatal.loosecalories.injection

import android.content.Context
import android.net.ConnectivityManager
import com.fatal.loosecalories.App
import com.fatal.loosecalories.data.DefaultScheduler
import com.fatal.loosecalories.data.LooseData
import com.fatal.loosecalories.data.local.LocalData
import com.fatal.loosecalories.data.remote.RemoteService
import com.fatal.loosecalories.models.MyObjectBox
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * Created by fatal on 10/28/2017.
 */
@Module
class DataModule {

    fun getOkHttpBuilder(): OkHttpClient.Builder =
            OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor()
                            .apply { HttpLoggingInterceptor.Level.BODY }
                    )

    @Provides
    @Singleton
    fun providesOkHttpClient(app: App): OkHttpClient {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return getOkHttpBuilder()
                .addInterceptor { chain ->
                    val requestBuilder = chain.request().newBuilder()

                    chain.proceed(requestBuilder.build())
                }
                .build()
    }

    @Singleton
    @Provides
    fun providesLocalData(context: Context): LocalData = LocalData(MyObjectBox.builder().androidContext(context).build(), DefaultScheduler);

    @Singleton
    @Provides
    fun providesLooseData(localData: LocalData, remoteService: RemoteService): LooseData = LooseData(localData, remoteService)

    @Provides
    @Singleton
    fun providesRemoteService(okHttpClient: OkHttpClient, gson: Gson): RemoteService {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://www.google.bg/")
                .client(okHttpClient)
                .build()

        return retrofit.create(RemoteService::class.java)
    }
}