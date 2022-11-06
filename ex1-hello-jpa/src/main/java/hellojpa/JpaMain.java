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

            // 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);   // 이렇게 하면 알아서 JPA가 team의 key를 member에 FK로 insert해줌
            em.persist(member);

            // 새로운 팀B
            Team teamB = new Team();
            teamB.setName("TeamB");
            em.persist(teamB);

            member.setTeam(teamB);

            // 멤버 조회
            Member findMember = em.find(Member.class, member.getId());

            Team findTeam = findMember.getTeam();   // 이런식으로 바로 끄집어낼 수 있다!

            // 확인용
            System.out.println("findTeam = " + findTeam.getName());



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
