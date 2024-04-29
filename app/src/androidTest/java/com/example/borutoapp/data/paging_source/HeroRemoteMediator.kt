package com.example.borutoapp.data.paging_source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.test.core.app.ApplicationProvider
import com.example.borutoapp.data.local.BorutoDatabase
import com.example.borutoapp.data.local.paging_source.HeroRemoteMediator
import com.example.borutoapp.data.remote.FakeRemoteApi2
import com.example.borutoapp.domain.model.Hero
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class HeroRemoteMediator {
    private lateinit var borutoApi: FakeRemoteApi2
    private lateinit var borutoDatabase: BorutoDatabase

    @Before
    fun setup() {
        borutoApi = FakeRemoteApi2()
        borutoDatabase = BorutoDatabase.create(
            context = ApplicationProvider.getApplicationContext(),
            useInMemory = true
        )
    }

    @After
    fun cleanUp() {
        borutoDatabase.clearAllTables()
    }

    @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        val remoteMediator = HeroRemoteMediator(
            borutoApi,
            borutoDatabase
        )
        val pagingState = PagingState<Int, Hero>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 3),
            leadingPlaceholderCount = 0
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assert(result is RemoteMediator.MediatorResult.Success)
        assert(!((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached))
    }

    @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
    @Test
    fun refreshLoadSuccessAndEndOfPaginationTrueWhenNoMoreData() = runTest {
        borutoApi.clearData()
        val remoteMediator = HeroRemoteMediator(
            borutoApi,
            borutoDatabase
        )
        val pagingState = PagingState<Int, Hero>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 3),
            leadingPlaceholderCount = 0
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assert(result is RemoteMediator.MediatorResult.Success)
        assert(((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached))
    }

    @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runTest {
        borutoApi.addException()
        val remoteMediator = HeroRemoteMediator(
            borutoApi,
            borutoDatabase
        )
        val pagingState = PagingState<Int, Hero>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 3),
            leadingPlaceholderCount = 0
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assert(result is RemoteMediator.MediatorResult.Error)
    }
}