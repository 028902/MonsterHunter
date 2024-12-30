package com.example.board.mapper;

import com.example.board.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void registration(User user);

    String getPasswordById(String id);
}
