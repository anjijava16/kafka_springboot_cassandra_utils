# ProjectName: cqss_mmtech_works
#  Cassandra Database
 ``` 
 CREATE KEYSPACE “mmtechsoftworkd”
WITH replication = {'class': ‘Strategy name’, 'replication_factor' : ‘No.Of   replicas’};
 ```

 #  Cassandra Employee
 
 CREATE TABLE employee(
    		  id int PRIMARY KEY,
    		  firstName text,
    		  lastName text,
    		  emailId text
    		);
    		
