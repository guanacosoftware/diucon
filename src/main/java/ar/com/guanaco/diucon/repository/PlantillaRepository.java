package ar.com.guanaco.diucon.repository;

import ar.com.guanaco.diucon.domain.Plantilla;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Plantilla entity.
 */
@Repository
public interface PlantillaRepository extends JpaRepository<Plantilla, Long>, JpaSpecificationExecutor<Plantilla> {

    @Query(value = "select distinct plantilla from Plantilla plantilla left join fetch plantilla.subcategorias",
        countQuery = "select count(distinct plantilla) from Plantilla plantilla")
    Page<Plantilla> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct plantilla from Plantilla plantilla left join fetch plantilla.subcategorias")
    List<Plantilla> findAllWithEagerRelationships();

    @Query("select plantilla from Plantilla plantilla left join fetch plantilla.subcategorias where plantilla.id =:id")
    Optional<Plantilla> findOneWithEagerRelationships(@Param("id") Long id);
}
