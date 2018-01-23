FROM openjdk:7
COPY . /usr/src/jxassert
WORKDIR /usr/src/jxassert
RUN ./gradlew build