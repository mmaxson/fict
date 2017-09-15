

#Build:
    mvn flyway:migrate    (mvn flyway:repair after a failure; mvn flyway:clean to run all scripts from start)

    mvn clean package docker:build
    docker build -t marku/fict target/docker
    docker push marku/fict
    docker pull marku/fict


#Running:
 
  ###Prerequisites:
- auth-server and config-server must be running
- Ask for tokens[access+refresh] using HTTP POST on /oauth/token, with grant_type=password, and resource owners credentials as req-params.
- Additionally, send client credentials in Authorization header.
- POST localhost:7771/murun/auth/oauth/token?grant_type=password&username={auth.users.username}&password={auth.users.password}
        with basic authentication (Client id in auth.oauth_client_details;)

- To get a refresh token, POST localhost:7771/murun/auth/oauth/token?grant_type=refresh_token&refresh_token=xxxxxx
m

  ##Shell Run:

    Env vars:
        LOG_APPENDER=Console-Appender




  ##Docker Run:

    docker run -d -p  80:80 -e 3306:3306   -e "LOG_APPENDER=Console-Appender"  -e SPRING_PROFILES_ACTIVE=dev -e CLOUD_CONFIG_USER_PASSWORD=marku -e CLOUD_CONFIG_IP=127.0.0.0.0 -e CLOUD_CONFIG_PORT=8888   marku/fict --name fict