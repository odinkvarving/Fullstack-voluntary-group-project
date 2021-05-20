package idatt2105.frivilligprosjekt.romreservasjon.controller;

import idatt2105.frivilligprosjekt.romreservasjon.model.Section;
import idatt2105.frivilligprosjekt.romreservasjon.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SectionController {



    @Autowired
    private SectionService sectionService;

    @GetMapping("/sections/{id}")
    public Section findById(@PathVariable int id){
        return sectionService.findById(id);
    }
}
