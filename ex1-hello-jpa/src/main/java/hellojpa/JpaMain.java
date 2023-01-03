package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.WeakHashMap;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Address address = new Address("city", "street", "10000");
            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());

            Member member1 = new Member();
            member1.setUsername("hello");
            member1.setHomeAddress(address);

            Member member2 = new Member();
            member2.setUsername("hello");
            member2.setHomeAddress(copyAddress);    // 동일한 address 를 사용하고 있음

//            member1.getHomeAddress().setCity("newCity");      // 이렇게 값 변경이 불가능해지면서 side effect를 막는다!

            // 새로운 값으로 변경하고 싶다면
            Address newAddress = new Address("newCity", address.getStreet(), address.getZipcode());
            member1.setHomeAddress(newAddress);

            em.persist(member1);
            em.persist(member2);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}
