package com.example.stylefeed.utils

import com.example.stylefeed.domain.model.ApiError
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class FlowNetworkResultExtensionTest {

    @Test
    fun `Success일 때 transform이 잘 적용된다`() = runTest {
        val flow = flowOf(NetworkResult.Success("data"))
        val result = flow.mapResult { it.uppercase() }.first()
        assertEquals("DATA", result)
    }

    @Test
    fun `Error일 때 ApiException이 throw 된다`() = runTest {
        val flow = flowOf(NetworkResult.Error(500, "서버 에러"))

        try {
            flow.mapResult { it }.first()
            fail("예외가 발생해야 합니다")
        } catch (e: ApiException) {
            assertEquals(500, e.code)
            assertEquals("서버 에러", e.message)
        }
    }

    @Test
    fun `NetworkError일 때 ApiException이 throw 된다`() = runTest {
        val flow = flowOf(NetworkResult.NetworkError)

        try {
            flow.mapResult { it }.first()
            fail("예외가 발생해야 합니다")
        } catch (e: ApiException) {
            assertEquals(ApiError.NETWORK_ERROR_CODE, e.code)
            assertEquals("네트워크 연결 오류", e.message)
        }
    }
}
