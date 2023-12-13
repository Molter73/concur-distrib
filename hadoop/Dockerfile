FROM cloudera/quickstart:latest as builder

COPY src/ src/

RUN mkdir -p target/
RUN javac -cp /usr/lib/hadoop/*:/usr/lib/hadoop-mapreduce/* src/*.java -d target/ -Xlint
RUN jar -cvf logcounter.jar -C target/ .

FROM cloudera/quickstart:latest

RUN mkdir -p /workspace/input
RUN mkdir -p /workspace/output
WORKDIR /workspace

COPY --from=builder logcounter.jar .
COPY scripts/entrypoint.sh /usr/local/bin/entrypoint.sh

ENTRYPOINT entrypoint.sh
