
package com.nerea.mydish.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nerea.mydish.repository.entity.AlimentoEntity;

@Repository
public interface AlimentoRepository extends JpaRepository<AlimentoEntity, Long> {

}
