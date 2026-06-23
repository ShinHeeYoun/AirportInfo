# AirportInfo

세계 공항 정보를 조회하고 데이터를 시각화할 수 있는 Java Swing 기반 데스크톱 애플리케이션입니다. 
공공데이터포털의 [국토교통부 세계 공항 정보](https://www.data.go.kr/data/3051587/fileData.do) 데이터를 기반으로 합니다.

## 주요 기능 (Features)
- **공항 정보 조회 및 검색**: 국가, 공항명, IATA/ICAO 코드를 통해 전 세계 공항을 빠르게 검색할 수 있습니다.
- **공항 상세 정보 제공**: 선택한 공항의 상세 정보(지역, 영문/국문 국가명, 도시명 등)를 확인할 수 있습니다.
- **차트 및 통계 시각화**: 지역별, 국가별 공항 수 분포를 원형 차트(Pie Chart) 및 히스토그램으로 한눈에 파악할 수 있습니다.
- **데이터 내보내기**: 검색된 데이터나 차트 결과를 파일(CSV, JSON 등)로 저장하고 이메일로 전송할 수 있습니다.
- **다국어 및 테마 지원**: 한국어와 영어 인터페이스를 지원하며, 화면 테마(Lite 등)를 변경할 수 있습니다.

## 기술 스택 (Tech Stack)
- **Language**: Java
- **GUI Framework**: Java Swing (IntelliJ IDEA GUI Designer)
- **Database**: MySQL (`mysql-connector-java`) - 원격 DB 연동
- **Libraries**:
    - UI 디자인: `material-ui-swing`
    - 이메일 발송: `javax.mail`
    - 데이터/문서 처리: `jsoup`, `json`

## 실행 방법 (How to Run)

본 프로젝트는 IntelliJ IDEA 환경에서 개발되었으며 빌드 도구(Maven/Gradle 등) 대신 자체 `lib` 폴더를 참조하고 있습니다.

1. **프로젝트 열기**:
   - IntelliJ IDEA를 실행하고 `Open` 메뉴를 통해 `AirportInfo` 폴더를 엽니다.
2. **라이브러리 추가**:
   - `File > Project Structure > Modules > Dependencies` 로 이동합니다.
   - `+` 버튼을 누르고 `JARs or Directories`를 선택한 후, 프로젝트 내의 `lib` 폴더에 있는 `.jar` 파일들을 모두 선택하여 프로젝트 라이브러리로 추가합니다.
3. **애플리케이션 실행**:
   - `src/main/java/com/airportinfo/Main.java` 파일을 열고, `main` 메서드의 왼쪽 여백에 있는 실행(▶) 버튼을 클릭하여 프로그램을 시작합니다.
   - *참고: DB 연결 정보가 `DBManager.java`에 하드코딩되어 있으므로 별도의 로컬 DB 설정 없이 즉시 실행 가능합니다.*

## 원본 데이터 출처
- 공공데이터포털 | 국토교통부_세계공항_정보
- 링크: https://www.data.go.kr/data/3051587/fileData.do (UTF-8)

## 트러블 슈팅 (Troubleshooting)
- **"데이터를 로드할 수 없습니다" 에러 해결**
  - 원인: `airport_data.csv` 파일의 인코딩(EUC-KR)과 자바 내부에서 파일을 읽어들이는 인코딩이 맞지 않아 발생했습니다.
  - 조치: CSV 파일의 인코딩을 UTF-8로 변환하여 데이터를 정상적으로 로드하도록 수정했습니다.
- **위키피디아 정보 로드 실패 및 에러창 발생 ("위키피디아에서 정보를 불러올 수 없습니다" / 403 Forbidden 에러)**
  - 원인: 위키백과 크롤링 봇 차단 정책으로 인해 `Jsoup` 및 `ImageIO` 요청 시 브라우저 식별(User-Agent) 헤더가 없어 403 Forbidden 차단이 발생했습니다.
  - 조치: `AirportWikiCrawler.java` 파일 내 위키백과 본문(Jsoup) 및 사진 다운로드(ImageIO) 연결부에 범용 브라우저의 `User-Agent` 문자열을 추가하여 차단을 우회했습니다.
- **위키피디아 정보 "정보가 없습니다" (Not Found) 에러 해결**
  - 원인 1: 한국어 윈도우 환경에서 Java의 `Locale.getDefault().toString()`을 바로 사용하면 `ko_KR`이 반환되어, 존재하지 않는 `ko_KR.wikipedia.org`로 접속을 시도하는 문제가 있었습니다.
  - 원인 2: 검색할 공항 이름과 IATA 코드 데이터 내부에 띄어쓰기(공백)가 포함되어 있어, 위키백과 본문 텍스트와 정확하게 일치하지 않아 검색에 실패했습니다.
  - 원인 3: 본문 텍스트를 찾는 CSS 선택자가 너무 엄격하거나 위키백과 표(table) 태그 내의 캡션을 본문으로 착각하는 문제가 있었습니다.
  - 조치: 
    - Locale에서 `.getLanguage()`를 명시적으로 호출하여 `ko.wikipedia.org` 등 올바른 언어별 서브도메인을 가리키도록 수정했습니다.
    - 검색 텍스트 비교 시 `.trim()`을 적용하여 공백 문제를 해결했습니다.
    - 불필요한 사진 캡션을 본문으로 오인하는 문제를 막기 위해 추출된 `<p>` 태그 텍스트의 길이를 기준으로(50자 초과) 검증하여, 실제 상세한 정보가 담긴 위키백과 본문 문단만 안정적으로 가져오도록 필터링을 개선했습니다.
