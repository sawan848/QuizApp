package com.example.quizapp.di

import com.example.quizapp.data.RemoteDataRepository
import com.example.quizapp.use_case.GetAllQuestionUseCase
import com.example.quizapp.use_case.QuestionUseCases
import com.example.quizapp.util.Constant
import com.example.quizapp.data.RemoteDataRepositoryImpl
import com.example.quizapp.data.QuestionApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi():Moshi=Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideUseCases(repository: RemoteDataRepository): QuestionUseCases {
        return QuestionUseCases(
            getAllQuestion = GetAllQuestionUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideRepository(api: QuestionApi): RemoteDataRepository {
        return RemoteDataRepositoryImpl(api)
    }


    @Provides
    @Singleton
    fun provideTodoApi(moshi: Moshi): QuestionApi {

        return Retrofit.Builder().
            baseUrl(Constant.BASE_URL).
            addConverterFactory(MoshiConverterFactory.create(moshi)).
        build().create(QuestionApi::class.java)
    }

}