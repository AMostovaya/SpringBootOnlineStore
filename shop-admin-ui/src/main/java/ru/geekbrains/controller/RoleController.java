package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.model.Role;
import ru.geekbrains.model.User;
import ru.geekbrains.repo.RoleRepository;
import ru.geekbrains.service.RoleService;

import java.util.List;
import java.util.Optional;

@RequestMapping("/role")
@Controller
public class RoleController {

    private RoleService roleService;
    private final RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleService roleService, RoleRepository roleRepository) {
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String roleList(Model model)
    {
        List<Role> rolePage = roleService.findAll();
        model.addAttribute("rolesPage", rolePage);
        return "roles";
    }

    @GetMapping("new")
    public String createUser(Model model) {
        model.addAttribute("role", new Role());
        return "role";
    }

    @DeleteMapping
    public String deleteUser(@RequestParam("id") Long id) {
        roleService.delete(id);
        return "redirect:/role";
    }

    @PostMapping
    public String saveUser(Role role, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "role";
        }
        roleService.save(role);
        return "redirect:/role";
    }

}
