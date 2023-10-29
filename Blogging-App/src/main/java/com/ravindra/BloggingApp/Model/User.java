package com.ravindra.BloggingApp.Model;

import com.ravindra.BloggingApp.Model.Enum.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotBlank
    private String userName;

    @NotBlank
    private String userHandle;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Min(13)
    private int userAge;

    @Pattern(regexp = "^.+@(?![Ii][Nn][Ss][Tt][Aa][Aa][Dd][Mm][Ii][Nn]\\.[Cc][Oo][Mm]$).+$")
    @Column(unique = true)
    private String userEmail;

    @Size(min = 8, message = "Password length should be greater than or equal to 8")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$",message = "Password should contain only numbers and letters should not contain any other special characters")
    private String userPassword;

}
