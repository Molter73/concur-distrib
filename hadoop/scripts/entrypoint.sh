#!/usr/bin/env bash

set -exuo pipefail

DAEMONS="\
    mysqld \
    cloudera-quickstart-init \
    zookeeper-server \
    hadoop-hdfs-datanode \
    hadoop-hdfs-journalnode \
    hadoop-hdfs-namenode \
    hadoop-hdfs-secondarynamenode \
    hadoop-httpfs \
    hadoop-mapreduce-historyserver \
    hadoop-yarn-nodemanager \
    hadoop-yarn-resourcemanager \
    hbase-master \
    hbase-rest \
    hbase-thrift \
    hive-metastore \
    hive-server2 \
    sqoop2-server \
    spark-history-server \
    hbase-regionserver \
    impala-state-store \
    oozie \
    solr-server \
    impala-catalog \
    impala-server"

for daemon in ${DAEMONS}; do
    sudo service "${daemon}" start
done

hadoop fs -mkdir -p /workspace/input
hadoop fs -put /workspace/input/* /workspace/input

hadoop jar /workspace/logcounter.jar LogCounterDriver /workspace/input /workspace/output

hadoop fs -cat /workspace/output/*
