package com.MindForum.version1.service.impl;

import com.MindForum.version1.DTO.request.CreateAnswerRequest;
import com.MindForum.version1.DTO.request.UpdateAnswerRequest;
import com.MindForum.version1.entity.Answer;
import com.MindForum.version1.entity.Question;
import com.MindForum.version1.entity.User;
import com.MindForum.version1.repository.AnswerRepository;
import com.MindForum.version1.repository.QuestionRepository;
import com.MindForum.version1.service.AnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Override
    public void createAnswer(CreateAnswerRequest createAnswerRequest, User user) {
        //Find question
        Question question = questionRepository.findById(createAnswerRequest.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        Answer answer = Answer.builder()
                .content(createAnswerRequest.getContent())
                .score(0)
                .question(question)
                .user(user)
                .build();

        answerRepository.save(answer);
        log.info("User id {} has created answer id {}", user.getId(), answer.getId());
    }

    @Override
    public void updateAnswer(User user, Long id, UpdateAnswerRequest updateAnswerRequest) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer not found"));

        if (user.getId() == answer.getUser().getId()) {
            answer.setContent(updateAnswerRequest.getNewContent());
            answer.setUpdatedAt(Instant.now());

            log.info("User id {} has updated answer id {}", user.getId(), answer.getId());
        } else throw new RuntimeException("Cannot be updated");
    }

    @Override
    public void deleteAnswer(User user, Long id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer not found"));

        if (user.getId() == answer.getUser().getId()) {
            answerRepository.delete(answer);

            log.info("User id {} has deleted answer id {}", user.getId(), answer.getId());
        } else throw new RuntimeException("Cannot be deleted");
    }
}
