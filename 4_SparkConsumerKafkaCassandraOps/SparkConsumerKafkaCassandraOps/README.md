# Step1
	 	Application: SparkConsumereIntoCassandraService
	 
	 
# Step 2:
	  	For Spark with Cassandra 1.x refer pomSpark1.6_cassandra1.x.xml
	  	For Spark with Cassandra 2.x refer pom.xml
	  	
# Step 3: run the SparkConsumer Logic


# Step 4 Configure the Cassandra info 
      # Crate cassandra Database 
     CREATE KEYSPACE IF NOT EXISTS mmtechwork WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};
	
	# Create Cassandra Table 
	CREATE TABLE mmtechwork.employee1 (
    id text PRIMARY KEY,
    emailId text,
    firstName text,
    lastName text
	) WITH bloom_filter_fp_chance = 0.01
      	  	
	  	 	 	
  

 
