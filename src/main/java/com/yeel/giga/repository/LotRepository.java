package com.yeel.giga.repository;

import com.yeel.giga.model.Lot;
import com.yeel.giga.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotRepository extends JpaRepository<Lot, Long>{
    List<Lot> findLotsByOwnerIs(UserData userData);
}
