package com.pedroleon.app.web.rest.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeroDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String slug;
    private PowerstatsDTO powerstats;
    private AppearanceDTO appearance;
    private BiographyDTO biography;
    private WorkDTO work;
    private ImagesDTO images;
}
