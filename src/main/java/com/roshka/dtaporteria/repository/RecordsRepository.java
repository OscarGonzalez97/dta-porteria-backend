package com.roshka.dtaporteria.repository;

import com.roshka.dtaporteria.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordsRepository extends JpaRepository<Member, String> {

}
