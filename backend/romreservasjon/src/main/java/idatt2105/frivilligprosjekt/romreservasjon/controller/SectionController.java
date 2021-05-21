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
     * GetMapping for fetching all Sections from database
     *
     * @return a list of all Sections found in database
     */
    @GetMapping("/sections")
    public List<Section> getAllSections() {
        return sectionService.findAll();
    }

    /**
     * GetMapping returning a specific Section by id
     *
     * @param id: the id of the Section
     * @return the Section that was found
     */
    @GetMapping("/sections/{id}")
    public Section findById(@PathVariable int id){
        return sectionService.findById(id);
    }
}
