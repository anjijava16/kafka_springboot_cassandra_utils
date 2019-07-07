package com.mmtechpocs.sparkcassandra.process;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import com.datastax.spark.connector.japi.CassandraJavaUtil;
import com.google.gson.Gson;
import com.mmtechpocs.sparkcassandra.model.Employee;
import com.mmtechpocs.sparkcassandra.utils.PropertyFileReader;

import kafka.serializer.StringDecoder;

public class SparkCassandraProcessor {

	//private static final Logger logger = Logger.getLogger(SparkCassandraProcessor.class);

	public static JavaSparkContext sc;
	
	public static void main(String[] args) throws Exception {
		// read Spark and Cassandra properties and create SparkConf
		Properties prop = PropertyFileReader.readPropertyFile();
		SparkConf conf = new SparkConf().setAppName(prop.getProperty("com.iot.app.spark.app.name"))
				.setMaster(prop.getProperty("com.iot.app.spark.master"))
				.set("spark.cassandra.connection.host", prop.getProperty("com.iot.app.cassandra.host"))
				.set("spark.cassandra.connection.port", prop.getProperty("com.iot.app.cassandra.port"))
				.set("spark.cassandra.connection.keep_alive_ms", prop.getProperty("com.iot.app.cassandra.keep_alive"));
		
		
		// batch interval of 5 seconds for incoming stream
		JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(8));
		// add check point directory
		jssc.checkpoint(prop.getProperty("com.iot.app.spark.checkpoint.dir"));

		// read and set Kafka properties
		Map<String, String> kafkaParams = new HashMap<String, String>();
		kafkaParams.put("zookeeper.connect", prop.getProperty("com.iot.app.kafka.zookeeper"));
		kafkaParams.put("metadata.broker.list", prop.getProperty("com.iot.app.kafka.brokerlist"));
		String topic = prop.getProperty("com.iot.app.kafka.topic");
		Set<String> topicsSet = new HashSet<String>();
		topicsSet.add(topic);

		// create direct kafka stream

		JavaPairInputDStream<String, String> directKafkaStream = KafkaUtils.createDirectStream(jssc, String.class,
				String.class, StringDecoder.class, StringDecoder.class, kafkaParams, topicsSet);

		

		// We need non filtered stream for poi traffic data calculation
		JavaDStream<String> nonFilteredIotDataStream = directKafkaStream.map(tuple -> tuple._2());

		//nonFilteredIotDataStream.print();
		 sc = jssc.sparkContext();
		nonFilteredIotDataStream.foreachRDD((v1, v2) -> {
			
			v1.foreach((message) -> {
				Gson gson = new Gson();
				System.out.println("################  Start Here ###################################### ");
				Employee empData = gson.fromJson(message, Employee.class);
				System.out.println(" In Cassandra Consumer Side logic Here ===>"+empData.toString());
				System.out.println("################  End Here ###################################### ");
				//System.out.println("V1 Inside the Logic "+v1+"  V2 Inside the Logic ==>"+v2);

				List<Employee> employee = Arrays.asList(empData);
				System.out.println("employee ==List ==>"+employee);
				//javaFunctions(rdd).writerBuilder("mmtechwork", "employee", mapToRow(Employee.class)).saveToCassandra();

				/** 
				JavaRDD<Employee> rdd = sc.parallelize(employee);
				
				
				Map<String, String> columnNameMappings = new HashMap<String, String>();
				columnNameMappings.put("id", "id");
				columnNameMappings.put("firstname", "firstname");
				columnNameMappings.put("emailid", "emailid");
				columnNameMappings.put("lastname", "lastname");
				
				javaFunctions(rdd).writerBuilder("mmtechwork", "employee", CassandraJavaUtil.mapToRow(Employee.class, columnNameMappings)).saveToCassandra();
					**/
			});
		});
		

		jssc.start();
		jssc.awaitTermination();

	}
}
