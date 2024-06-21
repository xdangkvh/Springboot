package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.example.demo.dto.NewDTO;

public interface INewService {
    NewDTO save(NewDTO newDTO);

    // void delete(Long id);
    // void delete(long[] ids);
    boolean delete(List<Long> ids);

    List<NewDTO> findAll(Pageable pageable);

    int totalItem();
}