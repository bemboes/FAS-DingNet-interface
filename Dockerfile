FROM openjdk:11
WORKDIR /usr/app
COPY DingNetExe/DingNet.jar .
CMD [ "java", "-jar", "DingNet.jar" ]
