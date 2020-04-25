# portalizer.org


 Docker Hub

`docker pull andreisasu/portalizer:latest`

By default the container uses an embedded H2 database, MySQL is also supported and can be enabled via Spring profiles:
`docker run -e _JAVA_OPTIONS="-Dspring.profiles.active=prod,h2,swagger,testdata" -e ADMIN_PASS=<admin_password> -e USER_PASS=<user_password> <container_id>`
