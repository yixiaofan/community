package com.mybolg.community.service;

import com.mybolg.community.dto.CommentDTO;
import com.mybolg.community.enums.CommentTypeEnum;
import com.mybolg.community.exception.CustomizeErrorCode;
import com.mybolg.community.exception.CustomizeException;
import com.mybolg.community.mapper.CommentMapper;
import com.mybolg.community.mapper.QuestionMapper;
import com.mybolg.community.mapper.UserMapper;
import com.mybolg.community.model.Comment;
import com.mybolg.community.model.Question;
import com.mybolg.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            Comment dbComment = commentMapper.selectById(comment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else{
            Question question = questionMapper.getById(comment.getParentId());
            if(question == null){
                throw new CustomizeException((CustomizeErrorCode.QUESTION_NOT_FOUND));
            }
            commentMapper.insert(comment);
            questionMapper.incCommentCount(question.getId());
        }
    }

    public List<CommentDTO> listByQuestionId(Long id, CommentTypeEnum type) {
        List<Comment> comments = commentMapper.selectByIdAndType(id,type.getType());
        if(comments.size() == 0) return null;
        //获取去重的评论人id
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);
        //获取评论人并转换为Map
        List<User> users =userMapper.selectInId(userIds);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        //转换comment为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
