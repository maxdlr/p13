package com.maxdlr.p13.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<Entity> {
  Optional<Entity> findOneById(long id);

  List<Entity> findAll();

  Entity save(Entity team);
}
