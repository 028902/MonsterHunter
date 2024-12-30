package com.example.board.mapper;

import com.example.board.entity.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<Board> getBoardList();
    void insertBoard(Board board);
    void updateBoard(Board board);
    void deleteBoard(int seq);
}
