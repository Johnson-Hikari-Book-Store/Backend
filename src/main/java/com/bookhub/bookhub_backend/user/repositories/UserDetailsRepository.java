package com.bookhub.bookhub_backend.user.repositories;

import com.bookhub.bookhub_backend.user.entities.UserDetailsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDetailsRepository extends MongoRepository<UserDetailsEntity, String> {
}
