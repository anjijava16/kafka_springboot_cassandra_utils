package com.mmtechpocs.sparkcassandra.process;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmtechpocs.sparkcassandra.model.Employee;

import SparkConsumerKafkaCassandraOps.SparkConsumerKafkaCassandraOps.Word;
import scala.Tuple2;

public class SparkConsumerProcessOperation {
	public static JavaSparkContext sparkContext;

	public static void main(String[] args) throws InterruptedException {

		Logger.getLogger("org").setLevel(Level.OFF);
		Logger.getLogger("akka").setLevel(Level.OFF);

		Map<String, Object> kafkaParams = new HashMap<>();
		kafkaParams.put("bootstrap.servers", "localhost:9092");
		kafkaParams.put("key.deserializer", StringDeserializer.class);
		kafkaParams.put("value.deserializer", StringDeserializer.class);
		kafkaParams.put("group.id", "use_a_separate_group_id_for_each_stream");
		kafkaParams.put("auto.offset.reset", "latest");
		kafkaParams.put("enable.auto.commit", false);

		Collection<String> topics = Arrays.asList("dev-pushmessage_service-topic");

		SparkConf sparkConf = new SparkConf();
		sparkConf.setMaster("local[2]");
		sparkConf.setAppName("WordCountingAppWithCheckpoint");
		sparkConf.set("spark.cassandra.connection.host", "127.0.0.1");
		sparkConf.set("spark.cassandra.connection.port", "9042");
		sparkConf.set("spark.cassandra.connection.keep_alive_ms", "10000");

		JavaStreamingContext streamingContext = new JavaStreamingContext(sparkConf, Durations.seconds(6));

		sparkContext = streamingContext.sparkContext();

		streamingContext.checkpoint("./.checkpoint");

		JavaInputDStream<ConsumerRecord<String, String>> messages = KafkaUtils.createDirectStream(streamingContext,
				LocationStrategies.PreferConsistent(),
				ConsumerStrategies.<String, String>Subscribe(topics, kafkaParams));

		JavaPairDStream<String, String> results = messages
				.mapToPair(record -> new Tuple2<>(record.key(), record.value()));

		JavaDStream<String> messageRDD = results.map(tuple2 -> tuple2._2());

		JavaDStream<Employee> jsonInputDTORDD = messageRDD.map(new Function<String, Employee>() {
			public Employee call(String string) throws Exception {
				
				return new ObjectMapper().readValue(string, Employee.class);
				
			}
		});

		jsonInputDTORDD.foreachRDD(javaRdd -> {

			List<Employee> listOfEmployees = javaRdd.collect();
			JavaRDD<Employee> rdd = sparkContext.parallelize(listOfEmployees);

			System.out.println("RDD count Here " + rdd.count());
			System.out.println("RDD collect Here " + rdd.collect());

			if (rdd.count() != 0) {
				Map<String, String> columnNameMappings = new HashMap<String, String>();
				columnNameMappings.put("id", "id");
				columnNameMappings.put("firstname", "firstname");
				columnNameMappings.put("emailid", "emailid");
				columnNameMappings.put("lastname", "lastname");

				javaFunctions(rdd)
						.writerBuilder("mmtechwork", "employee1", mapToRow(Employee.class, columnNameMappings))
						.saveToCassandra();

			}
		});

		streamingContext.start();
		streamingContext.awaitTermination();

	}

}
