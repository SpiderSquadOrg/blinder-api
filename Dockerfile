FROM maven:3.9.6 AS build
WORKDIR /app
COPY pom.xml /app
RUN mvn dependency:resolve
COPY . /app
RUN mvn clean
RUN mvn package -DskipTests -X

# Set environment variables
ENV SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
ENV SPRING_JPA_HIBERNATE_SHOW_SQL=true
ENV SPRING_JPA_PROPERTIES_JAVAX_PERSISTENCE_VALIDATION_MODE=none
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://blinder.postgres.database.azure.com:5432/blinder-db
ENV SPRING_DATASOURCE_USERNAME=bothuany
ENV SPRING_DATASOURCE_PASSWORD=Blinder_pass31
ENV SPOTIFY_CLIENT_ID=fb4cf45805384d23bb222135d23fc49c
ENV SPOTIFY_CLIENT_SECRET=6c506ab4cf9f497b84124d023eee1872
ENV RAPIDAPI_KEY=556055836cmsh3c96f16e5e8be1cp1e896bjsnf0d7dc9a612e
ENV SPRING_SERVLET_MULTIPART_MAX_FILE_SIZE=10MB
ENV SPRING_SERVLET_MULTIPART_MAX_REQUEST_SIZE=11MB
ENV SNAPADMIN_ENABLED=true
ENV SNAPADMIN_BASEURL=admin
ENV SNAPADMIN_MODELSPACKAGE=com.blinder.api.user.model,com.blinder.api.TVSeriesCategories.model,com.blinder.api.TVSeries.model,com.blinder.api.report.model,com.blinder.api.possibleMatch.model,com.blinder.api.MusicCategory.model,com.blinder.api.Music.model,com.blinder.api.MovieCategory.model,com.blinder.api.Movie.model,com.blinder.api.location.model,com.blinder.api.hobby.model,com.blinder.api.filter.model,com.blinder.api.characteristics.model
ENV SPRING_JPA_OPEN_IN_VIEW=true

FROM openjdk
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]