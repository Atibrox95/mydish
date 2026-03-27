package com.nerea.mydish.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nerea.mydish.repository.entity.PlatoEntity;

@Repository
public interface PlatoRepository extends JpaRepository<PlatoEntity, Long> {
}
