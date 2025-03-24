import com.example.stylefeed.data.remote.dto.MockResponseDto
import com.example.stylefeed.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

// 테스트용 API 인터페이스 (suspend 아님, Flow + NetworkResultCallAdapterFactory로 처리)
interface MockApi {
    @GET("mock")
    fun getMockData(): Flow<NetworkResult<MockResponseDto>>
}
