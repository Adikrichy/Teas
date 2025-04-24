package org.aldousdev.teas.controller.tea;

import org.aldousdev.teas.models.tea.Sugar;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class SugarController {

    @GetMapping("/sugars")
    public List<Sugar> getSugar(){
        return Arrays.asList(Sugar.values());
    }
}

