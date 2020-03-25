package ar.com.guanaco.diucon.repository;

import ar.com.guanaco.diucon.domain.SubCategoria;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SubCategoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubCategoriaRepository extends JpaRepository<SubCategoria, Long>, JpaSpecificationExecutor<SubCategoria> {
}
