FROM amazoncorretto:17.0.4-alpine3.15

# create a user to run the application
RUN addgroup -S app && \
    adduser -S app -G app && \
    install -d -o app -g app /usr/app

# Work in that user's home directory
WORKDIR /usr/app

# Copy the JAR info theimage
ENV JAR=target/bakeoff-0.0.1-SNAPSHOT.jar
COPY --chown=app:app ${JAR} app.jar

EXPOSE 8080
CMD java -jar /usr/app/app.jar
