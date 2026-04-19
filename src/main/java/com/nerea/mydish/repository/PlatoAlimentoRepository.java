package com.nerea.mydish.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nerea.mydish.repository.entity.PlatoAlimentoEntity;
import com.nerea.mydish.repository.entity.PlatoAlimentoId;

@Repository
public interface PlatoAlimentoRepository extends JpaRepository<PlatoAlimentoEntity, PlatoAlimentoId>{
}
