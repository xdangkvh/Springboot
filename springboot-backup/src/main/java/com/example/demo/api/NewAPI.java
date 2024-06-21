package com.example.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.api.output.NewOutput;
import com.example.demo.dto.NewDTO;
import com.example.demo.service.INewService;

@CrossOrigin
@RestController // RestController = ReponseBody + Controller; declare @RestController to create
				// API
// ResponseBody annotation được dùng để thông báo với controller rằng Java
// Object
// trả về cho client sẽ tự động ánh xạ sang JSON và chuyển vào HttpResponse.
// @RequestBody được dùng để ánh xạ HttpRequest body sang một java object tự
// động.
public class NewAPI {
	@Autowired
	private INewService newService;

	@GetMapping(value = "/new")
	public NewOutput showNew(@RequestParam("page") int page, @RequestParam("limit") int limit) {
		NewOutput result = new NewOutput();
		result.setPage(page);
		Pageable pageable = PageRequest.of(page - 1, limit);
		result.setListResult(newService.findAll(pageable));
		result.setTotalPage((int) Math.ceil((double) (newService.totalItem()) / limit));
		return result;
	}

	@PostMapping(value = "/new") // PostMapping = method POST + RequestMapping
	public NewDTO createNew(@RequestBody NewDTO model) {
		return newService.save(model);
	}

	@PutMapping(value = "/new/{id}")
	public NewDTO updateNew(@RequestBody NewDTO model, @PathVariable("id") long id) {
		model.setId(id);
		return newService.save(model);
	}

	// @DeleteMapping(value = "/new/{id}")
	// public void deleteNew(@PathVariable("id") long id) {
	// newService.delete(id);

	// }

	@DeleteMapping(value = "/new")
	public void deleteNew(@RequestBody List<Long> ids) {
		newService.delete(ids);

	}
}