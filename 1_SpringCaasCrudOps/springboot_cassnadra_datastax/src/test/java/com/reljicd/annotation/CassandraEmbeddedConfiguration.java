//package com.reljicd.annotation;
//	
//import java.lang.annotation.Documented;
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Inherited;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//import org.cassandraunit.spring.CassandraDataSet;
//import org.cassandraunit.spring.CassandraUnitDependencyInjectionTestExecutionListener;
//import org.cassandraunit.spring.EmbeddedCassandra;
//import org.springframework.test.context.TestExecutionListeners;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
//
//@Target({ElementType.TYPE})
//@Retention(RetentionPolicy.RUNTIME)
//@Documented
//@Inherited
//@TestExecutionListeners(listeners = {
//        CassandraUnitDependencyInjectionTestExecutionListener.class,
//        DependencyInjectionTestExecutionListener.class}
//)
//@TestPropertySource(locations = "classpath:test.properties")
//@EmbeddedCassandra
//@CassandraDataSet(keyspace = "test_keyspace", value = {"dataset.cql"})
//public @interface CassandraEmbeddedConfiguration {
//}