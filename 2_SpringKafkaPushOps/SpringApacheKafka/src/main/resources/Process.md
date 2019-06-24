# Spring Swagger URL
```

http://localhost:8080/springKafka/swagger-ui.html#!/web-rest-controller/producerUsingGET

```

# Kafka Best practices

 
```
1: Producer
2. Kafka Broker(Server)
3. Consumer

1.Producer Send data to Kafka Broker/ Server:
--->Compression of Large messages(We recommend that you compress large messages to reduce the disk footprint,
 and also the footprint on the wire)
--->Batch messages in Apache Kafka
# format: host1:port1,host2:port2 ...
	bootstrap.servers=localhost:9092

# specify the compression codec for all data generated: none, gzip, snappy, lz4
compression.type=none

# name of the partitioner class for partitioning events; default partition spreads data randomly
#partitioner.class=

# the maximum amount of time the client will wait for the response of a request
#request.timeout.ms=

# how long `KafkaProducer.send` and `KafkaProducer.partitionsFor` will block for
#max.block.ms=

# the producer will wait for up to the given delay to allow other records to be sent so that the sends can be batched together
#linger.ms=

# the maximum size of a request in bytes
#max.request.size=

# the default batch size in bytes when batching multiple records sent to a partition
#batch.size=

# the total bytes of memory the producer can use to buffer records waiting to be sent to the server
#buffer.memory=
    

2. Kafka Broker(Server) :

   The Kafka Broker is the central part of Kafka. It receives and stores log messages until the given retention period has exceeded. 
   Topics:
     Partitions:
       Replications :
         LOGS Direcotrys(Message Logs,retention periods)
         
       Note: In Kafka, replication is implemented at the partition level. 
       Kafka automatically failover to these replicas when a server in the cluster fails so that messages remain available
        in the presence of failures.
       Each partition of a topic can be replicated on one or many nodes, depending on the number of nodes you have in your cluster
       #The directory in which the log data is kept (supplemental for log.dirs property) default:	/tmp/kafka-logs
		log.dir
		
		#The directories in which the log data is kept. If not set, the value in log.dir is used
		log.dirs
		
		#The number of messages accumulated on a log partition before messages are flushed to disk
		log.flush.interval.messages
		
		#he maximum time in ms that a message in any topic is kept in memory before flushed to disk. If not set,
		 the value in log.flush.scheduler.interval.ms is used
		log.flush.interval.ms
   
3. Consumer :
   Apache Kafka Consumer Consumers can read log messages from the broker, starting from a specific offset. 
   Consumers are allowed to read from any offset point they choose. 
	   spark.kafka.consumer.group-id=jsa-group
		spark.kafka.consumer.bootstrap.servers=localhost:9092,localhost:9093,localhost:9094
		spark.kafka.consumer.group.id=test
		spark.kafka.consumer.enable.auto.commit=true
		spark.kafka.consumer.auto.commit.interval.ms=1000
		spark.kafka.consumer.session.timeout.ms=30000
		spark.kafka.consumer.key.deserializer=org.apache.kafka.common.serialization.StringDeserializer
		spark.kafka.consumer.value.deserializer=org.apache.kafka.common.serialization.StringDeserializer 
		
		  security.protocol" -> "SSL",
		  "ssl.truststore.location" -> "/some-directory/kafka.client.truststore.jks",
		  "ssl.truststore.password" -> "test1234",
		  "ssl.keystore.location" -> "/some-directory/kafka.client.keystore.jks",
		  "ssl.keystore.password" -> "test1234",
		  "ssl.key.password" -> "test1234" 
		   
   This allows consumers to join the cluster at any point in time.
   #The password of the private key in the key store file. This is optional for client password
	ssl.key.password:	
	
	#The location of the key store file. This is optional for client and can be used for two-way authentication for client.	
	ssl.keystore.location:	
	
	
	#The store password for the key store file. This is optional for client and only needed if ssl.keystore.location is configured.
	ssl.keystore.password 
	
	#The location of the trust store file.
	ssl.truststore.location
	#What to do when there is no initial offset in Kafka or if the current offset does not exist any more on the server 
	(e.g. because that data has been deleted):
	earliest: automatically reset the offset to the earliest offset
	latest: automatically reset the offset to the latest offset
	none: throw exception to the consumer if no previous offset is found for the consumer's group
	anything else: throw exception to the consumer
	
	auto.offset.reset
	
	#If true the consumer's offset will be periodically committed in the background.
	enable.auto.commit
	
	#The maximum number of records returned in a single call to poll(). 500
	max.poll.records
	
	#The maximum delay between invocations of poll() when using consumer group management. 
	This places an upper bound on the amount of time that the consumer can be idle before fetching more records.
	 If poll() is not called before expiration of this timeout, 
	 then the consumer is considered failed and the group will rebalance in order to reassign the partitions to another member.
	
	# By Default 300000
	max.poll.interval.ms
   
   
   Spark Consumer :
	   OPTIONAL - Consumer Back Pressure Support. Default is true
		consumer.backpressure.enabled=false
		OPTIONAL - This can further control RDD Partitions. Number of Blocks fetched from Kafka to merge before writing to Spark Block Manager. Default is 1
		consumer.num_fetch_to_buffer=10
		OPTIONAL - ZK used for consumer offset commit. It can be different ZK cluster than what used for Kafka.
		zookeeper.consumer.connection=host1:2181,host2:2181
	   
```


# Kafak Producer,Broker and Consumer Config Parameters :
```

		# Spring BOOT Parameters
		server.contextPath=/springKafka
		
		
		
		spark.kafka.producer.bootstrap-servers=localhost:9092,localhost:9092,localhost:9093,localhost:9094
		spark.kafka.producer.topic=dev-kafka-topic
		#jsa.kafka.topic=jsa-kafka-topic
		 #Set acknowledgements for producer requests.      
		spark.kafka.producer.acks=all
		#If the request fails, the producer can automatically retry,
		spark.kafka.producer.retries=0
		# Specify buffer size in config the default batch size in bytes when batching multiple records sent to a partition
		spark.kafka.producer.batch.size=16384
		#Reduce the no of requests less than 0   
		spark.kafka.producer.linger.ms=1
		#The buffer.memory controls the total amount of memory available to the producer for buffering.   
		spark.kafka.producer.buffer.memory=33554432
		spark.kafka.producer.key.serializer=org.apache.kafka.common.serialization.StringSerializer
		spark.kafka.producer.value.serializer= org.apache.kafka.common.serialization.StringSerializer
		# the maximum size of a request in bytes 1048576 (1MB) 
		spark.kafka.producer.max.request.size=1048576
		## specify the compression codec for all data generated: none, gzip, snappy, lz4
		spark.kafka.producer.compression.type=gzip
		
		
		#Protocol used to communicate with brokers. Valid values are: PLAINTEXT, SSL, SASL_PLAINTEXT, SASL_SSL.
		security.protocol=SSL
		kafka.ssl.truststore.location=/dbfs/mnt/kafka-private/ssl/kafka.client.truststore.jks
		kafka.ssl.keystore.location=/dbfs/mnt/kafka-private/ssl/kafka.client.keystore.jks
		kafka.ssl.keystore.password=<keystore-password>
		kafka.ssl.truststore.password=truststore-password>
		
		
		# If min.insync.replicas is set to 2 and acks is set to all, each message must be written successfully to at least
		#two replicas.This guarantees that the message is not lost unless both hosts crash.
		min.insync.replicas=1
		
		#auto.create.topics.enable is set to true by
		## partitioner.class
		#Partitioner=org.apache.kafka.clients.producer.Partitioner interface.
		
		#The name of the security provider used for SSL connections. Default value is the default security provider of the JVM.
		ssl.provider=null
		#The file format of the trust store file Example JKS
		ssl.truststore.type=
		
		
		
		spark.kafka.consumer.group-id=jsa-group
		spark.kafka.consumer.bootstrap.servers=localhost:9092,localhost:9093,localhost:9094
		spark.kafka.consumer.group.id=test
		spark.kafka.consumer.enable.auto.commit=true
		spark.kafka.consumer.auto.commit.interval.ms=1000
		spark.kafka.consumer.session.timeout.ms=30000
		spark.kafka.consumer.key.deserializer=org.apache.kafka.common.serialization.StringDeserializer
		spark.kafka.consumer.value.deserializer=org.apache.kafka.common.serialization.StringDeserializer  


```



set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_131

# Step1 : Start the Zookeeper

bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties 


# Step 2: Start Kafka Server
bin\windows\kafka-server-start.bat ./config/server.properties 

# Step 3: List of Topocs info 
bin\windows\kafka-topics.bat --list --zookeeper localhost:2181

# Step 4: Create a topic
bin\windows\kafka-topics.bat --create --zookeeper localhost:2182 --replication-factor 1 --partitions 1 --topic welcometest

# Step 5: Create a command for producer data
bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic test 


# Step 6: Create a command for Consumer data
bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic testconnect-test

# Kafka Divided into three parts



#How can I send large messages with Kafka (over 15MB)?

```
Code:
private ProducerConfig kafkaConfig() {
    Properties props = new Properties();
    props.put("metadata.broker.list", BROKERS);
    props.put("serializer.class", "kafka.serializer.StringEncoder");
    props.put("request.required.acks", "1");
    props.put("message.max.bytes", "" + 1024 * 1024 * 40);
    return new ProducerConfig(props);
}
ERROR:
5305 [main] ERROR kafka.producer.async.DefaultEventHandler  - Failed to send requests for topics datasift with correlation ids in [213,224]
kafka.common.FailedToSendMessageException: Failed to send messages after 3 tries

Kafka 0.10
######################################################
Minor changes required for Kafka 0.10 and the new consumer compared to laughing_man's answer:
Broker: No changes, you still need to increase properties message.max.bytes and replica.fetch.max.bytes. message.max.bytes has to be equal or smaller(*) than replica.fetch.max.bytes.
Producer: Increase max.request.size to send the larger message.
Consumer: Increase max.partition.fetch.bytes to receive larger messages.
(*) Read the comments to learn more about message.max.bytes<=replica.fetch.max.bytes
######################################################

Kafka 0.8:
######################################################
You need to adjust three (or four) properties:
Consumer side:fetch.message.max.bytes - this will determine the largest size of a message that can be fetched by the consumer.
Broker side: replica.fetch.max.bytes - this will allow for the replicas in the brokers to send messages within the cluster and make sure the messages are replicated correctly. If this is too small, then the message will never be replicated, and therefore, the consumer will never see the message because the message will never be committed (fully replicated).
Broker side: message.max.bytes - this is the largest size of the message that can be received by the broker from a producer.
Broker side (per topic): max.message.bytes - this is the largest size of the message the broker will allow to be appended to the topic. This size is validated pre-compression. (Defaults to broker's message.max.bytes.)
I found out the hard way about number 2 - you don't get ANY exceptions, messages, or warnings from Kafka, so be sure to consider this when you are sending large messages.
######################################################
```
#Q: Use of producer.properties and consumer.properties file in Apache Kafka
```
Q: Inside the Kafka package there is an config folder which has various config files. This folder has consumer.properties and producer.properties files, are these configurations used when we run the Kafka Cluster and when our code connects to kafka cluster or are these just sample property files? The documentation is not very clear on this.
We have an automation job that create and deploys new kafka nodes and I need to know whether any changes needs to be made in these files.

ANS:
The consumer.properties and producer.properties files are just examples for configuring a consumer or producer application. They can be used by the kafka-console-consumer console application for example with the --consumer.config paramenter by the kafka-console-producer console application with the --producer.config parameter. In any case even server.properties and zookeeper.properties files are example of configuration for broker and zookeeper.

```
# Kafka connect API 

```
bin/connect-standalone.sh config/connect-standalone.properties config/connect-file-source.properties config/connect-file-sink.properties

To start a standalone Kafka Connector, we need following three configuration files.
onnect-standalone.properties
connect-file-source.properties
connect-file-sink.properties

step 1:connect-file-source.properties:
###################
name=local-file-source
connector.class=FileStreamSource
tasks.max=1
file=D:/Tech_Works_2019_USA/kafka_2.11-2.0.0/Kafka_connect_examples/src/test.txt
topic=connect-test
#########################################

$ bin\windows\connect-standalone.bat config\connect-standalone.properties config\connect-file-source.properties config\connect-file-sink.properties

By default, Apache Kafka producer will distribute the messages to different partitions by round-robin fashion.

Custom Partition example:
https://howtoprogram.xyz/2016/06/04/write-apache-kafka-custom-partitioner/

```