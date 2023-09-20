# Todo-management-rest-api

## About the project
This is a web service for a todo management system.
#### Key features:
1. User and todo can be created.
2. User read, update, and delete their tasks.
3. The system supports Authentication using the JWT token. No API should be public other than signup and login.

## Tools and Technologies used
1. Java 17
2. Spring boot
3. Spring Data JPA
4. Spring Security
5. oauth2 resource server
6. OpenAPI and swagger
7. Mysql docker image

## Build manual
1. Download project
   ```
   git clone https://github.com/<user_github_account>/Todo-management-rest-api.git
   ```
2. Go to directory
   ```
   cd /todo-management-rest-api and checkout development branch
   ```
3. Install docker image
   ```
   https://docs.docker.com/engine/install/ubuntu/
   ```
4. Create mysql docker container
   open ubuntu terminal
   ```
   docker run --detach --env MYSQL_ROOT_PASSWORD=12345 --env MYSQL_USER=todo_user --env MYSQL_PASSWORD=12345 --env MYSQL_DATABASE=todos --name mysql --publish 3306:3306 mysql:8-oracle
   ```
5. Run docker and the run mysql docker container

   ![alt text](https://github.com/protickcse22/Todo-management-rest-api/blob/development/src/main/resources/static/docker.png)

6. Run TodoManagementRestApiApplication.java class
7. Swagger URL:
   ```
   http://localhost:8080/swagger-ui.html
   ```
   
