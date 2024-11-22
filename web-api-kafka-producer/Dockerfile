# Create base container image
FROM openjdk:22-jdk AS builder
ARG INPUT_JAR_FILE=target/web-api-kafka-producer.jar
ARG FINAL_JAR_FILE=web-api-kafka-producer.jar
COPY ${INPUT_JAR_FILE} ${FINAL_JAR_FILE}
RUN java -Djarmode=layertools -jar ${FINAL_JAR_FILE} extract

# Create layered main docker container image from base container
FROM openjdk:22-jdk
# Expose port 8080, used when this container is run using `docker run`
EXPOSE 8080
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
RUN true
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]