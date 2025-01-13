package com.example.board.service;

import com.example.board.dto.BoardListDto;
import com.example.board.dto.MonsterListDto;
import com.example.board.dto.SearchAllDto;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.MainRepository;
import com.example.board.repository.MonsterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainService {
    private final MainRepository mainRepository;

    public SearchAllDto search(String keyword) {
        Pageable pageable = PageRequest.of(0, 3);

        Page<MonsterListDto> monsters = mainRepository.searchAllMonsterList(keyword, pageable);
        Page<BoardListDto> boards = mainRepository.searchAllBoardList(keyword, pageable);

        SearchAllDto searchResult = new SearchAllDto();
        searchResult.setMonsters(monsters.getContent());
        searchResult.setBoards(boards.getContent());
        searchResult.setTotalMonsterCount(monsters.getTotalElements());
        searchResult.setTotalBoardCount(boards.getTotalElements());

        return searchResult;
    }
}
