package org.portalizer.setup;

import org.portalizer.domain.BoardTemplate;
import org.portalizer.repository.BoardTemplateRepository;
import org.portalizer.service.mapper.BoardTemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SetupDefaultBoardTemplates implements ApplicationRunner {

    private final BoardTemplateRepository boardTemplateRepository;
    private final BoardTemplateMapper boardTemplateMapper;

    private static final Logger logger = LoggerFactory.getLogger(SetupDefaultBoardTemplates.class);


    public SetupDefaultBoardTemplates(BoardTemplateRepository boardTemplateRepository, BoardTemplateMapper boardTemplateMapper) {
        this.boardTemplateRepository = boardTemplateRepository;
        this.boardTemplateMapper = boardTemplateMapper;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        DefaultBoardTemplates.get().forEach(boardTemplateDTO -> {
            if(!boardTemplateRepository.existsByTitle(boardTemplateDTO.getTitle())) {
                final BoardTemplate boardTemplate = boardTemplateMapper.toEntity(boardTemplateDTO);
                logger.info("Saving default board template: {}", boardTemplate.getTitle());
                boardTemplate.getColumnDefinitions().forEach(boardTemplateColumnDefinition -> {
                    boardTemplateColumnDefinition.setBoardTemplate(boardTemplate);
                });
                boardTemplateRepository.save(boardTemplate);
            }
        });


    }
}
