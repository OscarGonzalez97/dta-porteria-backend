package com.roshka.dtaporteria.repository;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.roshka.dtaporteria.dto.MemberDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MemberRepository {
    List<MemberDTO> list();

    Boolean add(MemberDTO post);
    Boolean edit(String id, MemberDTO post);
    Boolean delete(String id);

    Map<String, Object> getById(String id);

}
