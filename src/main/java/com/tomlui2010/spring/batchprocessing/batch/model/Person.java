package com.tomlui2010.spring.batchprocessing.batch.model;

import lombok.*;

/*Entity object representing a subscriber*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private String firstname;
    private String lastname;
    private String email;
    private String subscription_status;
    private String email_sent_status = "N";

}



