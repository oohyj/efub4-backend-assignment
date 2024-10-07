package efub.assignment.community.post.domain;

import efub.assignment.community.account.domain.Account;
import efub.assignment.community.board.domain.Board;
import efub.assignment.community.post.dto.PostUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) // 스프링 프로젝트의 테스트 확장 모델
@WebMvcTest(Account.class) //WEB MVC 관련 컴포넌트 로드
@MockBean(JpaMetamodelMappingContext.class) // 테스트 환경에서 실제 데이터베이스와 상호작용없이 테스트할 수 있게 함
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) //테스트 메서드의 언더스코어를 공백으로 대체
public class PostTest {

    private Account account1;
    private Board board1;
    private Post post1;


    // account  , board , post
    @BeforeEach
    private void set(){
        account1 = Account.builder()
                .email("mentos@mail.com")
                .password("watch1234")
                .nickname("익명1")
                .university("와플대학")
                .build();

        board1 = Board.builder()
                .account(account1)
                .boardDescription("믿었었어... 바빴다면서... 노래 더 내주라")
                .boardName("What 2 do")
                .boardNotice("Dean을 아시나요")
                .build();

        post1 = Post.builder()
                .account(account1)
                .content("아뇨 딘 모릅니다. 누구죠")
                .board(board1)
                .writerOpen("아 이거 boolean으로 해야하는데")
                .title("딘에 관하여")
                .build();
    }

    //성공 1
    @Test
    void 게시물_생성_테스트(){
        //given
        //when
        //then
        assertNotNull(post1);
    }

    //실패1
    @Test
    void 게시물_수정_테스트(){
        //given
        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .content("attention~~")
                .title("new jeans을 아시나요")
                .build();
        //when
        post1.update(postUpdateDto);
        //then
        assertEquals("딘에 관하여" , post1.getTitle());
    }


}
