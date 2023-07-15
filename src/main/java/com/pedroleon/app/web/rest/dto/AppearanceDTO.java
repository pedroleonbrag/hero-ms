package com.pedroleon.app.web.rest.dto;

import com.pedroleon.app.domain.EGender;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppearanceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private EGender gender;
    private String race;
    private String height;
    private String weight;
    private String eyeColor;
    private String hairColor;
}
