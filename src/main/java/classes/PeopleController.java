package classes;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;


@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonInterface personInterface;
    @Autowired
    public PeopleController(PersonInterface personInterface) {
        this.personInterface = personInterface;
    }
    @GetMapping("/hello")
    public String index1(){//тестовая страница
        System.out.println("ed");
        return "hello";
    }

    @GetMapping()
    public String index(Model model){//список людей
        model.addAttribute("people", personInterface.index());
        return "index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){//человек по id
        model.addAttribute("person", personInterface.show(id));
        return "show";
    }
    /*@GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());

        return "new";
    }*/
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {//форма нового человека
        return "new";
    }
    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){//добавление нового человека
        if (bindingResult.hasErrors())
            return "new";
        personInterface.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")//возвращает страницу для редактирования по id
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("person", personInterface.show(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){
        if (bindingResult.hasErrors())
            return "edit";

        personInterface.update(id, person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personInterface.delete(id);
        return "redirect:/people";
    }
}
