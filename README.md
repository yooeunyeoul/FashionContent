# 📱 FashionContent Android App

아래는 프로젝트의 전반적인 구현 내용과 기술 요구 사항, 그리고 UX적인 고민과 테스트 코드 작성 현황을 상세히 설명한 문서입니다.

<br>

## 🚩 기술 명세 및 충족 현황

| 요구사항                 | 적용 여부 | 상세 내용                  |
|----------------------|-------|------------------------|
| **Kotlin 사용**          | ✅     | Kotlin을 사용하여 구현했습니다. |
| **MVVM or MVI**      | ✅| Airbnb Mavericks를 활용하여 MVI 패턴으로 구현했습니다. |
| **Jetpack Compose**  | ✅     | 전체 화면을 Jetpack Compose로 구현했습니다. |
| **Coroutine (비동기)** | ✅     | 모든 비동기 처리를 Coroutine으로 처리했습니다. |
| **단위 테스트 (Optional)**  | ✅     | 핵심 로직에 대한 단위 테스트를 작성했습니다. |

<br>

## 🛠️ IDE 및 빌드 환경
- **IDE**: Android Studio Ladybug | 2024.2.1
- **Android Gradle Plugin (AGP)**: 8.5.2
- **Gradle JDK**: JetBrains Runtime 21.0.4
- **Kotlin 버전**: 2.0.0
- **Compile SDK**: 35
- **Min SDK**: 28
- **Target SDK**: 35

<br>

## 📌 기능 명세 및 구현 내용

- 다양한 콘텐츠(배너, 상품, 스타일)를 한 화면에서 리스트 형태로 제공합니다.
- 서버 응답에 따라 콘텐츠의 순서와 형태가 동적으로 달라지게 구현했습니다.
- 콘텐츠 순서 변경을 Compose Preview로 손쉽게 테스트할 수 있도록 구성했습니다.

```kotlin
@Preview(showBackground = true)
@Composable
fun SectionsListPreview() {
    val listState = rememberLazyListState()
    val sectionHeights = remember { mutableStateMapOf<Int, Float>() }
    FashionContentTheme {
        SectionsList(
            sectionStates = PreviewData.allSections.shuffled(), // 🔁 순서 바꿔서 테스트
            isVisible = true,
            listState = listState,
            sectionHeights = sectionHeights,
            recentlyAddedIds = emptySet(),
            sectionLoadingMap = mapOf(0 to false, 1 to true), // 로딩 여부 섞어보기
            onFooterClick = { _, _, _ -> }
        )
    }
}
```

<br>

## 🎨 디자인 시스템 구축 및 컴포넌트 관리

- **디자인 시스템**을 구축하여 재사용 가능한 공통 컴포넌트와 특정 화면(Product)에 종속된 UI 컴포넌트를 명확히 분리하여 관리했습니다.
- 모든 UI 컴포넌트는 개별적으로 **Preview**를 통해 쉽게 확인할 수 있도록 구성했습니다.
  
<br>

## 💡 유저 경험(UX)을 위한 고민
### 무한 스크롤 배너 구현
- 배너는 무한 스크롤이 가능하도록 구현했습니다.
- 사용자가 배너 영역을 보지 않을 때는 스크롤이 멈추고, 다시 진입할 때 스크롤을 재개합니다.

### 더보기 확장 시 자동 스크롤 지원
- '더보기' 클릭으로 확장된 영역이 현재 화면 크기를 초과하면, 자동으로 스크롤하여 확장된 영역을 사용자에게 즉시 보여줍니다.

### 새로운 추천 알림 스낵바 제공
- "새로운 추천" 버튼 클릭 시 간단한 스낵바를 통해 사용자가 콘텐츠가 업데이트되었음을 명확히 인지할 수 있도록 했습니다.

### Sticky Header 구현
- 콘텐츠 영역이 길어지더라도 상단 타이틀을 Sticky Header로 고정하여 사용자가 항상 콘텐츠의 유형을 확인할 수 있도록 구성했습니다.

<br>

## ✅ 단위 테스트 작성 현황 (Test Coverage)

아래의 핵심 영역에 대한 단위 테스트를 작성했습니다:

- **API 레이어 (`NetworkResult`)**
  - Retrofit 응답을 성공, 서버 에러(500), 네트워크 에러(IOException)로 구분하여 테스트했습니다.

- **Repository 레이어 (`ProductRepositoryImpl`)**
  - 서버에서 콘텐츠 타입과 순서를 임의로 변경하여 내려주어도 올바르게 매핑하는지 테스트했습니다.

- **UseCase 레이어 (`GetSectionsUseCase`)**
  - 각 콘텐츠 유형별로 정확한 visible item count와 total item count를 계산하는 비즈니스 로직을 테스트했습니다.

- **ViewModel 레이어 (`ProductViewModel`)**
  - UI 이벤트(footer 클릭, refresh 클릭)에 대한 처리를 테스트했습니다.
  - UI 상태 업데이트 및 Side Effect가 올바르게 작동하는지 검증했습니다.

 <br>
  
## 🗃️ 프로젝트 구조 (Architecture)

```
app
├── data
│   ├── remote (API, DTO, Mapper)
│   └── repository (RepositoryImpl)
├── domain
│   ├── model (Domain Models)
│   ├── repository (Repository Interface)
│   └── usecase (UseCases)
├── ui
│   └── screens
│       └── product (ViewModel, UI Components, Interactions)
├── designsystem (재사용 가능한 공통 UI 컴포넌트 및 테마)
├── utils (확장 함수 및 공통 유틸리티)
└── test (단위 테스트 코드)
```

<br>


## ⚙️ 주요 사용 기술 및 라이브러리
- Language : Kotlin
- Architecture : MVI (Airbnb Mavericks)
- UI Framework : Jetpack Compose
- Asynchronous : Coroutine, Flow
- DI : Hilt
- Network : Retrofit2, Kotlin Serialization
- Testing : JUnit, MockK, Coroutines Test, Turbine


