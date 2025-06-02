#!/bin/sh
echo "🚀 Starting P13 Application"
echo "📁 Contents of /usr/share/nginx/html:"
ls -la /usr/share/nginx/html/
echo "⚙️  Testing nginx configuration..."
nginx -t
echo "☕ Starting Spring Boot application..."
java -jar /app/app.jar &
JAVA_PID=$!
echo "✓ Spring Boot started with PID $JAVA_PID"
sleep 2
echo "🌐 Starting nginx..."
exec nginx -g "daemon off;"
