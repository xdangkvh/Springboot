package com.example.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.PermissionDTO;
import com.example.demo.service.IPermissionService;

public class PermissionAPI {
    @Autowired
    private IPermissionService permissionService;

    @PostMapping(value = "/permission") // PostMapping = method POST + RequestMapping
    public PermissionDTO createRole(@RequestBody PermissionDTO model) {
        return permissionService.save(model);
    }

    @PutMapping(value = "/permission/{id}")
    public PermissionDTO updatePermission(@RequestBody PermissionDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return permissionService.save(model);
    }

    @DeleteMapping(value = "/permission")
    public void deleteRole(@RequestBody List<Long> ids) {
        permissionService.delete(ids);

    }
}
