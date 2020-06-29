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

@RequestMapping("/user")
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

    @GetMapping("new")
    public String createUser(Model model) {
        log.debug("Create new user");
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "user";
    }

    @GetMapping
    public String userList(Model model,
                           @RequestParam(name = "minAge", required = false) Integer minAge,
                           @RequestParam(name = "maxAge", required = false) Integer maxAge,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam(name = "nameSearch", required = false) String nameSearch)
    {
        log.debug("User list. With minAge = {} and maxAge = {}", minAge, maxAge);

        Page<User> userPage = userService.findByAgeName(minAge, maxAge, nameSearch,
                PageRequest.of(page.orElse(1) - 1, size.orElse(5)));
        model.addAttribute("usersPage", userPage);
        model.addAttribute("prevPageNumber", userPage.hasPrevious() ? userPage.previousPageable().getPageNumber() + 1 : -1);
        model.addAttribute("nextPageNumber", userPage.hasNext() ? userPage.nextPageable().getPageNumber() + 1 : -1);
        return "users";
    }

    @PostMapping
    public String saveUser(User user, BindingResult bindingResult) {
        log.debug("Save user method");

        if (bindingResult.hasErrors()) {
            return "user";
        }
        userService.save(user);
        return "redirect:/user";
    }

   @GetMapping("edit/{id}")
   public String editUserById(@PathVariable(name = "id") Long id, Model model) {

       Optional<User> user = userService.findById(id);
       model.addAttribute("user", user.get());
       model.addAttribute("roles", roleRepository.findAll());
       return "update-user";

   }

    @PostMapping("update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            log.info("Errors");
            return "update-user";
        }

        userService.save(user);
        model.addAttribute("users", userService.findAll());
        return "redirect:/user";
    }

    @DeleteMapping
    public String deleteUser(@RequestParam("id") Long id) {

        log.info("Delete {}", id);
        userService.delete(id);
        return "redirect:/user";

    }

}
