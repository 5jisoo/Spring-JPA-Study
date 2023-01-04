package hellojpa;

import hellojpa.section7.AddressEntity;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
//            // 여기서 Member는 테이블이 아닌 Member Entity를 가리키는 것.
//            List<Member> result = em.createQuery(
//                    "select m from Member m where m.username like '%kim%'",
//                    Member.class
//            ).getResultList();
//
//            for (Member member : result) {
//                System.out.println("member = " + member);
//            }


//            //Criteria 사용 준비
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//            CriteriaQuery<Member> query = cb.createQuery(Member.class);
//
//            //루트 클래스 (조회를 시작할 클래스)
//            Root<Member> m = query.from(Member.class);
//
//            //쿼리 생성 CriteriaQuery<Member>
//            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "kim"));
//            List<Member> resultList = em.createQuery(cq).getResultList();

            // JPQL
            // select m from Member m where m.age > 18
//            JPAFactoryQuery query = new JPAQueryFactory(em);
//            QMember m = QMember.member;
//            List<Member> list =
//                    query.selectFrom(m)
//                            .where(m.age.gt(18))
//                            .orderBy(m.name.desc())
//                            .fetch();


            String sql = "SELECT MEMBER_ID, city, street, zipcode, USERNAME FROM MEMBER";
            List<Member> resultList =
                    em.createNativeQuery(sql, Member.class).getResultList();



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
