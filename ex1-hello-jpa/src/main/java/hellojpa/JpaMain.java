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

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            // 저장
            Member member = new Member();
            member.setUsername("member1");
//            member.changeTeam(team);   // 이런식으로 Member에서 team을 설정해주어야 함!

            team.addMember(member);
            em.persist(member);


            em.flush();
            em.clear();

            Team findTeam = em.find(Team.class, team.getId());
            System.out.println("====================");
            System.out.println("members = " + findTeam);
            System.out.println("====================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
