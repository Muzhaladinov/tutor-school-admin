package com.school.admin.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String fullName;

    @Size(max = 30)
    @Column(length = 30)
    private String phone;

    @Size(max = 120)
    @Column(length = 120)
    private String subjects; // например: "Математика, Физика"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TeacherStatus status = TeacherStatus.ACTIVE;

    // ставка: например 1500 руб за занятие
    @Column(nullable = false)
    private Integer rateRub = 0;

    @Size(max = 500)
    @Column(length = 500)
    private String note;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Teacher() {}

    public Long getId() { return id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getSubjects() { return subjects; }
    public void setSubjects(String subjects) { this.subjects = subjects; }

    public TeacherStatus getStatus() { return status; }
    public void setStatus(TeacherStatus status) { this.status = status; }

    public Integer getRateRub() { return rateRub; }
    public void setRateRub(Integer rateRub) { this.rateRub = rateRub; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}