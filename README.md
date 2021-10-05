

# General information

Horecarobot project

<!-- vscode-markdown-toc -->
* 1. [Quick start](#Quickstart)
* 2. [Pipeline](#Pipeline)

<!-- vscode-markdown-toc-config
	numbering=true
	autoSave=true
	/vscode-markdown-toc-config -->
<!-- /vscode-markdown-toc -->

##  1. <a name='Quickstart'></a>Quick start

Rename `application.properties.example` to `application.properties` in `src/main/resources/`

Run this command in your terminal:

`./mvnw spring-boot:run`

Or use your IDE integration.

##  2. <a name='Pipeline'></a>Pipeline

Builds, tests and deploys new docker image to [docker hub](https://hub.docker.com/repository/docker/iqfx/horeca-backend).

## 3. Building the docker image
`./mvnw clean package`

`docker build -t horecarobot:latest .`

If you want to run it / test it:

`docker-compose up -d --build`

Credits:
- wait_for_it script: https://github.com/vishnubob/wait-for-it