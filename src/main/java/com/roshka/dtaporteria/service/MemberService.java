package com.roshka.dtaporteria.service;

import com.roshka.dtaporteria.dto.MemberDTO;

import java.util.List;

public interface MemberService {
    List<MemberDTO> list();

    Boolean add(MemberDTO post);
    Boolean edit(String id, MemberDTO post);
    Boolean delete(String id);

}
