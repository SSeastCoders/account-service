FROM maven
WORKDIR /var/www

COPY . .
RUN mvn clean -DskipTests install
#EXPOSE 8223

CMD [ "mvn", "-pl", "account-api", "-P", "dev", "spring-boot:run"]