package com.burak.cards.repository;

import com.burak.cards.entity.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardsRepository extends JpaRepository<Cards, Long> {

  Optional<Cards> findByMobileNumber(String mobileNumber);

  Optional<Cards> findByCardNumber(String cardNumber);

}
