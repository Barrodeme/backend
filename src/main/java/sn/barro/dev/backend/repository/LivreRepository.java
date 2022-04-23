package sn.barro.dev.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sn.barro.dev.backend.model.LivreModel;

import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
@Repository
public interface LivreRepository extends JpaRepository<LivreModel, Long> {

    @Query("select DISTINCT l.auteurModel from LivreModel  l")
    List<Object> findAllLivre();



}
