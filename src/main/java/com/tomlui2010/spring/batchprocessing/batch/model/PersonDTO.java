package com.tomlui2010.spring.batchprocessing.batch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PersonDTO {

    /*
    Data Transfer Object (DTO) to represent a subscriber information
     */
    private String firstname;
    private String lastname;
    private String email;
    private String subscription_status;
    private String email_sent_status;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
}



