package hellojpa;

import hellojpa.section7.AddressEntity;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

//            member.getAddressHistory().add(new Address("old1", "street", "10000"));
//            member.getAddressHistory().add(new Address("old2", "street", "10000"));
            member.getAddressHistory().add(new AddressEntity("old1", "street", "10000"));
            member.getAddressHistory().add(new AddressEntity("old1", "street", "10000"));

            em.persist(member);

            em.flush();
            em.clear();

            // 깔끔하게 다시 조회
            System.out.println("================= START =====================");
            Member findMember = em.find(Member.class, member.getId());

            // homeCity -> newCity
//            findMember.getHomeAddress().setCity("newCity");   // X
            Address a = findMember.getHomeAddress();
            // 이런식으로 완전히 새로운 객체로 교체하자!!
            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));

            // 값 타입 컬렉션 수정 : 치킨 => 한식
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");    // 이런식으로 삭제하고 add 갈아끼워야 함.


            // 값 타입 컬렉션 수정 : old1을 new Address로
                // remove는 equals를 사용. 완전히 똑같은 객체를 넘겨주면 됨. :: 이래서 equals() override가 필수적인것!
//            findMember.getAddressHistory().remove(new Address("old1", "street", "10000"));
//            findMember.getAddressHistory().add(new Address("newCity1", "street", "10000"));


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
