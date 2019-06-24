# Spring Boot + Apache Cassandra

This is an example **Spring Boot + Apache Cassandra** app.

It was made using **Spring Boot**, **Apache Cassandra**, **Spring Security**, **Spring Data Cassandra**, **Docker** and **Docker Compose**.

# How to Start Cassandra in Windows 
  ```
  Start Cassandra Server
    C:/Dev> cassandra.bat
    
  # Run cqlsh
   C:/Dev>cqlsh  

```
# Different Ways to Cassandra Server
``` 
 Step 1 : Using cqlsh
 Step 2: Using DataStaX UI(It is similar to SQL Developer Tool
 
 ```
 
# Cassandra Linux Setup

```
export CASSANDRA_HOME = ~/cassandra
export PATH = $PATH:$CASSANDRA_HOME/bin 

```

##What is Apache Cassandra?
Cassandra is a distributed database management system designed for handling a high volume of structured data across commodity servers

Cassandra handles the huge amount of data with its distributed architecture.

Data is placed on different machines with more than one replication factor that provides high availability and no single point of failure.


Cassandra stores data on different nodes with a peer to peer distributed fashion architecture.

All the nodes exchange information with each other using Gossip protocol.
 **Gossip** is a protocol in Cassandra by which nodes can communicate with each other.

## Cassandra Data Model


Cassandra is an open source, distributed database. 

It’s useful for managing large quantities of data across multiple data centers as well as the cloud.

Cassandra data model

Cassandra’s data model consists of keyspaces, column families, keys, and columns.

The table below compares each part of the Cassandra data model to its analogue in a relational data model.

```
** Cassandra Data Model	VS       ** Relational Data Model

Keyspace						Database
Column 						Family	Table
Partition Key					Primary Key
Column Name/Key				Column Name
Column Value				Column Value


Relational Database								NoSQL Database
Handles data coming in low	 velocity				Handles data coming in high velocity
Data arrive from one or few locations			Data arrive from many locations
Manages structured data								Manages structured unstructured and semi-structured data.
Supports complex transactions (with joins)			Supports simple transactions
single point of failure with failover				No single point of failure
Handles data in the moderate volume.					Handles data in very high volume
Centralized deployments						Decentralized deployments
Transactions written in one location				Transaction written in many locations
Gives read scalability					Gives both read and write scalability
Deployed in vertical fashion					Deployed in Horizontal fashion


Massively Scalable Architecture: 
   Cassandra has a masterless design where all nodes are at the same level which provides operational simplicity and easy scale out.
Masterless Architecture: 
   Data can be written and read on any node.
Linear Scale Performance:
   As more nodes are added, the performance of Cassandra increases.
No Single point of failure: 
   Cassandra replicates data on different nodes that ensures no single point of failure.




```



###Partition key

The partition key is responsible for distributing data among nodes. A partition key is the same as the primary key when the primary key consists of a single column.


### Imp Point 
Partition keys belong to a node. Cassandra is organized into a cluster of nodes, with each node having an equal part of the partition key hashes.

Imagine we have a four node Cassandra cluster. In the example cluster below, Node 1 is responsible for partition key hash values 0-24; 

Node 2 is responsible for partition key hash values 25-49; and so on.

##Replication factor

Depending on the replication factor configured, data written to Node 1 will be replicated in a clockwise fashion to its sibling nodes. 
So in our example above, assume we have a four-node cluster with a replication factor of three. When we insert data with a partition key of 23, 
the data will get written to Node 1 and replicated to Node 2 and Node 3. 
When we insert data with a partition key of 88, the data will get written to Node 4 and replicated to Node 1 and Node 2.



##Clustering key

Clustering keys are responsible for sorting data within a partition. Each primary key column after the partition key is considered a clustering key. 
In the crossfit_gyms_by_location example, country_code is the partition key; 
state_province, city, and gym_name are the clustering keys. 
Clustering keys are sorted in ascending order by default. So when we query for all gyms in the United States, 
the result set will be ordered first by state_province in ascending order, followed by city in ascending order, and finally gym_name in ascending order.


#Order by
To sort in descending order, add a WITH clause to the end of the CREATE TABLE statement.

```

CREATE TABLE crossfit_gyms_by_location (  
   country_code text,  
   state_province text,  
   city text,  
   gym_name text,  
   PRIMARY KEY (country_code, state_province, city, gym_name)  
) WITH CLUSTERING ORDER BY (state_province DESC, city ASC, gym_name ASC);

```

#Composite key

Composite keys are partition keys that consist of multiple columns. The crossfit_gyms_by_location example only used country_code for partitioning. 
The result is that all gyms in the same country reside within a single partition. This can lead to wide rows. 
In the case of our example, there are over 7,000 CrossFit gyms in the United States, so using the single column partition key results in a row with over 7,000 combinations.

To avoid wide rows, we can move to a composite key consisting of additional columns. 
If we change the partition key to include the state_province and city columns, the partition hash value will no longer be calculated off only country_code. 
Now, each combination of country_code, state_province, and city will have its own hash value and be stored in a separate partition within the cluster.
 We accomplish this by nesting parenthesis around the columns we want included in the composite key. 

```

CREATE TABLE crossfit_gyms_by_city (  
 country_code text,  
 state_province text,  
 city text,  
 gym_name text,  
 opening_date timestamp,  
 PRIMARY KEY ((country_code, state_province, city), opening_date, gym_name)  
) WITH CLUSTERING ORDER BY ( opening_data ASC, gym_name ASC );


```



# Valid Query:

 SELECT * FROM crossfit_gyms_by_city WHERE country_code = 'USA' and state_province = 'VA' and city = 'Arlington'

# Invalid Query :
SELECT * FROM crossfit_gyms_by_city WHERE country_code = 'USA' and state_province = 'VA'

#Components of Cassandra


** Node **
Node is the place where data is stored. It is the basic component of Cassandra.

**Data Center **
A collection of nodes are called data center. Many nodes are categorized as a data center.

**Cluster **
The cluster is the collection of many data centers.

**Commit Log **
Every write operation is written to Commit Log. Commit log is used for crash recovery.

**Mem-table **
After data written in Commit log, data is written in Mem-table. Data is written in Mem-table temporarily.

**SSTable **
When Mem-table reaches a certain threshold, data is flushed to an SSTable disk file.


#Data Replication
As hardware problem can occur or link can be down at any time during data process, a solution is required to provide a backup when the problem has occurred.
 So data is replicated for assuring no single point of failure.

```

SimpleStrategy

SimpleStrategy is used when you have just one data center. SimpleStrategy places the first replica on the node selected by the partitioner. After that, remaining replicas are placed in clockwise direction in the Node ring.

```

# NetworkTopologyStrategy

```
NetworkTopologyStrategy is used when you have more than two data centers.

In NetworkTopologyStrategy, replicas are set for each data center separately. NetworkTopologyStrategy places replicas in the clockwise direction in the ring until reaches the first node in another rack.

```

# Cassandra Keyspaces syntax
```

CREATE KEYSPACE <identifier> WITH <properties>

CREATE KEYSPACE “KeySpace Name”
WITH replication = {'class': ‘Strategy name’, 'replication_factor' : ‘No.Of   replicas’};

CREATE KEYSPACE “KeySpace Name”
WITH replication = {'class': ‘Strategy name’, 'replication_factor' : ‘No.Of  replicas’}

AND durable_writes = ‘Boolean value’;


```

# Strategy Name
   # Simple Strategy'==>	Specifies a simple replication factor for the cluster.
   # Network Topology Strategy ==>Using this option, you can set the replication factor for each data-center independently.
   


#Durable_writes
By default, the durable_writes properties of a table is set to true, however it can be set to false. 
You cannot set this property to simplex strategy.
   
   
#Cassandra Data Model Rules
In Cassandra, writes are not expensive. Cassandra does not support joins, group by, OR clause, aggregations, etc. 
So you have to store your data in such a way that it should be completely retrievable. 
So these rules must be kept in mind while modelling data in Cassandra.   



#Database(Schema ) creation:

CREATE KEYSPACE mmtech_cql WITH replication = {'class': 'NetworkTopologyStrategy'} AND durable_writes = false;

# Describe Schema
DESC KEYSPACES ;


# Alter Schema/Database Properties
```
Alter Keyspace KeyspaceName with replication={'class':'StrategyName', 
	'replication_factor': no of replications on different nodes} 
    	with DURABLE_WRITES=true/false
    	
```
# Use Schema
USE kalyan_cql;

# Drop Schema
DROP KEYSPACE IF EXISTS mmtech_cql ;

# Table Creation:
```
CREATE TABLE example_table (
   text_field_1 TEXT,
   text_field_2 TEXT,
   int_field_1  INT,
   int_field_2  INT,
   PRIMARY KEY (text_field_1, text_field_2, int_field_1, int_field_2))
   WITH CLUSTERING ORDER BY (text_field_2 ASC, int_field_1 ASC, int_field_2 ASC);
describe tables;

```

# Insert into the table
```
insert into  example_table (text_field_1,text_field_2,int_field_1,int_field_2) values('id','name',10,20);
```

# Describe tables
describe tables

# Describe specific table

DESC TABLE example_table ;

# Table Creation Customer
 ```
CREATE TABLE customer(
   id int PRIMARY KEY,
   firstname text,
   lastname text,
   age int
);

```

# Index Creation for specific column

– Create an index on firstname column:

CREATE INDEX ON javasampleapproach.customer (firstname);


# View creation 
```
CREATE MATERIALIZED VIEW sample.hotels_by_state AS
    SELECT id, name, address, state, zip FROM hotels
        WHERE state IS NOT NULL AND id IS NOT NULL AND name IS NOT NULL
    PRIMARY KEY ((state), name, id)
    WITH CLUSTERING ORDER BY (name DESC)
    
```


# DAO Layer Logic
```
    @Query("Select * from mmtechwork.example_table where text_field_1=?0")
    List<ExampleTable> findByTextField1(String textField1);
    
```

# DAO Layer Logic With Out Partition Column Should Use allowed Keyword

```
@Query("Select * from mmtechwork.customer where firstname=?0 ALLOW FILTERING")
Customer findByFirstName(String firstName);
	
```

# Table Creation with UUID() Column
```
CREATE TABLE IF NOT EXISTS student (student_id uuid,department_id int,name text,
address text, PRIMARY KEY (student_id,department_id));


```

# Table Insertion using UUID()
```
// As of Cassandra 2.0.7 you can just use uuid() to create a UUID while inserting data.	
insert into  student (student_id,department_id,name,address) values(uuid(),1,'Ava Turner','Marana, Arizona');

```

## Collection DataTypes 

```
Create table Teacher
(
id int,
Name text,
Email set<text>,
Primary key(id)
);

Example: 2

 CREATE TABLE data2 (name text PRIMARY KEY, phone set<varint>);
 
 
```


## Insert into List Collection

```
Example 1:

insert into Teacher(id,Name,Email) values(l,'Guru99',{'abc@gmail.com','xyz@hotmail.com'});


Example 2:
 INSERT INTO data2(name, phone)VALUES ('rahman',    {9848022338,9848022339});


```

## Update the Collection
```
UPDATE data2  SET phone = phone + {9848022330} where name = 'rahman';

```
## Collection Map Example

```
## Collection DataTypes 

Create table Teacher_MAP
(
id int,
Name map<text,text>,
Primary key(id)
);

Example 2:
CREATE TABLE data3 (name text PRIMARY KEY, address map<text, text>);



```

## Insert into Collection Map Example

```
 INSERT INTO data3 (name, address)   VALUES ('robin', {'home' : 'hyderabad' , 'office' : 'Delhi' } );

```

### Update the Map Collection

```
UPDATE data3 SET address = address+{'office':'mumbai'} WHERE name = 'robin';

```
## Drop table 
```
drop table data3
```

## Truncate table 
```
truncate table data3
```

## Describe Cluster
```

 describe cluster;

Cluster: Test Cluster
Partitioner: Murmur3Partitioner

Range ownership:
                    -7749204545882411908  [127.0.0.1]
                     5118743066069148646  [127.0.0.1]
                    -5760020275797864974  [127.0.0.1]
                     5710620650991956686  [127.0.0.1]
                    -5204748592063444658  [127.0.0.1]
                      641973582962189432  [127.0.0.1]
                    -2701529671729050377  [127.0.0.1]
                    

```

## Expand on Operation
```

cqlsh:mmtechwork> expand on;
Now Expanded output is enabled
cqlsh:mmtechwork> select * from data3;

@ Row 1
---------+-----------------------------------------------------------------
 name    | robin
 address | {'area': 'navymumabi', 'home': 'hyderabad', 'office': 'mumbai'}

(1 rows)
cqlsh:mmtechwork>


```

#Alter table KeyspaceName.TableName  +
```

Alter ColumnName TYPE ColumnDataype |
Add ColumnName ColumnDataType |
Drop ColumnName |
Rename ColumnName To NewColumnName |
With propertyName=PropertyValue

```


#Cassandra Query Language(CQL): Insert Into, Update, Delete (Example)


#Insert Query
```  
Insert into KeyspaceName.TableName(ColumnName1, ColumnName2, ColumnName3 . . . .)
values (Column1Value, Column2Value, Column3Value . . . .) 

```

#Upsert Data: 
``` Cassandra does upsert. Upsert means that Cassandra will insert a row if a primary key does not exist already otherwise if primary key already exists, it will update that row.
```

#Update Data
```
Update KeyspaceName.TableName 
Set ColumnName1=new Column1Value,
      ColumnName2=new Column2Value,
      ColumnName3=new Column3Value,
Where ColumnName=ColumnValue
```

# Delete Data

```

Delete from KeyspaceName.TableName	Where ColumnName1=ColumnValue

```
### Command line arguments

It is also possible to pass these configuration settings using command line arguments. Example:


## What Cassandra does not support

```
There are following limitations in Cassandra query language (CQL).

CQL does not support aggregation queries like max, min, avg
CQL does not support group by, having queries.
CQL does not support joins.
CQL does not support OR queries.
CQL does not support wildcard queries.
CQL does not support Union, Intersection queries.
Table columns cannot be filtered without creating the index.
Greater than (>) and less than (<) query is only supported on clustering column.
Cassandra query language is not suitable for analytics purposes because it has so many limitations.

```


##Cassandra Where Clause
```
Select ColumnNames from KeyspaceName.TableName Where ColumnName1=Column1Value AND
	ColumnName2=Column2Value AND
	
```

#Cassandra Create Index

```
Command 'Create index' creates an index on the column specified by the user. If the data already exists for the column you want to index, Cassandra creates indexes on the data during the 'create index' statement execution.

After creating an index, Cassandra indexes new data automatically when data is inserted.
The index cannot be created on primary key as a primary key is already indexed.
Indexes on collections are not supported in Cassandra.
Without indexing on the column, Cassandra can't filter that column unless it is a primary key.
That's why, for filtering columns in Cassandra, indexes needs to be created.

Create index DeptIndex on University.Student(dept);


```

#Drop Index
``` 
Drop index IF EXISTS KeyspaceName.IndexName 

```

# Getting Started with Cassandra on Docker 

```


Step 1. Create Docker Account

Step 2. Installing Docker

Step 3. Pull The Docker Images
		Docker Login
		$ docker login

Step 4. Pull the DataStax Image
The DataStax Server Image is the DataStax distribution of Apache Cassandra with additional capabilities of Search Engine, Spark Analytics and Graph Components (configurable at the docker run step). For quality and simplicity, this is your best bet.
		$ docker pull datastax/dse-server:latest

Step 5 .Pull DataStax Studio Image (Notebook)
		$ docker pull datastax/dse-studio:latest

Step 6. Run The Containers
The -g flag starts a Node with Graph Model enabled
The -s flag starts a Node with Search Engine enabled
The -k flag starts a Node with Spark Analytics enabled
		$> docker run -e DS_LICENSE=accept --memory 4g --name my-dse -d datastax/dse-server -g -s -k

		$> docker run -e DS_LICENSE=accept --link my-dse -p 9091:9091 --memory 1g --name my-studio -d datastax/dse-studio

Step 7. Connecting Studio

```


# ========= Status =========

```
#Active containers
$> docker ps
#Container Utilization
$> docker stats
#Container Details
$> docker inspect my-dse
#NodeTool Status

$> docker exec -it my-dse nodetool status



```



# ========== Logs ==========
```
#Server Logs
$> docker logs my-dse
#System Out
$> docker exec -it my-dse cat /var/log/cassandra/system.log
#Studio Logs
$> docker logs my-studio

```

# ==== Start/Stop/Remove ====

```
#Start Container
$> docker start my-dse
#Stop Container
$> docker stop my-dse
#Remove Container
$> docker remove my-dse

```

# ======= Additional =======

```
#Contaier IPAddress
&> docker inspect my-dse | grep IPAddress
#CQL (Requires IPAddress from above)
$> docker exec -it my-dse cqlsh [IPAddress]
#Bash
$> docker exec -it my-dse bash
DockerCassandraDatastaxNoSQLApache Spark

```

```bash
$ java -Djava.security.egd=file:/dev/./urandom -jar app.jar \
    --spring.data.cassandra.contact-points=assandra_contact_point1,cassandra_contact_point2 \
    --spring.data.cassandra.keyspace-name=keyspace-name
``` 




### How to run

### Maven

open a terminal and run the following commands to ensure that you have valid versions of Java and Maven installed:

```bash
$ java -version
java version "1.8.0_102"
Java(TM) SE Runtime Environment (build 1.8.0_102-b14)
Java HotSpot(TM) 64-Bit Server VM (build 25.102-b14, mixed mode)
```

```bash
$ mvn -v
Apache Maven 3.3.9 (bb52d8502b132ec0a5a3f4c09453c07478323dc5; 2015-11-10T16:41:47+00:00)
Maven home: /usr/local/Cellar/maven/3.3.9/libexec
Java version: 1.8.0_102, vendor: Oracle Corporation
```


#### Using the Maven Plugin

The Spring Boot Maven plugin includes a run goal that can be used to quickly compile and run your application. 
Applications run in an exploded form, as they do in your IDE. 
The following example shows a typical Maven command to run a Spring Boot application:
 
```bash
$ mvn spring-boot:run
``` 

#### Using Executable Jar

To create an executable jar run:

```bash
$ mvn package
``` 

To run that application, use the java -jar command, as follows:

```bash
$ java -jar target/spring-boot-cassandra-0.0.1-SNAPSHOT.jar
```


Application UR
## Tests

Tests can be run by executing following command from the root of the project:

```bash
$ mvn test
```