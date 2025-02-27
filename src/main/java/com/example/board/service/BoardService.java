package com.example.board.service;

import com.example.board.dto.BoardListDto;
import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Page<BoardListDto> findBoardList(Pageable pageable) {
        return boardRepository.findBoardList(pageable);
    }
    public Page<BoardListDto> searchBoardList(String nickname, String title, Pageable pageable) {
        return boardRepository.searchBoardList(nickname, title, pageable);
    }

    public Board getBoardDetail(int seq){
        Board board = boardRepository.findById(seq).orElseThrow(() -> new EntityNotFoundException("Board not found"));
        board.setCount(board.getCount()+1);
        return boardRepository.save(board);
    }

    public Board insertBoard(Board board) {
        return boardRepository.save(board);
    }

    public Board updateBoard(int seq, Board updatedBoard) {
        Board board = boardRepository.findById(seq).orElseThrow(() -> new EntityNotFoundException("Board " + seq + " not found"));
        if (updatedBoard.getTitle() != null) board.setTitle(updatedBoard.getTitle());
        if (updatedBoard.getBody() != null) board.setBody(updatedBoard.getBody());
        return boardRepository.save(board);
    }

    public void deleteBoard(int seq) {
        boardRepository.findById(seq).orElseThrow(() -> new EntityNotFoundException("Board " + seq + " not found"));
        boardRepository.deleteById(seq);
    }
}
