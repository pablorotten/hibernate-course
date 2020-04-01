package com.infiniteskills.data;

import java.util.Calendar;
import java.util.Date;

import com.infiniteskills.data.entities.TimeTest;
import org.hibernate.Session;
import com.infiniteskills.data.entities.User;

public class Application {

  public static void main(String[] args) {
    Session session = HibernateUtil.getSessionFactory().openSession();

    try {
//      timeTest(session);
//      userTest(session);
      userAgeTest(session);

    } catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }finally{
      session.close();
      HibernateUtil.getSessionFactory().close();
      System.exit(0);
    }

  }

  public static void userTest(Session session){
    session.getTransaction().begin();

    User user = new User();
    user.setBirthDate(getMyBirthday());
    user.setCreatedBy("kevin");
    user.setCreatedDate(new Date());
    user.setEmailAddress("kmb385@yahoo.com");
    user.setFirstName("Kevin");
    user.setLastName("Bowersox");
    user.setLastUpdatedBy("kevin");
    user.setLastUpdatedDate(new Date());

    session.save(user);
  }

  public static void userAgeTest(Session session){
    session.getTransaction().begin();

    User user = new User();
    user.setBirthDate(getMyBirthday());
    user.setCreatedBy("kevin");
    user.setCreatedDate(new Date());
    user.setEmailAddress("kmb385@yahoo.com");
    user.setFirstName("Kevin");
    user.setLastName("Bowersox");
    user.setLastUpdatedBy("kevin");
    user.setLastUpdatedDate(new Date());

    session.save(user);
    session.getTransaction().commit();

    session.refresh(user);

    System.out.println(user.getAge());
  }

  private static Date getMyBirthday(){
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.YEAR, 1984);
    calendar.set(Calendar.MONTH, 6);
    calendar.set(Calendar.DATE, 19);
    return calendar.getTime();
  }

  public static void timeTest(Session session){
    session.getTransaction().begin();
    TimeTest test = new TimeTest(new Date());
    session.save(test);
    session.getTransaction().commit();
    session.refresh(test);
    System.out.println(test.toString());
  }
}
