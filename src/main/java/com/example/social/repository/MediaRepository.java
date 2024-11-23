package com.example.social.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.social.model.Media;

public interface MediaRepository extends CrudRepository<Media ,Integer>
{
}