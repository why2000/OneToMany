package com.week3.onetomany;


import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;


import javax.persistence.*;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class OneToManyApplication {

  private DataSource getDataSource() {
    final PGSimpleDataSource dataSource = new PGSimpleDataSource();
    //        dataSource.setDatabaseName("OrmDemo");
    dataSource.setUser("why");
    dataSource.setPassword("why");
    dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
    return dataSource;
  }

  private Properties getProperties() {
    final Properties properties = new Properties();
    properties.put( "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect" );
    properties.put( "hibernate.connection.driver_class", "org.postgresql.Driver" );
    return properties;
  }

  private EntityManagerFactory entityManagerFactory(DataSource dataSource, Properties hibernateProperties ){
    final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setPackagesToScan("com/week3/onetomany");
    em.setJpaVendorAdapter( new HibernateJpaVendorAdapter() );
    em.setJpaProperties( hibernateProperties );
    em.setPersistenceUnitName( "demo-unit" );
    em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
    em.afterPropertiesSet();
    return em.getObject();
  }

  public static void main(String[] args) {
    OneToManyApplication application = new OneToManyApplication();
    DataSource dataSource = application.getDataSource();
    Properties properties = application.getProperties();
    EntityManagerFactory entityManagerFactory = application.entityManagerFactory(dataSource, properties);
    EntityManager em = entityManagerFactory.createEntityManager();
    PersistenceUnitUtil unitUtil = entityManagerFactory.getPersistenceUnitUtil();
    //insertToManager(em, "SomeManager1");
    //insertToCustomer(em, "SomeCustomer1", 1L);
    getManagerById(em, 1L);
    getCustomerById(em, 1L);
    removeManagerById(em, 1L);
  }

  /**
   * one Manager to many Customer
   * a simple example of CRU for Customer, CRD for Manager
   */

  private static void insertToManager(EntityManager em, String name) {
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    Manager manager = new Manager();
    manager.setName(name);
    em.merge(manager);
    tx.commit();
  }

  private static void getManagerById(EntityManager em, Long mid) {
    Query query = em.createQuery("select m from Manager m where m.id = ?1");
    query.setParameter(1, mid);
    Manager m = (Manager) query.getSingleResult();
    System.out.println(m.getName());
  }

  private static void removeManagerById(EntityManager em, Long mid){
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    Query query = em.createQuery("select m from Manager m where m.id = ?1");
    query.setParameter(1, mid);
    Manager m = (Manager) query.getSingleResult();
    em.remove(m);
    tx.commit();

  }


  private static void insertToCustomer(EntityManager em, String name, Long mid) {
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    Query query = em.createQuery("select m from Manager m where m.id = ?1");
    query.setParameter(1, mid);
    Manager m = (Manager) query.getSingleResult();
    Customer customer = new Customer();
    customer.setName(name);
    customer.setManager(m);
    em.merge(customer);
    tx.commit();
  }

  private static void updateCustomerById(EntityManager em, Long cid, String name, Long mid) {
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    Query query = em.createQuery("select c from Customer c where c.id = ?1");
    query.setParameter(1, cid);
    Customer customer = (Customer) query.getSingleResult();
    customer.setName(name);
    Query query2 = em.createQuery("select m from Manager m where m.id = ?2");
    query2.setParameter(2, mid);
    Manager manager = (Manager) query.getSingleResult();
    if(manager.getName().length() > 0)
      customer.setManager(manager);
    em.merge(customer);
    tx.commit();
  }

  private static void getCustomerById(EntityManager em, Long cid) {
    Query query = em.createQuery("select c from Customer c where c.id = ?1");
    query.setParameter(1, cid);
    Customer c = (Customer) query.getSingleResult();
    System.out.println(c.getName());
  }


}
