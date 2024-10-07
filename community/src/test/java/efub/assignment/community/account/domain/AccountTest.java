package efub.assignment.community.account.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class) // 스프링 프로젝트의 테스트 확장 모델
@WebMvcTest(Account.class) //WEB MVC 관련 컴포넌트 로드
@MockBean(JpaMetamodelMappingContext.class) // 테스트 환경에서 실제 데이터베이스와 상호작용없이 테스트할 수 있게 함
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) //테스트 메서드의 언더스코어를 공백으로 대체
public class AccountTest {

    String email = "mentos@mail.com";
    String password = "watch1234";
    String nickname = "익명1";
    String university = "와플대학";

    // account
    private Account makeAccount(){
        Account account1 = Account.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .university(university)
                .build();
        return account1;
    }

    // 성공하는 경우
    @Test
    public void 계정_생성_테스트(){
        //given

        //when
        Account account1 = makeAccount();

        //then
        assertEquals(email , account1.getEmail());
        assertEquals(password , account1.getPassword());
        assertEquals(nickname , account1.getNickname());
        assertEquals(university , account1.getUniversity());
    }


    // 실패하는 경우
    @Test
    public void 잘못된_계정_상태_조회(){
        //given
        //when
        Account account1 = makeAccount();
        //then
        assertEquals(AccountStatus.UNREGISTERED, account1.getStatus());
    }
}
