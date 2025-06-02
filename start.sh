#!/bin/sh
ls -la /usr/share/nginx/html/
nginx -t
java -jar /app/app.jar &
sleep 2
exec nginx -g "daemon off;"
