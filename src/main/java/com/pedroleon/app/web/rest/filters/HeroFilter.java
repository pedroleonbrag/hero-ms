package com.pedroleon.app.web.rest.filters;

import com.pedroleon.app.domain.EAlignment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeroFilter {

    String name;
    String race;
    EAlignment alignment;
}
