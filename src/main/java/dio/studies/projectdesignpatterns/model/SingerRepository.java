package dio.studies.projectdesignpatterns.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingerRepository extends CrudRepository<Singer,Long> {
}
