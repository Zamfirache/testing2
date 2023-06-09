package com.example.unittesting2.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;




@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The book ISBN must be defined.")
    @Pattern(
            regexp = "^([0-9]{10}|[0-9]{13})$",
            message = "The ISBN format must be valid."
    )
    private String isbn;
    @NotBlank(
            message = "The book title must be defined."
    )
    private String title;

    @NotBlank(message = "The book author must be defined.")
    private String author;

    @NotNull(message = "The book price must be defined.")
    @Positive(
            message = "The book price must be greater than zero."
    )
    private Double price;


}
