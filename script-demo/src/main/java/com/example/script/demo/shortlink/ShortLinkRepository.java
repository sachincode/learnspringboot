package com.example.script.demo.shortlink;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShortLinkRepository extends CrudRepository<ShortLink, Long> {

}
