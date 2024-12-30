package com.example.board.service;

import com.example.board.entity.Board;
import com.example.board.entity.Party;
import com.example.board.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    BoardMapper boardMapper;

    public List<Board> getBoardList() {
        return boardMapper.getBoardList();
    }
    public void insertBoard(Board board) {
        boardMapper.insertBoard(board);
    }
    public void updateBoard(Board board) {
        boardMapper.updateBoard(board);
    }
    public void deleteBoard(int seq){
        boardMapper.deleteBoard(seq);
    }
}
