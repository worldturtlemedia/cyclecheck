package com.worldturtlemedia.cyclecheck.data.api.util

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isTrue
import com.worldturtlemedia.cyclecheck.core.network.APIResult
import com.worldturtlemedia.cyclecheck.core.network.Mapper
import com.worldturtlemedia.cyclecheck.core.network.dataOrThrow
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class DeferredResponseKtxTest {

    @Test
    fun `awaitResponse should call transform on a successful request`() = runBlocking {
        val response = CompletableDeferred(Response.success(42))

        val mockTransform = mockk<(Response<Int>) -> APIResult<Int>>()
        response.awaitResponse(mockTransform)

        val arg = slot<Response<Int>>()
        verify { mockTransform.invoke(capture(arg)) }
        assertThat(arg.captured.body()).isEqualTo(42)
    }

    @Test
    fun `awaitResponse should catch any error and return an error Result`() = runBlocking {
        val mockResponse = mockk<Deferred<Response<Int>>> {
            coEvery { await() } throws HttpException(Response.error<Int>(404, mockk()))
        }

        val mockTransform = mockk<(Response<Int>) -> APIResult<Int>>()
        val result = mockResponse.awaitResponse(mockTransform)

        coVerify(exactly = 0) { mockTransform.invoke(any()) }
        assertThat(result is APIResult.Error).isTrue()
    }

    @Test
    fun `awaitResult should catch any error and wrap it in a Result`() = runBlocking {
        val mockResponse = mockk<Deferred<Response<Int>>> {
            coEvery { await() } returns Response.error<Int>(404, mockk())
        }

        val mockTransform = mockk<(Int) -> Int>()
        val result = mockResponse.awaitResult(mockTransform) as APIResult.Error

        assertThat(result.exception).isInstanceOf(HttpException::class)
        coVerify(exactly = 0) { mockTransform.invoke(any()) }
    }

    @Test
    fun `awaitResult should handle a successful response with an empty body`() = runBlocking {
        val mockResponse = mockk<Deferred<Response<Int>>> {
            coEvery { await() } returns Response.success<Int>(null)
        }

        val mockTransform = mockk<(Int) -> Int>()
        val result = mockResponse.awaitResult(mockTransform) as APIResult.Error

        assertThat(result.exception).isInstanceOf(NullPointerException::class)
        coVerify(exactly = 0) { mockTransform.invoke(any()) }
    }

    @Test
    fun `awaitResult should call transform if the request is successful and has a body`() =
        runBlocking {
            val mockResponse = mockk<Deferred<Response<Int>>> {
                coEvery { await() } returns Response.success<Int>(42)
            }

            val mockTransform = mockk<(Int) -> Int>()
            val arg = slot<Int>()

            mockResponse.awaitResult(mockTransform)
            coVerify { mockTransform.invoke(capture(arg)) }
            assertThat(arg.captured).isEqualTo(42)
        }

    @Test
    fun `no-arg awaitResult should return the response body wrapped in a Result`() = runBlocking {
        val mockResponse = mockk<Deferred<Response<Int>>> {
            coEvery { await() } returns Response.success<Int>(42)
        }

        val result = mockResponse.awaitResult().dataOrThrow()
        assertThat(result).isEqualTo(42)
    }

    data class SampleMapper(val value: Int) : Mapper<String> {

        override fun map(): String = value.toString()
    }

    @Test
    fun `awaitMappedResult should call Mapper_map on a successful request`() = runBlocking {
        val mockResponse = mockk<Deferred<Response<SampleMapper>>> {
            coEvery { await() } returns Response.success<SampleMapper>(SampleMapper(42))
        }

        val result = mockResponse.awaitMappedResult() as APIResult.Success
        assertThat(result.data.toInt()).isEqualTo(42)
    }
}
