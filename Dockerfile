FROM maven:3-adoptopenjdk-8 as build

WORKDIR /sklgen

COPY generator-root .

RUN mvn versions:set -DremoveSnapshot
RUN mvn package

FROM java:8-jre as release

WORKDIR /sklgen

COPY --from=build /sklgen/generator-bash/target/*.zip /sklgen
RUN unzip *.zip && rm *.zip 
RUN mv generator-bash*/* . && rm -rf generator-bash*

RUN chmod +x /sklgen/bin/sklgen.sh

ENV SKLGEN_HOME=/sklgen
ENV PATH=${PATH}:/sklgen/bin/

WORKDIR /project

ENTRYPOINT [ "sklgen.sh" ]