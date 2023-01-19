package com.roshka.dtaporteria.repository;

import com.roshka.dtaporteria.model.Sync;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SyncRepository extends JpaRepository<Sync, Integer>{

    @Transactional
    @Modifying
    @Query(value = "UPDATE Sync set cantidad = cantidad+1 WHERE id=1")
    public void updateCantidad();
    @Transactional
    @Modifying
    @Query(value = "UPDATE Sync set cantidad = 0 WHERE id=1")
    public void resetCantidad();

}
