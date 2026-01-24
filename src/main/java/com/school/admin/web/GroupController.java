package com.school.admin.web;

import com.school.admin.domain.GroupEntity;
import com.school.admin.domain.Student;
import com.school.admin.domain.Teacher;
import com.school.admin.dto.GroupForm;
import com.school.admin.service.GroupService;
import com.school.admin.service.StudentService;
import com.school.admin.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin/groups")
public class GroupController {

    private final GroupService groupService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    public GroupController(GroupService groupService, TeacherService teacherService, StudentService studentService) {
        this.groupService = groupService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("q", q == null ? "" : q);
        model.addAttribute("groups", groupService.list(q));
        return "admin/groups/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("form", new GroupForm());
        fillRefs(model);
        model.addAttribute("mode", "create");
        return "admin/groups/form";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("form") GroupForm form,
                         BindingResult br,
                         Model model) {
        if (br.hasErrors()) {
            fillRefs(model);
            model.addAttribute("mode", "create");
            return "admin/groups/form";
        }

        GroupEntity g = new GroupEntity();
        applyFormToEntity(form, g);
        groupService.save(g);
        return "redirect:/admin/groups";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        GroupEntity g = groupService.getOrThrow(id);

        GroupForm form = new GroupForm();
        form.setName(g.getName());
        form.setSubject(g.getSubject());
        form.setPriceRub(g.getPriceRub());
        form.setTeacherId(g.getTeacher() == null ? null : g.getTeacher().getId());
        form.setStudentIds(g.getStudents().stream().map(Student::getId).toList());

        model.addAttribute("form", form);
        model.addAttribute("groupId", id);
        fillRefs(model);
        model.addAttribute("mode", "edit");
        return "admin/groups/form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute("form") GroupForm form,
                       BindingResult br,
                       Model model) {
        if (br.hasErrors()) {
            model.addAttribute("groupId", id);
            fillRefs(model);
            model.addAttribute("mode", "edit");
            return "admin/groups/form";
        }

        GroupEntity g = groupService.getOrThrow(id);
        applyFormToEntity(form, g);
        groupService.save(g);
        return "redirect:/admin/groups";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        groupService.delete(id);
        return "redirect:/admin/groups";
    }

    private void fillRefs(Model model) {
        List<Teacher> teachers = teacherService.listTop200();
        List<Student> students = studentService.listTop200();
        model.addAttribute("teachers", teachers);
        model.addAttribute("students", students);
    }

    private void applyFormToEntity(GroupForm form, GroupEntity g) {
        g.setName(form.getName());
        g.setSubject(form.getSubject());
        g.setPriceRub(form.getPriceRub() == null ? 0 : Math.max(0, form.getPriceRub()));

        // teacher
        if (form.getTeacherId() == null) {
            g.setTeacher(null);
        } else {
            Teacher t = teacherService.getOrThrow(form.getTeacherId());
            g.setTeacher(t);
        }

        // students
        HashSet<Student> set = new HashSet<>();
        if (form.getStudentIds() != null) {
            for (Long sid : form.getStudentIds()) {
                if (sid == null) continue;
                set.add(studentService.getOrThrow(sid));
            }
        }
        g.setStudents(set);
    }
}