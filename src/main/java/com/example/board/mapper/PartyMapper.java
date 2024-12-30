package com.example.board.mapper;

import com.example.board.entity.Party;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PartyMapper {
    List<Party> getPartyList();
    void insertParty(Party party);
    void updateParty(Party party);
    void deleteParty(int seq);
}
