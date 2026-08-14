# java 21 jdk가 설치된 공식이미지를 기반
FROM eclipse-temurin:21-jdk AS builder

# 작업 디렉토리를 /app으로 설정, 이후 명령어는 모두 이 폴더 기준
WORKDIR /app

# 소스 코드 올리기전 의존성 관련 파일 복사 (gradlew, 설정 파일)
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Gradle 실행 스크립트에 실행 권한 부여
RUN chmod +x gradlew

# 소스코드 복사
COPY src src

# Gradle을 이용해 프로젝트 빌드, --no-daemon으로 불필요한 백그라운드 프로세스가 남지않게 함
RUN ./gradlew clean bootJar --no-daemon

# jdk말고 jre 기반 이미지를 불러옴 컴파일러, 도구 등이 빠져있어 이미지 크기가 대폭 작아짐
FROM eclipse-temurin:21-jre

WORKDIR /app

# 이전 builder 단계의 /app/build/libs/ 폴더에 생성된 jar 파일을 app.jar로 복사
# 소스코드나 빌드도구느는 이 시점에 모두 사라짐
COPY --from=builder /app/build/libs/*.jar app.jar

# 해당 컨테이너가 이 포트를 사용함을 명시적(문서화 용도)로 알림
EXPOSE 8080

# 컨테이너가 시작할 때 이 명령어들을 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
