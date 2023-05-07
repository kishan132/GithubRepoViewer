package com.example.githubrepoviewer

import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun testResponse() {

        runBlocking {
            val response = GithubClient.api.getPublicRepoList("kishan")

            assertNotNull(response.body())

        }

    }

}