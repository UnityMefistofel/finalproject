package ru.maxima.finalproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maxima.finalproject.models.Role;
import ru.maxima.finalproject.models.eRole;

import java.util.Collection;
import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Override
    Optional<Role> findById(Long aLong);

    Optional<Role> findByName(eRole name);
}
