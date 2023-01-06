package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setUsername("member" + i);
                member.setAge(i);
                em.persist(member);
            }

            em.flush();     // 영속성 컨텍스트 clear
            em.clear();

            List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
            Member findMember = result.get(0);
            findMember.setAge(20);

            List<Team> resultList = em.createQuery("select m.team from Member m", Team.class).getResultList();

            List<Address> orders = em.createQuery("select o.address from Order o", Address.class)
                    .getResultList();

            List list = em.createQuery("select distinct m.username, m.age from Member m")
                    .getResultList();

            Object o = list.get(0);
            Object[] os = (Object[]) o;
            System.out.println("username = " + os[0]);
            System.out.println("age = " + os[1]);


            List<MemberDTO> memberDTOList = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();
            MemberDTO memberDTO = memberDTOList.get(0);
            System.out.println("memberDTO.username = " + memberDTO.getUsername());
            System.out.println("memberDTO.age = " + memberDTO.getAge());

            List<Member> members = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10).getResultList();
            System.out.println("result.size() = " + result.size());
            for (Member member1 : members) {
                System.out.println("member1 = " + member1);
            }

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
