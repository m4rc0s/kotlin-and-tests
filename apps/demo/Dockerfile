FROM openjdk:17

RUN adduser -r demo

WORKDIR /src

USER demo

COPY ./build/libs/demo-1.0.jar ./demo.jar

EXPOSE 8080

CMD java -jar demo.jar
