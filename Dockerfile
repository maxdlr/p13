FROM node:latest AS frontend-build
WORKDIR /usr/local/app
COPY ./front/ /usr/local/app/
RUN npm install -g @angular/cli && npm install
RUN ng build --configuration=production

RUN echo "=== Angular build completed ===" && \
  ls -la /usr/local/app/dist/front/ && \
  echo "=== Checking for index.html ===" && \
  test -f /usr/local/app/dist/front/index.html && echo "✓ index.html found" || echo "✗ index.html NOT found"

FROM maven:3.9.9-ibm-semeru-23-jammy AS backend-build
WORKDIR /workspace
COPY ./back/pom.xml /workspace
COPY ./back/src /workspace/src
RUN mvn -B -f pom.xml clean package -DskipTests

FROM eclipse-temurin:23-jre-alpine AS final-app-stage

ENV TZ=Europe/Paris
RUN apk add --no-cache tzdata && \
  cp /usr/share/zoneinfo/$TZ /etc/localtime && \
  echo $TZ > /etc/timezone

RUN apk add --no-cache nginx && \
  mkdir -p /run/nginx

RUN rm -rf /etc/nginx/conf.d/default.conf /etc/nginx/sites-enabled/default 2>/dev/null || true
RUN rm -rf /usr/share/nginx/html/* /usr/share/nginx/html/.* 2>/dev/null || true
COPY --from=frontend-build /usr/local/app/dist/front/browser/ /usr/share/nginx/html/
COPY ./front/nginx.conf /etc/nginx/nginx.conf

RUN nginx -t

COPY --from=backend-build /workspace/target/*.jar /app/app.jar

COPY start.sh /start.sh
RUN chmod +x /start.sh

EXPOSE 80 8080
CMD ["/start.sh"]
