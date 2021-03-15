package com.ninpou.stormotiontesttask

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.emedinaa.kotlinmvvm.FakeDataSource
import com.emedinaa.kotlinmvvm.FakeEmptyDataSource
import com.emedinaa.kotlinmvvm.FakeErrorDataSource
import com.ninpou.stormotiontesttask.data.network.OperationResult
import com.ninpou.stormotiontesttask.model.Data
import com.ninpou.stormotiontesttask.model.SuggestionItemRepository
import com.ninpou.stormotiontesttask.viewmodel.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class MVVMUnitTest {

    @Mock
    private lateinit var context: Application

    private lateinit var viewModel: SharedViewModel

    private lateinit var isViewLoadingObserver: Observer<Boolean>
    private lateinit var onMessageErrorObserver: Observer<Any>
    private lateinit var emptyListObserver: Observer<Boolean>
    private lateinit var onRenderDataObserver: Observer<List<Data>>

    private lateinit var museumEmptyList: List<Data>
    private lateinit var museumList: List<Data>

    private val fakeMuseumDataSource = FakeDataSource()
    private val fakeEmptyMuseumDataSource = FakeEmptyDataSource()
    private val fakeErrorMuseumDataSource = FakeErrorDataSource()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`(context.applicationContext).thenReturn(context)
        Dispatchers.setMain(testDispatcher)

        mockData()
        setupObservers()
    }

    @ExperimentalCoroutinesApi
    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `retrieve museums with ViewModel and Repository returns empty data`() {
        viewModel = SharedViewModel(SuggestionItemRepository(fakeEmptyMuseumDataSource), context)

        with(viewModel) {
            loadData()
            isViewLoading.observeForever(isViewLoadingObserver)
            isEmptyList.observeForever(emptyListObserver)
            suggestionListData.observeForever(onRenderDataObserver)
        }

        runBlockingTest {
            val response = fakeEmptyMuseumDataSource.retrieveData()
            Assert.assertTrue(response is OperationResult.Success)
            Assert.assertNotNull(viewModel.isViewLoading.value)
            Assert.assertTrue(viewModel.isEmptyList.value == true)
            Assert.assertTrue(viewModel.suggestionListData.value?.size == 0)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `retrieve museums with ViewModel and Repository returns full data`() {
        viewModel = SharedViewModel(SuggestionItemRepository(fakeMuseumDataSource), context)

        with(viewModel) {
            loadData()
            isViewLoading.observeForever(isViewLoadingObserver)
            suggestionListData.observeForever(onRenderDataObserver)
        }

        runBlockingTest {
            val response = fakeMuseumDataSource.retrieveData()
            Assert.assertTrue(response is OperationResult.Success)
            Assert.assertNotNull(viewModel.isViewLoading.value)
            Assert.assertTrue(viewModel.suggestionListData.value?.size == 3)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `retrieve museums with ViewModel and Repository returns an error`() {
        viewModel = SharedViewModel(SuggestionItemRepository(fakeErrorMuseumDataSource), context)
        with(viewModel) {
            loadData()
            isViewLoading.observeForever(isViewLoadingObserver)
            isEmptyList.observeForever(emptyListObserver)
            suggestionListData.observeForever(onRenderDataObserver)
        }

        runBlockingTest {
            val response = fakeErrorMuseumDataSource.retrieveData()
            Assert.assertTrue(response is OperationResult.Error)
            Assert.assertNotNull(viewModel.isViewLoading.value)
            Assert.assertNotNull(viewModel.onMessageError.value)
        }
    }

    private fun setupObservers() {
        isViewLoadingObserver = mock(Observer::class.java) as Observer<Boolean>
        onMessageErrorObserver = mock(Observer::class.java) as Observer<Any>
        emptyListObserver = mock(Observer::class.java) as Observer<Boolean>
        onRenderDataObserver = mock(Observer::class.java) as Observer<List<Data>>
    }

    private fun mockData() {
        museumEmptyList = emptyList()
        val mockList: MutableList<Data> = mutableListOf()
        mockList.add(
            Data(
                "HEADER--4",
                4,
                "https://avatars.githubusercontent.com/u/14220132?s=460&u=b4c889240d86a471ecf760c4b73463c0194e9392&v=4",
                "subbtitle",
                "Title",
                1
            )
        )
        mockList.add(
            Data(
                "HEADER--32",
                3,
                "https://avatars.githubusercontent.com/u/14220132?s=460&u=b4c889240d86a471ecf760c4b73463c0194e9392&v=4",
                "subbtitle",
                "Title",
                2
            )
        )
        mockList.add(
            Data(
                "HEADER--53",
                3,
                "https://avatars.githubusercontent.com/u/14220132?s=460&u=b4c889240d86a471ecf760c4b73463c0194e9392&v=4",
                "subbtitle",
                "Title",
                2
            )
        )

        museumList = mockList.toList()
    }
}