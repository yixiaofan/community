package com.mybolg.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mybolg.community.dto.PaginationDTO;
import com.mybolg.community.dto.QuestionDTO;
import com.mybolg.community.mapper.QuestionMapper;
import com.mybolg.community.mapper.UserMapper;
import com.mybolg.community.model.Question;
import com.mybolg.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<Question> questions = questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        if(questions!=null){
            for(Question question:questions){
                User user=userMapper.findById(question.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
            }
        }
        PageInfo<Question> pi = new PageInfo<>(questions);
        paginationDTO.setQuestions(questionDTOList);
        paginationDTO.setPagination(pi.getTotal(),page,size);
        return paginationDTO;
    }
}
