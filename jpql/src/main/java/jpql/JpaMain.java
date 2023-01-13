package jpql;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team1 = new Team();
            team1.setName("teamA");

            Team team2 = new Team();
            team2.setName("teamB");

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(team1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(team1);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(team2);


            em.persist(team1);
            em.persist(team2);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            em.flush();     // 영속성 컨텍스트 clear
            em.clear();

            String query = "select t From Team t join fetch t.members as m";
            List<Team> teams = em.createQuery(query, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(1)
                    .getResultList();

            for (Team team : teams) {
                System.out.println("team.getName() = " + team.getName() + "  |  members = " + team.getMembers().size());
                for (Member member : team.getMembers()) {
                    System.out.println("==> member.getUsername() = " + member.getUsername());
                }
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
