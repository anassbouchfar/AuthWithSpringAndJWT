package com.insea.SpringJwt.Controllers;

import com.insea.SpringJwt.Repositories.RoleRepository;
import com.insea.SpringJwt.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Roles")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("")
    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

    @GetMapping("/{id}")
    public Role getRole(@PathVariable int id){
        return roleRepository.findById(id).get();
    }

    @PostMapping("")
    public Role addRole(@RequestBody Role role){
        return roleRepository.save(role);
    }

    @PutMapping("")
    public Role updateRole(@RequestBody Role role){
        return roleRepository.save(role);
    }

    @DeleteMapping("{id}")
    public Role deleteRole(@PathVariable int id){
         Role role = roleRepository.findById(id).get();
         if(role!=null)
                roleRepository.delete(role);
         return role;
    }
}
