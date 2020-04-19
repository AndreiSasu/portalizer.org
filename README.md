# portalizer.org


 Docker

`docker pull andreisasu/portalizer:latest`

`docker run -e _JAVA_OPTIONS="-Dlogging.level.org.springframework=DEBUG -Dspring.profiles.active=prod,h2,swagger,testdata" -e ADMIN_PASS=<admin_password> -e USER_PASS=<user_password> <container_id>`
