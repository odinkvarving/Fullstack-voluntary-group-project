package idatt2105.frivilligprosjekt.romreservasjon.service;

import idatt2105.frivilligprosjekt.romreservasjon.model.Section;
import idatt2105.frivilligprosjekt.romreservasjon.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    /**
     * Method for finding all Sections from database
     *
     * @return a List of all Sections that was found
     */
    public List<Section> findAll() {
        return (List<Section>) sectionRepository.findAll();
    }

    /**
     * Method for finding a specific Section by id
     *
     * @param id the if of the Section to be found
     * @return the Section that was found
     */
    public Section findById(int id){
        return sectionRepository.findById(id).orElse(null);
    }
}
