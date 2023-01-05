package com.roshka.dtaporteria.repository;


import com.roshka.dtaporteria.dto.RecordDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository{
    List<RecordDTO> list();
}
