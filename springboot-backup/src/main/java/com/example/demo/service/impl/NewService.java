package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.converter.NewConverter;
import com.example.demo.dto.NewDTO;
import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.NewEntity;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.NewRepository;
import com.example.demo.service.INewService;

@Service
public class NewService implements INewService {

	@Autowired
	private NewRepository newRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private NewConverter newConverter;

	@Override
	public NewDTO save(NewDTO newDTO) {
		NewEntity newEntity = new NewEntity();
		if (newDTO.getId() != null) {
			NewEntity oldNewEntity = newRepository.findOneById(newDTO.getId());
			newEntity = newConverter.toEntity(newDTO, oldNewEntity);
		} else {
			newEntity = newConverter.toEntity(newDTO);
		}
		CategoryEntity categoryEntity = categoryRepository.findOneByCode(newDTO.getCategoryCode());
		newEntity.setCategory(categoryEntity);
		newEntity = newRepository.save(newEntity);
		return newConverter.toDTO(newEntity);
	}
	// @Override
	// public void delete(Long id) {
	// newRepository.deleteById(id);

	// }

	// @Override
	// public void delete(long[] ids) {
	// for (long item : ids) {
	// // NewEntity entityToDelete = new NewEntity();
	// // entityToDelete.setId(item); // Assuming setId method exists in NewEntity
	// newRepository.deleteById(item);
	// }
	// }

	@Override
	public boolean delete(List<Long> ids) {
		List<NewEntity> newsToDelete = newRepository.findAllById(ids);

		if (newsToDelete.size() != ids.size()) {
			// Log the IDs that were not found
			ids.removeAll(newsToDelete.stream().map(NewEntity::getId).collect(Collectors.toList()));
			System.out.println("IDs not found: " + ids);
			return false; // Some IDs were not found
		}
		newRepository.deleteAll(newsToDelete);
		return true;
		// for (long item : ids) {
		// // NewEntity entityToDelete = new NewEntity();
		// // entityToDelete.setId(item); // Assuming setId method exists in NewEntity
		// newRepository.deleteById(item);
		// }
	}

	@Override
	public List<NewDTO> findAll(Pageable pageable) {
		List<NewDTO> results = new ArrayList<>();
		List<NewEntity> entities = newRepository.findAll(pageable).getContent();
		for (NewEntity item : entities) {
			NewDTO newDTO = newConverter.toDTO(item);
			results.add(newDTO);
		}
		System.out.println("results: " + results);
		return results;
	}

	@Override
	public int totalItem() {
		return (int) newRepository.count();
	}

	// @Override
	// public void delete(long[] ids) {
	// for(long item: ids) {
	// newRepository.delete(item);
	// }
	// }
}
