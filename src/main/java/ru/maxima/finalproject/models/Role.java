package ru.maxima.finalproject.models;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column()
    private eRole name;

    public Role() {}

    public Role(eRole name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public eRole getName() {
        return name;
    }

    public void setName(eRole name) {
        this.name = name;
    }
}
