# Black Horse

This a project used for the Black Horse


## Installation

1. This project is implemented using Maven. Before running the project, please execute the command 'mvn clean install' to compile and package the project.

2. This project relies on MySQL and RabbitMQ to run. Please execute the following command to download and run the corresponding dependencies.
```bash
docker run -d --hostname my-rabbit --name rabbit -p 15672:15672 -p 5672:5672 -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin -e RABBITMQ_DEFAULT_VHOST=center  rabbitmq:management
```

3. Please run this command to startup:
```bash
# Clone the repository
mvn spring-boot:run
