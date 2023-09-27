package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tuc.ds2020.entities.DeviceEntity;
import ro.tuc.ds2020.entities.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<DeviceEntity, UUID> {
    /**
     * Example: JPA generate Query by Field
     */
    DeviceEntity findByDescription(String description);

    List<DeviceEntity> findByUser(UserEntity user);
}
