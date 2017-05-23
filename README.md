# MapReduce-SparkScala
Map Reduce programs with Traditional MapReduce framework, Spark with Java and Scala
      /**
       * <p>How to Run:
       * 1) Right click on project and export as JAR file(include dependent jars)
       * 2) copy the JAR file on to the server
       * 3) Copy the input  files into HDFS and execute below command
       * <br>
       * <br>[root@sandbox ~]# spark-submit --class com.rams.scalaspark.SaclaSparkWordCounter --master local --deploy-mode 
         client --executor-memory 1g --name scalasparkwordCount --conf "spark.app.id=scalasparkwordCount" 
         scala-spark-mapred-proj-0.0.1-SNAPSHOT.jar
         hdfs://sandbox.hortonworks.com:8020/rams/input/sampledata.txt
         hdfs://sandbox.hortonworks.com:8020/rams/output/scala_sparkword_count 1
       * 
       */
