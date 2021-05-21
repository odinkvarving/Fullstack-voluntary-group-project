package idatt2105.frivilligprosjekt.romreservasjon.controller;

import idatt2105.frivilligprosjekt.romreservasjon.model.Section;
import idatt2105.frivilligprosjekt.romreservasjon.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SectionController {

    @Autowired
    private SectionService sectionService;

    /**
     * GetMapping returning all Sections.
     *
     * @return a list of all Sections.
     */
    @GetMapping("/sections")
    public List<Section> getAllSections() {
        return sectionService.findAll();
    }

    /**
     * GetMapping returning a specific Sections based on id.
     *
     * @param id: id of requested Section.
     * @return a specific Section.
     */
    @GetMapping("/sections/{id}")
    public Section findById(@PathVariable int id){
        return sectionService.findById(id);
    }
}
