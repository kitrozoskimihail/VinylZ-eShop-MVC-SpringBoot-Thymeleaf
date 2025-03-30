    package com.vinylrecordshop.VinylZ.web;

    import com.vinylrecordshop.VinylZ.model.Author;
    import com.vinylrecordshop.VinylZ.model.Genre;
    import com.vinylrecordshop.VinylZ.model.Vinyl;
    import com.vinylrecordshop.VinylZ.service.AuthorService;
    import com.vinylrecordshop.VinylZ.service.VinylService;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.Base64;
    import java.util.Date;
    import java.util.List;


    @Controller
    public class VinylController {

        private final VinylService vinylService;
        private final AuthorService authorService;

        public VinylController(VinylService vinylService, AuthorService authorService) {
            this.vinylService = vinylService;
            this.authorService = authorService;
        }

        @GetMapping({"/", "/vinyls"})
        public String showVinyls(
                @RequestParam(required = false) String nameSearch,
                @RequestParam(required = false) Long authorId,
                Model model) {

            List<Vinyl> vinyls;
            List<Author> authors = authorService.listAllAuthors();

            if (nameSearch == null && authorId == null) {
                vinyls = vinylService.listAllVinyls();
            } else {
                vinyls = vinylService.listVinylsByNameAndAuthor(nameSearch, authorId);
            }

            model.addAttribute("vinyls", vinyls);
            model.addAttribute("authors", authors);
            model.addAttribute("genres", Genre.values());

            return "list.html";
        }

        @GetMapping("/vinyls/add")
        public String showAdd(Model model) {
            model.addAttribute("authors", authorService.listAllAuthors());
            model.addAttribute("genres", Genre.values());

            return "form.html";
        }

        @GetMapping("/vinyls/{id}/edit")
        public String showEdit(@PathVariable Long id, Model model) {
            Vinyl vinyl = vinylService.findVinylById(id);

            model.addAttribute("vinyl", vinyl);
            model.addAttribute("authors", authorService.listAllAuthors());
            model.addAttribute("genres", Genre.values());

            return "form.html";
        }

        @PostMapping("/vinyls")
        public String create(@RequestParam String vinylName,
                             @RequestParam String price,
                             @RequestParam String releaseDate,
                             @RequestParam Integer stock,
                             @RequestParam MultipartFile imageFile,
                             @RequestParam String genre,
                             @RequestParam List<Long> authorIDs) throws ParseException, IOException {

            String imageUrl = imageFile.isEmpty() ? "" : "data:image/png;base64," +
                    Base64.getEncoder().encodeToString(imageFile.getBytes());

            Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate);

            vinylService.createVinyl(vinylName, price, parsedDate, imageUrl, stock, Genre.valueOf(genre), authorIDs);

            return "redirect:/vinyls";
        }

        @PostMapping("/vinyls/{id}")
        public String update(@PathVariable Long id,
                             @RequestParam String vinylName,
                             @RequestParam String price,
                             @RequestParam String releaseDate,
                             @RequestParam Integer stock,
                             @RequestParam String imageUrl,
                             @RequestParam String genre,
                             @RequestParam List<Long> authorIDs) throws ParseException {

            Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate);
            vinylService.updateVinyl(id, vinylName, price, parsedDate, imageUrl, stock, Genre.valueOf(genre), authorIDs);

            return "redirect:/vinyls";
        }

        @PostMapping("/vinyls/{id}/delete")
        public String delete(@PathVariable Long id) {
            vinylService.deleteVinyl(id);

            return "redirect:/vinyls";
        }


    }