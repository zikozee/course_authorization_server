package com.zee.courseoauth2.repository;


import com.zee.courseoauth2.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @dev : Ezekiel Eromosei
 * @date : 28 Nov, 2026
 */

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    Optional<Client> findByClientId(String clientId);
}
