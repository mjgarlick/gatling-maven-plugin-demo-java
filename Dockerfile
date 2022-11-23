FROM eclipse-temurin:17-jre-alpine
WORKDIR /
COPY target/gatling-test-framework.jar app.jar
COPY start.sh .
RUN chmod +x start.sh
ENTRYPOINT ["./start.sh"]

