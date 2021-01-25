#!/usr/bin/bash

#Starts app and saves the process id in a file to kill it later
echo 'Starting Spring Boot app'
cd '/home/ec2-user/app'

echo 'Overwrite application properties with secret info'
cat << EOF > ./application.properties
spring.datasource.url=${RDS_DATASOURCE_URL}
spring.datasource.username=${RDS_USER}
spring.datasource.password=${RDS_PASSWORD}
EOF
echo 'Starting Spring Boot app'
java -jar si-projekt-anamnese-service-0.0.1-SNAPSHOT.jar &
