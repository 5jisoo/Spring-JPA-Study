package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Member {

    @Id
    private Long id;

    @Column(name = "name")          // DB에는 name이라고 지정
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)    // DB에서 Enum타입을 구현하기 위해 사용하는 애노테이션
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)   // TemporalType에는 DATE, TIME, TIMESTAMP가 있음. - 날짜 정보 매핑
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob                                // BLOB, CLOB 매핑 - 큰 컨텐츠를 넣고 싶은 경우.
    private String description;

    @Transient      // DB에서 관리하지 말아달라는 뜻
    private int temp;

    public Member() {    // 동적인 객체 생성을 위한 기본 생성자.

    }


}
