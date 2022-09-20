package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//
//            // 비영속
//            Member member = new Member();
//            member.setId(101L);
//            member.setName("HelloJPA");
//
//            // 영속 상태
//           em.persist(member);

//            // 회원 엔티티를 영속성 컨텍스트에서 분리, 준영속 상태
//            em.detach(member);
//
//            // 객체를 상태한 상태 (삭제)
//            em.remove(member);

//            Member findMember1 = em.find(Member.class, 101L);       // 이땐 쿼리가 나가야 됨.
//            Member findMember2 =  em.find(Member.class, 101L);      // 이땐 쿼리가 나가면 안됨 .:: 1차 캐시에서 조회해야 함.

//            // 영속
//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "B");
//
//            em.persist(member1);
//            em.persist(member2);    //영속성 컨테스트에 엔티티와 쿼리가 차곡차곡 쌓임.

            // 영속 상태
            Member member = em.find(Member.class, 150L);
            member.setName("AAAAA");

            em.detach(member);       // commit 할 때 아무 일도 일어나지 않음. - update 쿼리가 나가지 않는다!

//            Member member = new Member(200L, "member200");
//            em.persist(member);

            em.flush();     // 강제 호출

            System.out.println("=========================");

            tx.commit();    // 이때 쿼리가 날아가면서 DB에 저장됨.

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
