# MyGCD

# Project tech stack involves 

Java 8,

Maven 3.5,

Spring 4.1.4,

Hibernate 4.1.9,

MySQL 5 +,

ActiveMQ 5.15.2,

JBOSS AS 7.0.0 Final

The system supports 20 concurrent users at any point of time.

# Deployment methods:

Download the zip package from git and unzip.

After unzipping, you should be able to view four projects

• gcdcommon

• gcdrest

• gcdsoap

• gcdear


Goto MyGCD-master folder and run from cmd prompt "mvn clean install -DskipTests=true".

.ear file will be generated in gcdear project's target folder.

Install JBOSS AS 7 into any folder of your machine. Let that folder be your jboss_home folder.

Place the .ear file in jboss_server_home/standalone/deployments folder.

Start the server by going to jboss_home/bin and type standalone.bat

## Database setup

Download MySQL 5.x and log in as a root user. 

Modify the database schema/username/password in the GCDService project -> src/resource/jdbc.properties 

Create the following tables using the DDL commands.

CREATE TABLE `gcd`.`gcd` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `userkey` varchar(52) DEFAULT NULL,
  `gcd` int(12) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

CREATE TABLE `gcd`.`inputnumber` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `userkey` varchar(52) DEFAULT NULL,
  `number1` int(11) DEFAULT NULL,
  `number2` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=191 DEFAULT CHARSET=utf8;


The userkey column in each database will hold the user's generated session key. 
This key will be used in the application's session to check the number of concurrent users in the system.

## ActiveMQ setup

Download ActiveMQ from http://activemq.apache.org/activemq-5152-release.html

Extract the folder and goto bin folder

type "activemq start"

The project exposes two APIS - one is a restful webservice and the other is SOAP webservice.

Restful webservice exposes two apis - 

•	push-	which returns the status of the request to the caller as a String. The two parameters will be added to a JMS queue.

• list-	which returns a list of all the elements ever added to the queue from a database in the order added as a JSON structure. 

SOAP webservice exposes three apis

•	gcd- which returns the greatest common divisor* of the two integers at the head of the queue. These two elements will subsequently be discarded from the queue and the head replaced by the next two in line.

•	gcdList- which returns a list of all the computed greatest common divisors from a database. 

•	gcdSum- which returns the sum of all computed greatest common divisors from a database.

and type the following URL

For local deployment:

### Rest services

http://localhost:8080/gcdrest/rest//list

http://localhost:8080/gcdrest/rest/push (POST request with "key:"anystring" passed as a header)

(Example: http://localhost:8080/gcdrest/rest/push?i1=20&i2=16 (request header key:abcdefg. This is optional, in which case each POST will be considered as a new user request)

### Soap Services

http://localhost:8080/gcdsoap/webservices/gcdService.wsdl

The GCD soapservice returns a zero when there are no more elements in queue to be consumed.
