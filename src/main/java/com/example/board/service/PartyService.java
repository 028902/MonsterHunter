package com.example.board.service;

import com.example.board.entity.Party;
import com.example.board.mapper.PartyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartyService {
    @Autowired
    PartyMapper partyMapper;

    public List<Party> getPartyList() {
        return partyMapper.getPartyList();
    }
    public void insertParty(Party party) {
        partyMapper.insertParty(party);
    }
    public void updateParty(Party party) {
        partyMapper.updateParty(party);
    }
    public void deleteParty(int seq){
        partyMapper.deleteParty(seq);
    }
}
