package com.example.myfootballmatchschedule.presenter

import com.example.myfootballmatchschedule.Connection.ApiRepository
import com.example.myfootballmatchschedule.Connection.ApiUrl
import com.example.myfootballmatchschedule.CoroutineContextProviderTest
import com.example.myfootballmatchschedule.ModelData.MatchResponse
import com.example.myfootballmatchschedule.ModelData.ModelMatch
import com.example.myfootballmatchschedule.TabMatch.MatchView
import com.example.myfootballmatchschedule.TabMatch.NextMatch.NextMatchPresenter
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class NextMatchPresenterTest {
    private lateinit var presenter: NextMatchPresenter
    private val contex: CoroutineContextProviderTest = CoroutineContextProviderTest()

    @Mock
    private lateinit var view: MatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = NextMatchPresenter(
            view,
            apiRepository,
            gson,
            1,
            CoroutineContextProviderTest()
        )
    }

    @Test
    fun getMatchList() {
        val matchs: MutableList<ModelMatch> = mutableListOf()
        val match: MutableList<ModelMatch> = mutableListOf()
        val response = MatchResponse(matchs, match)
        val id = "441613"

        GlobalScope.launch(contex.main) {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository.doRequest(ApiUrl.getNextMatch(id)).await(),
                    MatchResponse::class.java
                )
            ).thenReturn(response)
            presenter.getNextMatchpresenter(id)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatchList(matchs)
            Mockito.verify(view).hideLoading()
        }
    }
}
