package com.pedroleon.app.web.rest.dto;

import com.pedroleon.app.domain.EAlignment;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BiographyDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private String fullName;
    private String alterEgos;
    private String aliases;
    private String placeOfBirth;
    private String firstAppearance;
    private String publisher;
    private EAlignment alignment;
}
