package com.MindForum.version1.service.impl;

import com.MindForum.version1.DTO.repsonse.*;
import com.MindForum.version1.DTO.request.CreateQuestionRequest;
import com.MindForum.version1.DTO.request.UpdateQuestionRequest;
import com.MindForum.version1.entity.Question;
import com.MindForum.version1.entity.Tag;
import com.MindForum.version1.entity.User;
import com.MindForum.version1.mapper.AnswerMapper;
import com.MindForum.version1.mapper.QuestionMapper;
import com.MindForum.version1.mapper.TagMapper;
import com.MindForum.version1.mapper.UserMapper;
import com.MindForum.version1.repository.AnswerRepository;
import com.MindForum.version1.repository.QuestionRepository;
import com.MindForum.version1.service.QuestionService;
import com.MindForum.version1.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final TagService tagService;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    private final QuestionMapper questionMapper;
    private final UserMapper userMapper;
    private final TagMapper tagMapper;
    private final AnswerMapper answerMapper;

    @Override
    public QuestionDetailResponse getQuestionDetail(Long questionId) {
        //Get question
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        //Get author
        UserResponse author = userMapper.toDTO(question.getUser());

        //Get answer
        List<AnswerResponse> answers = answerRepository.findAllByQuestion(question)
                .stream()
                .map(answerMapper::toDTO)
                .toList();

        //Get tag
        List<TagResponse> tagResponses = question.getTags()
                .stream()
                .map(tagMapper::toDTO)
                .toList();

        return QuestionDetailResponse.builder()
                .id(question.getId())
                .author(author)
                .title(question.getTitle())
                .content(question.getContent())
                .tags(tagResponses)
                .answers(answers)
                .createdAt(question.getCreatedAt())
                .build();
    }

    @Override
    public List<QuestionResponse> getQuestions(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Question> questions = questionRepository.findAll(pageable).toList();

        return questions
                .stream()
                .map(questionMapper::toDTO)
                .toList();
    }

    @Override
    public void updateQuestion(User user, Long id, UpdateQuestionRequest updateQuestionRequest) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        if (user.getId() == question.getUser().getId()) {
            question.setTitle(updateQuestionRequest.getNewTitle());
            question.setContent(updateQuestionRequest.getNewContent());
            question.setUpdatedAt(Instant.now());

            log.info("User id {} has updated question id {}", user.getId(), question.getId());
        } else throw new RuntimeException("Cannot be updated");
    }

    @Override
    public void createQuestion(User user, CreateQuestionRequest createQuestionRequest) {
        Set<Tag> tags = tagService.findAndCreateTag(createQuestionRequest.getTags());

        Question question = Question.builder()
                .title(createQuestionRequest.getTitle())
                .content(createQuestionRequest.getContent())
                .tags(tags)
                .user(user)
                .build();

        questionRepository.save(question);
        log.info("User id {} created question id {}", user.getId(), question.getId());
    }

    @Override
    public void deleteQuestion(User user, Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        if (user.getId() == question.getUser().getId()) {
            questionRepository.delete(question);
            log.info("Question id {} has been deleted", id);
        } else throw new RuntimeException("Cannot be performed");
    }
}
