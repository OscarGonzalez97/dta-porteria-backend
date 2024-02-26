package com.roshka.dtaporteria.repository;

import com.roshka.dtaporteria.dto.MemberDTO;
import com.roshka.dtaporteria.model.Member;
import org.springframework.data.domain.Example;
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
    long countAllByIsDefaulterIsNotNull();
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Member AS u WHERE u.fechaVencimiento<?1")
    void deleteFecha(String fecha);

    Member findOneByIdMember(String idMember);

    @Transactional
    @Query(value = "UPDATE Member SET status = :newStatus")
    @Modifying
    int updateAllByStatus(String newStatus);

}
