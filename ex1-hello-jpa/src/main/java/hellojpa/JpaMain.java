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

        EntityTransaction tx = em.getTransaction(); //트랜잭션을 얻음.
        tx.begin();                                 //트랜잭션 시작


        // 정석으로는 이렇게 try, catch문을 사용해야 함.
        try {
//            // 회원 등록
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("HelloB");
//            em.persist(member);

//            Member findMember = em.find(Member.class, 1L);

//            // 조회 확인
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.name = " + findMember.getName());

//            //회원 삭제
//            em.remove(findMember);

//            // 회원 수정
//            findMember.setName("HelloJPA");


            // 실습 - JPQL 소개

//            // 전체 회원 조회
//            // 이렇게 쿼리를 칠 수 있음.
//            List<Member> result = em.createQuery("select m from Member as m", Member.class).getResultList(); // 멤버 객체를 모두 가져와라!
//            for (Member member : result){
//                System.out.println("member.name = " + member.getName());
//            }

            // 페이징을 하고 싶은 경우
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)      // 1번부터
                    .setMaxResults(10)      // 10개 가져와!
                    .getResultList();
            for (Member member : result){
                System.out.println("member.name = " + member.getName());
            }



//            em.persist(findMember); // 회원 수정 시, 이렇게 저장 안해도 됨! - 자바 컬렉션을 다루는 것처럼 설계되었기 때문.

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();     // 사용 후 닫아주는 게 중요
        }
        emf.close();
    }
}
