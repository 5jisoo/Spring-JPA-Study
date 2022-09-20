package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
// @Table(name="USER") // 만약 DB에 테이블 이름이 USER라고 되어있다면 이렇게 설정.
public class Member {

    @Id
    private Long id;

    //@Column(name="username") // 만약에 column이름이 username으로 되어있다면 이렇게 설정하면 됨.
    private String name;

    public Member(){    // 동적인 객체 생성을 위한 기본 생성자.

    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
