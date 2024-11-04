package efub.assignment.community.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import efub.assignment.community.CommunityApplication;
import efub.assignment.community.account.AccountRepository;
import efub.assignment.community.account.dto.SignUpRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/data.sql")
@ActiveProfiles("test")
@ContextConfiguration(classes = CommunityApplication.class)
@TestPropertySource(locations = "classpath:application-test.yml")
public class AccountControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected ObjectMapper objectMapper; // 직렬화 , 역직렬화를 위한 클래스

    @Autowired
    protected AccountRepository accountRepository;

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }


    @Test
    @DisplayName("createAccount : 회원가입 성공")
    public void createAccount() throws Exception{
        /* given */
        final String url = "/accounts";
        final String email = "toreta@cocacola.com";
        final String password = "zeroIsGood123!";
        final String nickname = "토레타가 좋다";
        final String university = "EwhaWomansUniversity";
        final String studentId = "2020111";
        SignUpRequestDto requestDto= createDefaultSignUpRequestDto(email , password , nickname , university , studentId);

        /* when */
        String requestBody = objectMapper.writeValueAsString(requestDto); // 객체를 JSON으로 직렬화

        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)); // 설정 내용으로 요청

        /* then */
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").isNotEmpty()) //응답 dto의 accountId가 null이 아니라면 통과
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.nickname").value(nickname));

    }


    @Test
    @DisplayName("failCreateAccount : 회원가입 실패") //동일한 이메일로 가입시 예외처리
    public void failCreateAccount() throws Exception{
        /* given */
        final String url = "/accounts";
        final String email = "whiplash@mail.com";
        final String password = "backinthedays1104!";
        final String nickname = "aespa";
        final String university = "EwhaWomansUniversity";
        final String studentId = "2020111";
        SignUpRequestDto requestDto= createDefaultSignUpRequestDto(email , password , nickname , university , studentId);

        /* when */
        String requestBody = objectMapper.writeValueAsString(requestDto); // 객체를 JSON으로 직렬화

        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)); // 설정 내용으로 요청

        /* then */
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").isNotEmpty()) //응답 dto의 accountId가 null이 아니라면 통과
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.nickname").value(nickname));

    }

    @Test
    @DisplayName("failGetAccount : 없는 회원 조회") //없는 회원 조회 현재 2번 회원밖에 없음
    public void failGetAccount() throws Exception{
        /* given */
        final String url = "/accounts/3";

        /* when */
        ResultActions resultActions = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON));

        /* then */
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").isNotEmpty()); //응답 dto의 accountId가 null이 아니라면 통

    }




    private SignUpRequestDto createDefaultSignUpRequestDto(String email , String password , String nickname , String university , String studentId){
        return SignUpRequestDto.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .university(university)
                .studentId(studentId)
                .build();
    }

}
