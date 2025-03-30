package com.vinylrecordshop.VinylZ.web;

import com.vinylrecordshop.VinylZ.model.Author;
import com.vinylrecordshop.VinylZ.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String showAuthors(Model model) {
        List<Author> authors = authorService.listAllAuthors();

        model.addAttribute("authors", authors);

        return "author-list.html";
    }

    @GetMapping("/add")
    public String showAdd(Model model) {
        model.addAttribute("authors", authorService.listAllAuthors());

        return "author-form.html";
    }

    @GetMapping("/{id}/edit")
    public String showEdit(@PathVariable Long id,
                           Model model) {

        Author author = authorService.findAuthorById(id);
        model.addAttribute("author", author);

        return "author-form.html";
    }

    @PostMapping
    public String create(@RequestParam String firstName,
                         @RequestParam String lastName) {


        authorService.createAuthor(firstName, lastName);

        return "redirect:/authors";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam String firstName,
                         @RequestParam String lastName) {

        authorService.updateAuthor(id, firstName, lastName);

        return "redirect:/authors";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        authorService.deleteAuthor(id);

        return "redirect:/authors";
    }

}