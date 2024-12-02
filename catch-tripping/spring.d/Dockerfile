FROM eclipse-temurin:17-jre-alpine

# 컨테이너에서 8080 포트를 외부로 노출
EXPOSE 8080

# /tmp 디렉토리를 볼륨으로 설정
VOLUME /tmp

# 컨테이너 내에서 작업할 디렉토리 설정
WORKDIR /app

# 로컬에서 빌드된 JAR 파일을 /app 디렉토리로 복사
COPY ./*.jar /app/

# .jar 파일을 찾고, 없으면 에러 메시지 출력하고 빌드 실패
RUN JAR_FILE=$(find /app -name '*.jar' -print -quit) && \
    if [ -z "$JAR_FILE" ]; then \
        echo "No JAR file found!" && exit 1; \
    fi

# JAR 파일을 실행하는 ENTRYPOINT
ENTRYPOINT ["sh", "-c", "java -jar $(find /app -name '*.jar' -print -quit)"]