FROM node:latest AS frontend-build
WORKDIR /usr/local/app
COPY ./front/ /usr/local/app/
RUN npm install -g @angular/cli && npm install
RUN ng build --configuration=production

# Debug output
RUN echo "=== Angular build completed ===" && \
  ls -la /usr/local/app/dist/front/ && \
  echo "=== Checking for index.html ===" && \
  test -f /usr/local/app/dist/front/index.html && echo "✓ index.html found" || echo "✗ index.html NOT found"

FROM maven:3.9.9-ibm-semeru-23-jammy AS backend-build
WORKDIR /workspace
COPY ./back/pom.xml /workspace
COPY ./back/src /workspace/src
RUN mvn -B -f pom.xml clean package -DskipTests

FROM openjdk:25-ea-23-nanoserver AS final-app-stage
# If ibm-semeru-runtime is not readily available or preferred, you can also use:
# FROM openjdk:23-jre-slim-bullseye (or a jammy variant if you find one)
# FROM eclipse-temurin:23-jre-alpine (for a very small image)

# Set timezone if needed (good practice)
ENV TZ=Europe/Paris
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Install Nginx directly onto this JRE base image
RUN apt update && \
  apt install -y nginx && \
  apt clean && \
  rm -rf /var/lib/apt/lists/*

# Remove ALL default nginx content and copy your config (as you already have)
RUN rm -rf /etc/nginx/conf.d/default.conf /etc/nginx/sites-enabled/default 2>/dev/null || true
RUN rm -rf /usr/share/nginx/html/* /usr/share/nginx/html/.* 2>/dev/null || true
COPY --from=frontend-build /usr/local/app/dist/front/browser/ /usr/share/nginx/html/
COPY ./front/nginx.conf /etc/nginx/conf.d/app.conf

# Test nginx configuration
RUN nginx -t

# Copy Spring Boot JAR
COPY --from=backend-build /workspace/target/*.jar /app/app.jar

# Create startup script with better logging
RUN echo '#!/bin/bash\n\
  echo "🚀 Starting P13 Application"\n\
  echo "📁 Contents of /usr/share/nginx/html:"\n\
  ls -la /usr/share/nginx/html/\n\
  echo "⚙️  Testing nginx configuration..."\n\
  nginx -t\n\
  echo "☕ Starting Spring Boot application..."\n\
  java -jar /app/app.jar &\n\
  JAVA_PID=$!\n\
  echo "✓ Spring Boot started with PID $JAVA_PID"\n\
  sleep 2\n\
  echo "🌐 Starting nginx..."\n\
  exec nginx -g "daemon off;"\n' > /start.sh

RUN chmod +x /start.sh

EXPOSE 80 8080
CMD ["/start.sh"]
