package com.turkcell.rentACar.dataAccess;

import com.turkcell.rentACar.entities.sourceEntities.CardInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardInfoDao extends JpaRepository<CardInfo, Integer> {

    boolean existsCardInfoByCardNumber(String cardNumber);
}
