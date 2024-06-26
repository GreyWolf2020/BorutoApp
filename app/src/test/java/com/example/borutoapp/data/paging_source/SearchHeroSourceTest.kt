package com.example.borutoapp.data.paging_source

import androidx.paging.PagingSource
import com.example.borutoapp.data.local.paging_source.SearchHeroesSource
import com.example.borutoapp.data.remote.BorutoApi
import com.example.borutoapp.data.remote.FakeRemoteApi
import com.example.borutoapp.domain.model.Hero
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SearchHeroSourceTest {
    private lateinit var borutoApi: BorutoApi
    private lateinit var heroes: List<Hero>
    @Before
    fun setup() {
        borutoApi = FakeRemoteApi()
        heroes = listOf(
            Hero(
                id = 1,
                name = "Sasuke",
                image = "/images/sasuke.jpg",
                about = "Sasuke Uchiha (うちはサスケ, Uchiha Sasuke) is one of the last surviving members of Konohagakure's Uchiha clan. After his older brother, Itachi, slaughtered their clan, Sasuke made it his mission in life to avenge them by killing Itachi. He is added to Team 7 upon becoming a ninja and, through competition with his rival and best friend, Naruto Uzumaki.",
                rating = 5.0,
                power = 98,
                month = "July",
                day = "23rd",
                family = listOf(
                    "Fugaku",
                    "Mikoto",
                    "Itachi",
                    "Sarada",
                    "Sakura"
                ),
                abilities = listOf(
                    "Sharingan",
                    "Rinnegan",
                    "Sussano",
                    "Amateratsu",
                    "Intelligence"
                ),
                natureTypes = listOf(
                    "Lightning",
                    "Fire",
                    "Wind",
                    "Earth",
                    "Water"
                )
            ),
            Hero(
                id = 2,
                name = "Naruto",
                image = "/images/naruto.jpg",
                about = "Naruto Uzumaki (うずまきナルト, Uzumaki Naruto) is a shinobi of Konohagakure's Uzumaki clan. He became the jinchūriki of the Nine-Tails on the day of his birth — a fate that caused him to be shunned by most of Konoha throughout his childhood. After joining Team Kakashi, Naruto worked hard to gain the village's acknowledgement all the while chasing his dream to become Hokage.",
                rating = 5.0,
                power = 98,
                month = "Oct",
                day = "10th",
                family = listOf(
                    "Minato",
                    "Kushina",
                    "Boruto",
                    "Himawari",
                    "Hinata"
                ),
                abilities = listOf(
                    "Rasengan",
                    "Rasen-Shuriken",
                    "Shadow Clone",
                    "Senin Mode"
                ),
                natureTypes = listOf(
                    "Wind",
                    "Earth",
                    "Lava",
                    "Fire"
                )
            ),
            Hero(
                id = 3,
                name = "Sakura",
                image = "/images/sakura.jpg",
                about = "Sakura Uchiha (うちはサクラ, Uchiha Sakura, née Haruno (春野)) is a kunoichi of Konohagakure. When assigned to Team 7, Sakura quickly finds herself ill-prepared for the duties of a shinobi. However, after training under the Sannin Tsunade, she overcomes this, and becomes recognised as one of the greatest medical-nin in the world.",
                rating = 4.5,
                power = 92,
                month = "Mar",
                day = "28th",
                family = listOf(
                    "Kizashi",
                    "Mebuki",
                    "Sarada",
                    "Sasuke"
                ),
                abilities = listOf(
                    "Chakra Control",
                    "Medical Ninjutsu",
                    "Strength",
                    "Intelligence"
                ),
                natureTypes = listOf(
                    "Earth",
                    "Water",
                    "Fire"
                )
            )
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Search api with existing hero name, expect single hero result, assert loadResult_Page`() = runTest {
        val heroSource = SearchHeroesSource(borutoApi, query = "Sasuke")
        val expectedHerosSasuke = listOf(heroes.first())
        assertEquals<PagingSource.LoadResult<Int, Hero>>(
            expected = PagingSource.LoadResult.Page(
                data = expectedHerosSasuke,
                nextKey = null,
                prevKey = null
            ),
            actual = heroSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 3,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Search api with existing hero name, expect multiple heroes result, assert loadResult_Page`() = runTest {
        val heroSource = SearchHeroesSource(borutoApi, query = "Sa")
        val expectedHeroesSasukeSakura = listOf(
            heroes.first(),
            heroes.last()
        )
        assertEquals<PagingSource.LoadResult<Int, Hero>>(
            expected = PagingSource.LoadResult.Page(
                data = expectedHeroesSasukeSakura,
                nextKey = null,
                prevKey = null
            ),
            actual = heroSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 3,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Search api with empty name, assert empy heroes list and loadResult_Page`() = runTest {
        val heroSource = SearchHeroesSource(borutoApi, query = "")
        val expectedHeroesNone = emptyList<Hero>()
        val loadResult = heroSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 3,
                placeholdersEnabled = false
            )
        )

        val result = borutoApi.searchHeroes("").heroes

        assertTrue { result.isEmpty() }
        assertTrue { loadResult is PagingSource.LoadResult.Page }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Search api with unknown name, assert empy heroes list and loadResult_Page`() = runTest {
        val heroSource = SearchHeroesSource(borutoApi, query = "Unkown")
        val expectedHeroesNone = emptyList<Hero>()
        val loadResult = heroSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 3,
                placeholdersEnabled = false
            )
        )

        val result = borutoApi.searchHeroes("Unknown").heroes

        assertTrue { result.isEmpty() }
        assertTrue { loadResult is PagingSource.LoadResult.Page }

    }

}