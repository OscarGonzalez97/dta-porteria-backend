package com.roshka.dtaporteria.repository;

import com.roshka.dtaporteria.dto.MemberDTO;
import com.roshka.dtaporteria.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
@Transactional
public interface MembersRepository  extends JpaRepository<Member, String> {
    boolean existsByIdMember(String id_miembro);
    @Modifying
    @Query("DELETE FROM Member AS u WHERE u.fechaVencimiento<:fecha")
    void deleteFecha(@Param("fecha")String fecha);
}
