package com.swadhyay.kendra.entity;

import com.swadhyay.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kendra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Kendra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String city;

    private Double latitude;

    private Double longitude;

    @Column(length = 1000)
    private String description;

    private Boolean active;

    @OneToMany(
            mappedBy = "kendra",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<User> users = new ArrayList<>();

}