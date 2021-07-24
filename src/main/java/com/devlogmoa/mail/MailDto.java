package com.devlogmoa.mail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {

    private String address;
    private String title;
    private String message;
}
