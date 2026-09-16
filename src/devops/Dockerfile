# ==========================================
# STAGE 1: Build Environment
# ==========================================
FROM maven:3.8.5-openjdk-17-slim AS builder

WORKDIR /build

# Copy pom.xml first to leverage Docker layer caching
COPY pom.xml .
# Downloader dependencies (cached unless pom.xml changes)
RUN mvn dependency:go-offline -B

# Copy source code and build package
COPY src ./src
RUN mvn package -DskipTests -B

# ==========================================
# STAGE 2: Lightweight Runtime Environment
# ==========================================
FROM eclipse-temurin:17-jre-alpine

# 1. Define metadata and working directory
LABEL maintainer="infra-team@aivar.io"
WORKDIR /app

# 2. Add a non-privileged system user/group to run the application
# This prevents potential container escape attacks by restricting privileges.
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

# 3. Copy only the compiled JAR from stage 1
COPY --from=builder /build/target/*.jar app.jar

# 4. Set secure runtime JVM configurations
# -XX:+UseContainerSupport makes JVM aware of CPU and memory limits set by Docker/K8s.
# -XX:MaxRAMPercentage prevents OOM by allocating a safe percentage of container RAM.
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+ExitOnOutOfMemoryError -Djava.security.egd=file:/dev/./urandom"

# 5. Document secure runtime configuration patterns
# DO NOT hardcode secret keys in environment properties.
# Options for injecting secrets securely in AWS / Kubernetes:
#
#   Option A: AWS ECS / EKS Secrets Mount
#     Mount AWS Secrets Manager entries as files into the container. Spring reads them via:
#     -Dspring.config.import=optional:configtree:/vault/secrets/
#
#   Option B: Secure Environment Variable Injection
#     Inject values into environment variables at deploy-time using AWS ECS Parameter Store/Secrets,
#     referencing them in spring application.yaml via placeholder properties (e.g., ${DB_PASSWORD}).

EXPOSE 8080

# 6. Configure health check probe
HEALTHCHECK --interval=30s --timeout=5s --start-period=40s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# 7. Start the microservice
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
