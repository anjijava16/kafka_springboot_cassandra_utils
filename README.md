# kafka_springboot_cassandra_utils

# ProjectName: cqss_mmtech_works

# UseCase 1:
 ```
  CRUD Operation using MicroServices Developement:  UI(Swagger UI) => 
  
  Spring BOOT ==> Cassandra (Save,Read,Delete,Update)
  
  ```
# UseCase 2:
    ``` UI ==> Insert into Json Message to ==>Spring BOOT ==>Push to Kafka Producer (.send("json message")) ```

# UseCase 3.(i):
    
    ``` Backend System ==> Spring Boot/Spring Core /JavaCassandraClient Consume Kafka Message  ===> 	
    Kafka Consumer ===> Validate the data ==> Writ to cassandra DB
    
    ```
	
# UseCase 4 :
     
     ```
       Use Spark Consumer read Json String data ==> Validate data  ==>Write Into Cassandra
     ```
# Develop the ValidateFramework or Utils classes for Validate the Input jsonString message and consume jsonString messages

# UseCase 5:

java -jar avro-tools-1.8.1.jar compile schema ClickRecordV1.avsc .

It will generate NewClickRecord.java 

specific.avro.reader true ==>Setting code generation here ...

https://github.com/LearningJournal/ApacheKafkaTutorials/blob/master/AvroProducer-V1/AvroProducer.java




