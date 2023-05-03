package com.example.roomtodo.di

import android.content.Context
import com.example.roomtodo.Utils.Extensions.MoshiUtils
import com.example.roomtodo.databases.AppDatabase
import com.example.roomtodo.networks.JsonAdapter.ApiClient
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase(context)
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return MoshiUtils.getMoshi()
    }

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return ApiClient.getRetrofit(moshi)
    }

//    @Singleton
//    @Provides
//    fun provideUserApiInterface(retrofit: Retrofit): UserInterface {
//        return retrofit.create(UserInterface::class.java)
//    }
//
//    @Singleton
//    @Provides
//    fun provideTodoApiInterface(retrofit: Retrofit): TodosInterface {
//        return retrofit.create(TodosInterface::class.java)
//    }
//    @Singleton
//    @Provides
//    fun providePostApiInterface(retrofit: Retrofit): PostInterface {
//        return retrofit.create(PostInterface::class.java)
//    }
//    @Singleton
//    @Provides
//    fun provideCommentsApiInterface(retrofit: Retrofit): CommentsInterface {
//        return retrofit.create(CommentsInterface::class.java)
//    }
}