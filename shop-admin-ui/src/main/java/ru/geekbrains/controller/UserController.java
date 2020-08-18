package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.model.User;
import ru.geekbrains.repo.RoleRepository;
import ru.geekbrains.service.UserService;


import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {
    
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private UserService userService;
    private final RoleRepository roleRepository;


    @Autowired
    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/user/new")
    public String createUser(Model model) {
        log.debug("Create new user");
        model.addAttribute("user", new User());
        model.addAttribute("create", true);
        model.addAttribute("roles", roleRepository.findAll());
        return "user";
    }

    @GetMapping("/user")
    public String userList(Model model,
                           @RequestParam(name = "minAge", required = false) Integer minAge,
                           @RequestParam(name = "maxAge", required = false) Integer maxAge,
                           @RequestParam(name = "nameSearch", required = false) String nameSearch)
    {
        log.debug("User list. With minAge = {} and maxAge = {}", minAge, maxAge);

        model.addAttribute("activePage", "Users");
        model.addAttribute("users", userService.findAll());

        return "users";
    }

    @PostMapping("/user")
    public String saveUser(User user, BindingResult bindingResult) {
        log.debug("Save user method");
        if (bindingResult.hasErrors()) {
            return "user";
        }
        userService.save(user);
        return "redirect:/user";
    }

   @GetMapping("/user/{id}/edit")
   public String editUserById(@PathVariable(name = "id") Long id, Model model) {

       Optional<User> user = userService.findById(id);
       model.addAttribute("edit", true);
       model.addAttribute("user", user.get());
       model.addAttribute("roles", roleRepository.findAll());
       return "user";
   }

    @DeleteMapping
    public String deleteUser(@RequestParam("id") Long id) {
        log.info("Delete {}", id);
        userService.delete(id);
        return "redirect:/user";
    }

}
