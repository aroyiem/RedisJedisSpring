package com.redis.repo.impl;

import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;

import com.redis.bean.Person;
import com.redis.repo.PersonRepo;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("personRepo")
public class PersonRepoImpl implements PersonRepo {
    
    @Autowired
    /*
     * 
     * RedisTemplate takes care of serialiazation and is thread-safe.
     * By default RedisTemplate uses java serialiazation (JdkSerializationRedisSerializer)
     * 
     */
    private RedisTemplate<String, Person> redisTemplate;
    private static String PERSON_KEY = "Person";



    @Override
    /*
     * saving data in redis server
     * 
     */
    public void save(Person person) {
        this.redisTemplate.opsForHash().put(PERSON_KEY, person.getId(), person);
        /*
         * 
         * persisting the data for 5 days in cache
         */
        this.redisTemplate.expire(person.getId(), 5, TimeUnit.DAYS);
    }

    /*
     * 
     * fetching the data from redis server
     * 
     */
    @Override
    public Person find(String id) {
        return (Person) this.redisTemplate.opsForHash().get(PERSON_KEY, id);
    }

    /*
     * 
     * fetch all data from redis server for a particular Repo
     * 
     */
    @Override
    public Map<Object, Object> findAll() {
        return this.redisTemplate.opsForHash().entries(PERSON_KEY);
    }

    /*
     * 
     * delete data from redis server
     * 
     */
    @Override
    public void delete(String id) {
        this.redisTemplate.opsForHash().delete(PERSON_KEY, id);

    }
}
