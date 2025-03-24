package com.example.stylefeed.ui

import android.content.pm.ApplicationInfo
import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.stylefeed.FileUtil
import com.example.stylefeed.domain.repository.ProductRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ProductScreenTest {

  @get:Rule(order = 0)
  val hiltRule = HiltAndroidRule(this)

  @Inject lateinit var mockWebServer: MockWebServer
  @Inject lateinit var productRepository: ProductRepository

  @Before
  fun setup() {
    hiltRule.inject()
    println("🔥 MockWebServer URL: ${mockWebServer.url("/")}")
  }

  @Test
  fun testMockServerResponse() = runBlocking {
    val jsonResponse = FileUtil.readTestResource("api-response/success_response.json")
    mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(jsonResponse))

    val result = productRepository.getSections().first()
    println("🔥 API 호출 결과: $result")

    val request = mockWebServer.takeRequest()
    println("🔥 Received Request: ${request.path}")
  }
  @Test
  fun printTestManifestInfo() {
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    val appInfo = appContext.packageManager.getApplicationInfo(
      appContext.packageName,
      0
    )

    val usesCleartextTraffic = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      (appInfo.flags and ApplicationInfo.FLAG_USES_CLEARTEXT_TRAFFIC) != 0
    } else {
      "Not supported below API 28"
    }

    println("🔥 Test Manifest Information 🔥")
    println("Package Name: ${appContext.packageName}")
    println("Cleartext Traffic Enabled: $usesCleartextTraffic")
  }}

