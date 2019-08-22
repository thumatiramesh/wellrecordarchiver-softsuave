FROM ubuntu:16.04

RUN apt-get update 
RUN apt-get install -y openjdk-8-jdk wget unzip tesseract-ocr tesseract-ocr-fra
RUN apt-get install -y gosu bash openssl nano
RUN update-ca-certificates -f

ENV APP_PATH=/runtime

WORKDIR $APP_PATH

RUN wget https://repo1.maven.org/maven2/fr/pilato/elasticsearch/crawler/fscrawler-es6/2.6/fscrawler-es6-2.6.zip
RUN unzip fscrawler-es6-2.6.zip

RUN mkdir /dirs
RUN cd fscrawler-es6-2.6

ENV PATH="$APP_PATH/fscrawler-es6-2.6/bin:${PATH}"
ENV JAVA_HOME="/usr/lib/jvm/open-jdk"
ENV FS_JAVA_OPTS="-Xmx2g -Xms2g"

CMD tail -f /dev/null