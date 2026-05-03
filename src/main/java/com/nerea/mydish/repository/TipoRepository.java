package com.nerea.mydish.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nerea.mydish.repository.entity.TipoEntity;

public interface TipoRepository extends JpaRepository<TipoEntity, Long> {

}
