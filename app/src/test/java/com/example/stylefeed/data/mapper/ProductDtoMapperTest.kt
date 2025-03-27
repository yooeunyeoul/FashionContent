import com.example.stylefeed.data.remote.productapi.dto.RootDto
import com.example.stylefeed.data.remote.productapi.service.ProductApiService
import com.example.stylefeed.data.repository.ProductRepositoryImpl
import com.example.stylefeed.domain.model.Content
import com.example.stylefeed.domain.repository.ProductRepository
import com.example.stylefeed.utils.NetworkResult
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.File

class ProductRepositoryImplTest {

    private val json = Json { ignoreUnknownKeys = true }

    private lateinit var apiService: ProductApiService
    private lateinit var productRepository: ProductRepository

    @Before
    fun setup() {
        apiService = mockk()
        productRepository = ProductRepositoryImpl(apiService)
    }

    private fun loadJsonResponse(fileName: String): RootDto {
        val jsonResponse = File("src/test/resources/api-response/$fileName").readText()
        return json.decodeFromString<RootDto>(jsonResponse)
    }

    @Test
    fun `기본 순서대로 데이터 변환이 올바른지 테스트`() = runTest {
        val dtoResponse = loadJsonResponse("success_response_default_order.json")

        coEvery { apiService.fetchSections() } returns flowOf(NetworkResult.Success(dtoResponse))

        val result = productRepository.getSections().first()

        assertEquals(4, result.size)
        assertTrue(result[0].content is Content.BannerContent)
        assertTrue(result[1].content is Content.GridContent)
        assertTrue(result[2].content is Content.ScrollContent)
        assertTrue(result[3].content is Content.StyleContent)
    }

    @Test
    fun `변경된 순서대로 데이터 변환이 올바른지 테스트`() = runTest {
        val dtoResponse = loadJsonResponse("success_response_changed_order.json")

        coEvery { apiService.fetchSections() } returns flowOf(NetworkResult.Success(dtoResponse))

        val result = productRepository.getSections().first()

        assertEquals(4, result.size)
        assertTrue(result[0].content is Content.StyleContent)
        assertTrue(result[1].content is Content.BannerContent)
        assertTrue(result[2].content is Content.ScrollContent)
        assertTrue(result[3].content is Content.GridContent)
    }
}