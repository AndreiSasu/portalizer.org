package org.portalizer.service.mapper;

import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.portalizer.PortalizerApp;
import org.portalizer.domain.Board;
import org.portalizer.domain.User;
import org.portalizer.service.dto.BoardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PortalizerApp.class)
public class BoardMapperIT {

    private BoardMapper boardMapper;
    private UserMapper userMapper;

    @Autowired
    public BoardMapperIT(BoardMapper boardMapper, UserMapper userMapper) {
        this.boardMapper = boardMapper;
        this.userMapper = userMapper;
    }

    private User user;
    private Board board;

    @BeforeEach
    private void setup() {
        user = new User();
        user.setLogin("nobody");
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail("johndoe@localhost");
        user.setFirstName("john");
        user.setLastName("doe");
        user.setImageUrl("image_url");
        user.setLangKey("en");

        board = new Board();
        board.setOwner(user);
    }

    @Test
    public void testOwnerDtoFromBoardDto() {
        final BoardDTO boardDTO = boardMapper.toDto(board);
        Assertions.assertThat(boardDTO.getOwner()).isEqualTo(userMapper.toDto(user));
    }
}
