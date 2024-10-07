package efub.assignment.community.post.repository;

import efub.assignment.community.account.AccountRepository;
import efub.assignment.community.account.domain.Account;
import efub.assignment.community.board.BoardRepository;
import efub.assignment.community.board.domain.Board;
import efub.assignment.community.post.domain.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE, connection = EmbeddedDatabaseConnection.H2)
public class PostRepository {

    private Account account1;
    private Board board1;
    private Post post1;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    efub.assignment.community.post.PostRepository postRepository;

    // account  , board , post
    @BeforeEach
    private void set(){
        account1 = Account.builder()
                .email("mentos@mail.com")
                .password("watch1234")
                .nickname("익명1")
                .university("와플대학")
                .studentId("1234567")
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
                .writerOpen("아 이거 실수")
                .title("딘에 관하여")
                .build();

        Account savedAccount = accountRepository.save(account1);
        Board savedBoard = boardRepository.save(board1);
    }

    //성공 1
    @Test
    public void 게시물_저장_테스트(){
        Post savedPost = postRepository.save(post1);

        assertEquals("딘에 관하여" , savedPost.getTitle());
    }

    //실패 1
    @Test
    public void 게시물_존재_여부(){
        Post savedPost = postRepository.save(post1);

        Boolean isExist = postRepository.existsByTitle(savedPost.getTitle());

        assertThat(isExist).isFalse();
    }
}
