package cl.valenzuela.proyectocruds.crud.user.repository;

import cl.valenzuela.proyectocruds.crud.user.dto.UserDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends CrudRepository<UserDTO, Integer> {
}