package sn.barro.dev.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sn.barro.dev.backend.model.AuteurModel;
@Repository
public interface AuteurRepository extends JpaRepository<AuteurModel, Long> {}
