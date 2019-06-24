-- Cassandra Schema 
-- mmtechwork

use mmtechwork;
CREATE TABLE employees( id bigint PRIMARY KEY, firstname TEXT,lastname TEXT,emailId TEXT );
INSERT INTO employees (id, firstname,lastname,emailid) VALUES (2, 'Anji','m','anjaiahspr@gmail.com');
INSERT INTO employees (id, firstname,lastname,emailid) VALUES (3, 'Anji','m','anjaiahspr@gmail.com');
select * from employees;