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
     * Method for finding a list of all Sections.
     *
     * @return list of Sections.
     */
    public List<Section> findAll() {
        return (List<Section>) sectionRepository.findAll();
    }

    /**
     * Method for finding a specific Section based on given id.
     *
     * @param id: id of requested Section.
     * @return a specific Section.
     */
    public Section findById(int id){
        return sectionRepository.findById(id).orElse(null);
    }
}
