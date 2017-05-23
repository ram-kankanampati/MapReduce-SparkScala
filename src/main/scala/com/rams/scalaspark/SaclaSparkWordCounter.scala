package com.rams.scalaspark

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.hadoop.fs.Path
import org.apache.hadoop.fs.FileSystem

object SaclaSparkWordCounter {
      /**
       * How to Run:
       * 1) Right click on project and export as JAR file(include dependent jars)
       * 2) copy the JAR file on to the server
       * 3) Copy the input  files into HDFS and execute below command
       * 
       * [root@sandbox ~]# spark-submit --class com.rams.scalaspark.SaclaSparkWordCounter --master local --deploy-mode client --executor-memory 1g --name scalasparkwordCount --conf "spark.app.id=scalasparkwordCount" scala-spark-mapred-proj-0.0.1-SNAPSHOT.jar hdfs://sandbox.hortonworks.com:8020/rams/input/sampledata.txt hdfs://sandbox.hortonworks.com:8020/rams/output/scala_sparkword_count 1
       * 
       */
      def main(args: Array[String]){
      /**
       * This property need to be uncommented for local run;  
       */
      //System.setProperty("hadoop.home.dir", "C:\\winutils\\");
      if(args.length != 3){
        println("Please pass three arguments in the following order: <input> <output> <numPartitions>")
      }
      val inputFile = args(0);
      val outputPath = args(1);
      val numPartitios = args(2).toInt;
      
      //Create Spark context with Spark Configuration
      val conf = new SparkConf().setAppName("scalasparkwordCount").setMaster("local");
      // Create a Scala Spark Context.
      val sc = new SparkContext(conf)
      
      // Load our input data.
      val input =  sc.textFile(inputFile)
      
      // Split up lines into words by space as delimiter
      val words = input.flatMap(line => line.split(" "))
      
      // Transform into word and count.
      val counts = words.map(word => (word, 1)).reduceByKey( ((x, y) => x + y), numPartitios).sortByKey(false); //false for descending, true for ascending
      
      //DELETE output path if already exists - START
      val hadoopConf = sc.hadoopConfiguration
      val hdfs = FileSystem.get(hadoopConf)

      val path = new Path(outputPath)
      if (hdfs.exists(path)) {
        hdfs.delete(path, true)
      }      

      // Save the word count back out to a text file, causing evaluation.
      counts.saveAsTextFile(outputPath)
  }

}