package fsj_3_22.final_certification.online_store.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import fsj_3_22.final_certification.online_store.models.bEnumModel;

@Repository
public interface EnumRepository<EnumModel extends bEnumModel> extends JpaRepository<EnumModel, Integer> {
  EnumModel findByName(@NonNull String name);
}
