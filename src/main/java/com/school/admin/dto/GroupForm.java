package com.school.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class GroupForm {

    @NotBlank
    @Size(max = 120)
    private String name;

    @Size(max = 60)
    private String subject;

    private Long teacherId; // может быть null, если не назначен

    @NotNull
    private Integer priceRub = 0;

    // выбранные ученики
    private List<Long> studentIds = new ArrayList<>();

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public Integer getPriceRub() { return priceRub; }
    public void setPriceRub(Integer priceRub) { this.priceRub = priceRub; }

    public List<Long> getStudentIds() { return studentIds; }
    public void setStudentIds(List<Long> studentIds) { this.studentIds = studentIds; }
}