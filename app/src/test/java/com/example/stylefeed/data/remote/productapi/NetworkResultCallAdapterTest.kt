import com.example.stylefeed.data.remote.dto.MockResponseDto
import com.example.stylefeed.utils.NetworkResult
import com.example.stylefeed.utils.NetworkResultCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okio.Timeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.io.IOException


@OptIn(ExperimentalSerializationApi::class)
class NetworkResultCallAdapterTest {

    // Retrofit 설정 (JSON Converter 명시)
    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://mock.api/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .addCallAdapterFactory(NetworkResultCallAdapterFactory())
        .build()

    private val adapter = NetworkResultCallAdapterFactory()
        .get(
            MockApi::class.java.getMethod("getMockData").genericReturnType,
            arrayOf(),
            retrofit
        ) as CallAdapter<MockResponseDto, Flow<NetworkResult<MockResponseDto>>>

    @Test
    fun `성공 응답을 Flow로 변환`() = runTest {
        // 가짜 성공 응답을 위한 Call 객체
        val mockResponseDto = MockResponseDto("성공한 데이터")
        val mockCall = object : Call<MockResponseDto> {
            override fun execute() = retrofit2.Response.success(mockResponseDto)
            override fun clone(): Call<MockResponseDto> = this
            override fun enqueue(callback: retrofit2.Callback<MockResponseDto>) {}
            override fun isExecuted(): Boolean = false
            override fun cancel() {}
            override fun isCanceled(): Boolean = false
            override fun request(): okhttp3.Request = okhttp3.Request.Builder().url("https://mock.api/mock").build()
            override fun timeout(): Timeout {
                return Timeout.NONE
            }

        }

        val result = adapter.adapt(mockCall).first()

        assertTrue(result is NetworkResult.Success)
        assertEquals(mockResponseDto, (result as NetworkResult.Success).data)
    }

    @Test
    fun `API 에러 응답을 Flow로 변환`() = runTest {
        val errorBody = ResponseBody.create("text/plain".toMediaType(), "서버 에러")
        val mockCall = object : Call<MockResponseDto> {
            override fun execute() = retrofit2.Response.error<MockResponseDto>(500, errorBody)
            override fun clone(): Call<MockResponseDto> = this
            override fun enqueue(callback: retrofit2.Callback<MockResponseDto>) {}
            override fun isExecuted(): Boolean = false
            override fun cancel() {}
            override fun isCanceled(): Boolean = false
            override fun request(): okhttp3.Request = okhttp3.Request.Builder().url("https://mock.api/mock").build()
            override fun timeout(): Timeout {
                return Timeout.NONE
            }
        }

        val result = adapter.adapt(mockCall).first()

        assertTrue(result is NetworkResult.Error)
        assertEquals(500, (result as NetworkResult.Error).code)
        assertEquals("서버 에러", result.message)
    }

    @Test
    fun `네트워크 예외(IOException)를 Flow로 변환`() = runTest {
        val mockCall = object : Call<MockResponseDto> {
            override fun execute(): retrofit2.Response<MockResponseDto> {
                throw IOException("네트워크 연결 오류")
            }
            override fun clone(): Call<MockResponseDto> = this
            override fun enqueue(callback: retrofit2.Callback<MockResponseDto>) {}
            override fun isExecuted(): Boolean = false
            override fun cancel() {}
            override fun isCanceled(): Boolean = false
            override fun request(): okhttp3.Request = okhttp3.Request.Builder().url("https://mock.api/mock").build()
            override fun timeout(): Timeout {
                return Timeout.NONE
            }
        }

        val result = adapter.adapt(mockCall).first()

        assertTrue(result is NetworkResult.NetworkError)
    }
}