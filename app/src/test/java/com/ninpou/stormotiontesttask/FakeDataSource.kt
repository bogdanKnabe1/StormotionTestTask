package com.ninpou.stormotiontesttask

import com.ninpou.stormotiontesttask.data.network.OperationResult
import com.ninpou.stormotiontesttask.model.Data
import com.ninpou.stormotiontesttask.model.DataVideo
import com.ninpou.stormotiontesttask.model.SuggestionItemDataSourceI

class FakeDataSource : SuggestionItemDataSourceI {

    private val mockListData: MutableList<Data> = mutableListOf()
    private val mockListVideo: MutableList<DataVideo> = mutableListOf()

    init {
        mockData()
    }

    private fun mockData() {
        mockListData.add(
            Data(
                "HEADER--1",
                1,
                "https://avatars.githubusercontent.com/u/14220132?s=460&u=b4c889240d86a471ecf760c4b73463c0194e9392&v=4",
                "subbtitle",
                "Title",
                1
            )
        )
        mockListData.add(
            Data(
                "HEADER--2",
                2,
                "https://avatars.githubusercontent.com/u/14220132?s=460&u=b4c889240d86a471ecf760c4b73463c0194e9392&v=4",
                "subbtitle",
                "Title",
                2
            )
        )
        mockListData.add(
            Data(
                "HEADER--3",
                3,
                "https://avatars.githubusercontent.com/u/14220132?s=460&u=b4c889240d86a471ecf760c4b73463c0194e9392&v=4",
                "subbtitle",
                "Title",
                2
            )
        )

        mockListVideo.add(
            DataVideo(
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                "something in description",
            )
        )
    }

    override suspend fun retrieveData(): OperationResult<Data> {
        return OperationResult.Success(mockListData)
    }

    override suspend fun retrieveVideoData(): OperationResult<DataVideo> {
        return OperationResult.Success(mockListVideo)
    }
}