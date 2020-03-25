package ar.com.guanaco.diucon.repository;

import ar.com.guanaco.diucon.domain.Responsable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Responsable entity.
 */
@Repository
public interface ResponsableRepository extends JpaRepository<Responsable, Long>, JpaSpecificationExecutor<Responsable> {

    @Query(value = "select distinct responsable from Responsable responsable left join fetch responsable.subcategorias",
        countQuery = "select count(distinct responsable) from Responsable responsable")
    Page<Responsable> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct responsable from Responsable responsable left join fetch responsable.subcategorias")
    List<Responsable> findAllWithEagerRelationships();

    @Query("select responsable from Responsable responsable left join fetch responsable.subcategorias where responsable.id =:id")
    Optional<Responsable> findOneWithEagerRelationships(@Param("id") Long id);
}
