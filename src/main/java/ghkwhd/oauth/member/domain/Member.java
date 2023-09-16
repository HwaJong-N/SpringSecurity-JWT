package ghkwhd.oauth.member.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long no;
    private String id;
    private String name;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    public Member() {

    }

    @Builder
    public Member(String id, String name, String password, String email, MemberRole role, SocialType socialType) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.socialType = socialType;
    }

    // Member 가 생성되기 이전 DTO 로 User 를 생성할 때 사용
    // 비밀번호 암호화까지 동시에 수행
    public static Member createUser(MemberDTO dto, PasswordEncoder passwordEncoder) {
        Member member = Member.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))    // 암호화해서 User 생성
                .role(MemberRole.USER)    // 역할 지정
                .build();
        return member;
    }
}
