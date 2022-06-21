package com.example.lbctechnicaltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.lbctechnicaltest.api.TrackService
import com.example.lbctechnicaltest.database.*
import com.example.lbctechnicaltest.models.Track
import com.example.lbctechnicaltest.models.utils.NetworkState
import com.example.lbctechnicaltest.utils.TrampolineSchedulerProvider
import com.example.lbctechnicaltest.viewmodels.AlbumListViewModel
import io.reactivex.Single
import org.junit.Assert
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.*
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class AlbumListViewModelTest {
    @get:Rule
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<NetworkState>
    private val serviceMock = mock(TrackService::class.java)
    private val databaseMock = mock(AppDatabase::class.java)

    private lateinit var viewModel: AlbumListViewModel

    @Before
    @Throws(Exception::class)
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = AlbumListViewModel(databaseMock, serviceMock, TrampolineSchedulerProvider())
        viewModel.networkState.observeForever(observer)
    }

    @Test
    fun response_null_test() {
        `when`(serviceMock.getAllTracks()).thenReturn(null)

        Assert.assertNull(viewModel.albums.value)
        Assert.assertTrue(viewModel.networkState.hasObservers())
    }

    @Test
    fun api_fetch_data_success_test() {
        // Mock API response
        val response = listOf<Track>()

        `when`(serviceMock.getAllTracks()).thenReturn(Single.just(response))
        viewModel.getAlbums()
        verify(observer)?.onChanged(NetworkState.PENDING)
        verify(observer)?.onChanged(NetworkState.SUCCESS)
    }

    @Test
    fun api_fetch_data_error_test() {
        `when`(serviceMock.getAllTracks()).thenReturn(
            Single.error(Throwable("Api error -- TEST"))
        )
        viewModel.getAlbums()
        verify(observer)?.onChanged(NetworkState.PENDING)
        verify(observer)?.onChanged(NetworkState.ERROR)
    }
}