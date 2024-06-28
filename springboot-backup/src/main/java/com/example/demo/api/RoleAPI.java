package com.example.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.RoleDTO;
import com.example.demo.service.IRoleService;

@CrossOrigin
@RestController
public class RoleAPI {
    @Autowired
    private IRoleService roleService;

    @PostMapping(value = "/role") // PostMapping = method POST + RequestMapping
    public RoleDTO createRole(@RequestBody RoleDTO model) {
        return roleService.save(model);
    }

    @PutMapping(value = "/role/{id}")
    public RoleDTO updateRole(@RequestBody RoleDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return roleService.save(model);
    }

    @DeleteMapping(value = "/role")
    public void deleteRole(@RequestBody List<Long> ids) {
        roleService.delete(ids);

    }
}
