# 1단계: 빌드 및 테스트
FROM gradle:8.4-jdk21 AS builder

WORKDIR /app

COPY settings.gradle settings.gradle
COPY build.gradle build.gradle

COPY common common
COPY domain domain
COPY infrastructure infrastructure
COPY core core
COPY api api

# 테스트 먼저 실행 (테스트 실패 시 빌드 중단)
# RUN gradle clean test --no-daemon --build-cache

# 테스트 통과 시 jar 빌드
RUN gradle :api:bootJar --no-daemon --build-cache


# 2단계: 실행 환경
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/api/build/libs/api-*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

ENTRYPOINT ["java", "-Xms512m", "-Xmx1024m", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
