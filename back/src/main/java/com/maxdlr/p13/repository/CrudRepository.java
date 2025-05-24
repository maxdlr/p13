package com.maxdlr.p13.repository;

import java.util.List;

public interface CrudRepository<Entity> {
  Entity findOneById(long id);

  List<Entity> findAll();

  Entity save(Entity team);
}
