package efub.assignment.community.account.repository;

import efub.assignment.community.account.domain.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE, connection = EmbeddedDatabaseConnection.H2)
public class AccountRepository {

    @Autowired
    private efub.assignment.community.account.AccountRepository accountRepository;

    private Account account1;

    @BeforeEach
    void setUp(){
        account1 = Account.builder()
                .email("mentos@mail.com")
                .password("watch1234")
                .nickname("익명1")
                .university("와플대학")
                .studentId("111111")
                .build();

    }

    //성공 1
    @Test
    public void 계정_생성_테스트(){
        //given
        //when
        Account createdAccount = accountRepository.save(account1);
        //then
        assertEquals("와플대학" , createdAccount.getUniversity());
    }

    //실패 1
    @Test
    public void 닉네임_계정_찾기(){
        //given
        //when
        Account createdAccount = accountRepository.save(account1);
        Boolean isExist = accountRepository.existsByNickname(createdAccount.getNickname());
        //then
        assertThat(isExist).isFalse();
    }



}
