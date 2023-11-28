package com.placideh.usermgtapi.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users",
uniqueConstraints = {
        @UniqueConstraint(
                name = "email_unique",
                columnNames = "email"
        )
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer userId;

    @Column(name = "first_name")
    @NotBlank(message = "first name can not be blank")
    @NotEmpty(message = "first name must be entered")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "last name can not be blank")
    @NotEmpty(message = "last name must be entered")
    private String lastName;

    @NotBlank(message = "password name can not be blank")
    @NotEmpty(message = "password must be entered")
    @Size(min = 8,max = 200,message = "password must be 8 characters or more")
    @JsonIgnore
    private String password;

    @Column(name = "email")
    @Email(message = "enter a valid email")
    @NotBlank(message = "email can not be blank")
    @NotEmpty(message = "email must be entered")
    private String email;

    private Status status;


    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private String otp;
}
