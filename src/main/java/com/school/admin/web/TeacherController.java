package com.school.admin.web;

import com.school.admin.domain.Teacher;
import com.school.admin.domain.TeacherStatus;
import com.school.admin.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/teachers")
public class TeacherController {

    private final TeacherService service;

    public TeacherController(TeacherService service) {
        this.service = service;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("q", q == null ? "" : q);
        model.addAttribute("teachers", service.list(q));
        return "admin/teachers/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("teacher", new Teacher());
        model.addAttribute("statuses", TeacherStatus.values());
        model.addAttribute("mode", "create");
        return "admin/teachers/form";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("teacher") Teacher teacher,
                         BindingResult br,
                         Model model) {
        if (br.hasErrors()) {
            model.addAttribute("statuses", TeacherStatus.values());
            model.addAttribute("mode", "create");
            return "admin/teachers/form";
        }
        service.save(teacher);
        return "redirect:/admin/teachers";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("teacher", service.getOrThrow(id));
        model.addAttribute("statuses", TeacherStatus.values());
        model.addAttribute("mode", "edit");
        return "admin/teachers/form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute("teacher") Teacher form,
                       BindingResult br,
                       Model model) {
        if (br.hasErrors()) {
            model.addAttribute("statuses", TeacherStatus.values());
            model.addAttribute("mode", "edit");
            return "admin/teachers/form";
        }

        Teacher saved = service.getOrThrow(id);
        saved.setFullName(form.getFullName());
        saved.setPhone(form.getPhone());
        saved.setSubjects(form.getSubjects());
        saved.setStatus(form.getStatus());
        saved.setRateRub(form.getRateRub());
        saved.setNote(form.getNote());

        service.save(saved);
        return "redirect:/admin/teachers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/admin/teachers";
    }
}