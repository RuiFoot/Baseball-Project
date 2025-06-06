package io.github.ruifoot.core.service.baseball;

import io.github.ruifoot.domain.repository.TeamRepository;
import io.github.ruifoot.domain.service.baseball.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
}
