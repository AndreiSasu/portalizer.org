package org.portalizer.service.insights;

import org.portalizer.repository.BoardRepository;
import org.portalizer.repository.InformationCardRepository;
import org.portalizer.repository.UserRepository;
import org.portalizer.service.dto.CounterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CounterService {

    private UserRepository userRepository;
    private BoardRepository boardRepository;
    private InformationCardRepository informationCardRepository;

    @Autowired
    public CounterService(UserRepository userRepository, BoardRepository boardRepository, InformationCardRepository informationCardRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.informationCardRepository = informationCardRepository;
    }

    public List<CounterDTO> getOverAllCounters() {
        final long totalUsers = userRepository.count();
        final long totalBoards = boardRepository.count();
        final long totalCards = informationCardRepository.count();

        return Arrays.asList(new CounterDTO("Total Users", totalUsers),
            new CounterDTO("Total Boards", totalBoards),
            new CounterDTO("Total Cards", totalCards));
    }
}
