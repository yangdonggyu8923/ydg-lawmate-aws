package site.lawmate.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.lawmate.user.component.Messenger;
import site.lawmate.user.domain.dto.IssueDto;
import site.lawmate.user.domain.model.Issue;
import site.lawmate.user.domain.model.User;
import site.lawmate.user.repository.IssueRepository;
import site.lawmate.user.repository.UserRepository;
import site.lawmate.user.service.IssueService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
@Service
@RequiredArgsConstructor
@RequestMapping(path = "/issues")
public class IssueServiceImpl implements IssueService {
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public Messenger save(IssueDto dto) {
        log.info("Parameters received through save service: " + dto);
        User client = userRepository.findById(dto.getClient().getId())
                .orElseThrow(() -> new IllegalArgumentException("User " + dto.getClient() + " not found."));
        Issue issue = Issue.builder()
                .law(dto.getLaw())
                .title(dto.getTitle())
                .content(dto.getContent())
                .attachment(dto.getAttachment())
                .client(dto.getClient())
                .build();
        Issue savedIssue = issueRepository.save(issue);
        return Messenger.builder()
                .message(issueRepository.existsById(savedIssue.getId()) ? "SUCCESS" : "FAILURE")
                .build();
    }

    @Transactional
    @Override
    public Messenger delete(Long id) {
        issueRepository.deleteById(id);
        return Messenger.builder()
                .message(
                        Stream.of(id)
                                .filter(i -> existsById(i))
                                .peek(i -> issueRepository.existsById(i))
                                .map(i -> "FAILURE")
                                .findAny()
                                .orElseGet(() -> "SUCCESS")
                ).build();
    }

    @Transactional
    @Override
    public Messenger update(IssueDto dto) {
        Optional<Issue> optionalIssue = issueRepository.findById(dto.getId());
        if (optionalIssue.isPresent()) {
            Issue issue = optionalIssue.get();
            Issue updatedIssue = issue.toBuilder()
                    .law(dto.getLaw())
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .attachment(dto.getAttachment())
                    .client(dto.getClient())
                    .build();
            Long upatedIssueId = issueRepository.save(updatedIssue).getId();

            return Messenger.builder()
                    .message("SUCCESS ID is " + upatedIssueId)
                    .build();
        } else {
            return Messenger.builder()
                    .message("FAILURE")
                    .build();
        }
    }

    @Transactional
    @Override
    public List<IssueDto> findAll() {
        return issueRepository.findAllByOrderByIdDesc().stream().map(i -> entityToDto(i)).toList();
    }

    @Override
    public Optional<IssueDto> findById(Long id) {
        return issueRepository.findById(id).map(i -> entityToDto(i));
    }

    @Override
    public Messenger count() {
        return Messenger.builder()
                .message(issueRepository.count() + "")
                .build();
    }

    @Override
    public boolean existsById(Long id) {
        return issueRepository.existsById(id);
    }
}
