# Spring Batch Bulk Email
Use Spring Batch to send emails in bulk

## Description 
This Spring Batch application has 2 jobs: 

Job#1 : 
- To read from a csv file (sample.csv under src/main/resources) and import all it's data onto a database
- Uses MariaDB docker image for the database (docker compose yaml - docker-compose.yml under Docker folder)

Job#2 :
- Read all records from the Person table in the database and send emails to those people who have requested to be subscribed
- Used a fake SMTP server - MailCatcher - (Docker file @ https://github.com/chatwork/dockerfiles/blob/master/mailcatcher/Dockerfile)

## Prerequisites
- Java 17
- Maven
- Docker (Compose)
- MailCatcher docker file

## Run
1. Start Database container from the folder "Docker"
```
docker-compose up
```
2. Start Mailcatcher container
3. Compile and run the Java App.

## Sample Data
Generated using chatgpt

