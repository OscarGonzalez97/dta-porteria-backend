package com.roshka.dtaporteria.repository;

import com.roshka.dtaporteria.dto.UserDTO;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository {
    List<UserDTO> list();
}
