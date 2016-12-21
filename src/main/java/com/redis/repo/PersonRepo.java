package com.redis.repo;

import com.redis.bean.Person;
import java.util.Map;

public interface PersonRepo {

    public void save(Person person);

    public Person find(String id);

    public Map<Object, Object> findAll();

    public void delete(String id);
}
