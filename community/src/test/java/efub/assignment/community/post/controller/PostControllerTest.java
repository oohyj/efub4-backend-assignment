package efub.assignment.community.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import efub.assignment.community.CommunityApplication;
import efub.assignment.community.post.PostRepository;
import efub.assignment.community.post.dto.PostRequestDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/data.sql")
@ActiveProfiles("test")
@ContextConfiguration(classes = CommunityApplication.class)
@TestPropertySource(locations = "classpath:application-test.yml")
public class PostControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected ObjectMapper objectMapper; // 직렬화 , 역직렬화를 위한 클래스

    @Autowired
    protected PostRepository postRepository;

    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @Test
    @DisplayName("createdPost : 게시글 만들기 성공")
    public void createdPost() throws Exception{
        /* given */
        final String url = "/posts";
        final Long boardId = 1L;
        final String writerNickname = "aespa";
        final String title = "토레타가 좋다";
        final String content = "항상 1+1 원해요";
        final boolean writerOpen = true;

        PostRequestDto requestDto = createDefaultPost(boardId , writerNickname , title , content , writerOpen);

        /* when */
        String requestBody = objectMapper.writeValueAsString(requestDto);

        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        /* then */
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.postId").isNotEmpty())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));
    }

    @Test
    @DisplayName("failCreatedPost : 게시글 만들기 실패") // 없는 게시판에 글 올리기
    public void failCreatedPost() throws Exception{
        /* given */
        final String url = "/posts";
        final Long boardId = 2L;
        final String writerNickname = "푸하하하";
        final String title = "흑임자";
        final String content = "항상 1+1 원해요";
        final boolean writerOpen = true;

        PostRequestDto requestDto = createDefaultPost(boardId , writerNickname , title , content , writerOpen);

        /* when */
        String requestBody = objectMapper.writeValueAsString(requestDto);

        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        /* then */
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.postId").isNotEmpty())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content));
    }

    private PostRequestDto createDefaultPost(Long boardId, String writerNickname, String title, String content, boolean writerOpen) {
        return new PostRequestDto(boardId, writerNickname, title, content, writerOpen);
    }



}
