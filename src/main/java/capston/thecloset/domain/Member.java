package capston.thecloset.domain;

import capston.thecloset.service.MemberInputDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_Id")
    private Long userSn; //식별자

    @Column(unique = true)
    private String userId; //로그인아이디
    private String userName;
    private String email;
    private String password;


    @Column(name = "RegDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime regdt;//회원정보 등록

    @Column(name = "UptDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime udtDt;//회원정보 수정

    @Enumerated(EnumType.STRING)
    private Role role;

    //createMember를 위한 생성자 , 다른 클래스에서 호출 불가능
    private Member(String userId, String userName, String email,String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = Role.USER;
        this.regdt = LocalDateTime.now();
    }

    //dto -> entity
    public static Member createMember(MemberInputDto parameter){
        String encPassword= BCrypt.hashpw(parameter.getPassword(),BCrypt.gensalt());
        return new Member(parameter.getUserId(),parameter.getUserName(),parameter.getEmail(),encPassword);
    }
}
