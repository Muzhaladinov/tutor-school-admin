package com.school.admin.web;

import com.school.admin.domain.Student;
import com.school.admin.domain.StudentStatus;
import com.school.admin.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("q", q == null ? "" : q);
        model.addAttribute("students", service.list(q));
        return "admin/students/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("statuses", StudentStatus.values());
        model.addAttribute("mode", "create");
        return "admin/students/form";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("student") Student student,
                         BindingResult br,
                         Model model) {
        if (br.hasErrors()) {
            model.addAttribute("statuses", StudentStatus.values());
            model.addAttribute("mode", "create");
            return "admin/students/form";
        }
        service.save(student);
        return "redirect:/admin/students";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", service.getOrThrow(id));
        model.addAttribute("statuses", StudentStatus.values());
        model.addAttribute("mode", "edit");
        return "admin/students/form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute("student") Student form,
                       BindingResult br,
                       Model model) {
        if (br.hasErrors()) {
            model.addAttribute("statuses", StudentStatus.values());
            model.addAttribute("mode", "edit");
            return "admin/students/form";
        }

        Student saved = service.getOrThrow(id);
        saved.setFullName(form.getFullName());
        saved.setPhone(form.getPhone());
        saved.setSubject(form.getSubject());
        saved.setStatus(form.getStatus());
        saved.setNote(form.getNote());

        service.save(saved);
        return "redirect:/admin/students";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/admin/students";
    }
}