package br.com.estud_io_api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDTO {

    private String name;

    private String email;

    private LocalDate creationDate;

    private String goal;

    private Boolean emailVerified;

}
