package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.CrewRepository;
import es.uma.taw.momdb.dto.CrewDTO;
import es.uma.taw.momdb.entity.Crew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrewService extends DTOService<CrewDTO, Crew>{

    @Autowired
    private CrewRepository crewRepository;

    public List<CrewDTO> listarActores () {
        return this.listarActores(null);
    }

    public List<CrewDTO> listarActores (String name) {
        List<Crew> crew;

        if ((name == null || name.isEmpty())) {
            crew = crewRepository.filterActors();
        } else { // solo título
            crew = this.crewRepository.filterByName(name);
        }
        return this.entity2DTO(crew);
    }


}
