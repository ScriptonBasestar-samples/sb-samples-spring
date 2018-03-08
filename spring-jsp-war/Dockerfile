FROM tomcat;8.5

EXPOSE 8080

ADD . /app
WORKDIR app
RUN ./mvn2 package


VOLUME "m2:/root/.m2"

RUN rm -rf /usr/local/tomcat/webapps/*
RUN cp /app/ratget/hello-world.war /usr/local/tomcat/webapps/ROOT.war

CMD ["bin/"