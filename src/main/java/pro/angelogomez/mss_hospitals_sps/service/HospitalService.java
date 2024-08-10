package pro.angelogomez.mss_hospitals_sps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.angelogomez.mss_hospitals_sps.dao.HospitalDAO;
import pro.angelogomez.mss_hospitals_sps.dto.HospitalDTO;
import pro.angelogomez.mss_hospitals_sps.entity.Hospital;
import pro.angelogomez.mss_hospitals_sps.exception.ResourceNotFoundException;
import pro.angelogomez.mss_hospitals_sps.repository.HospitalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HospitalService {

    @Autowired
    private HospitalDAO hospitalDAO;

    @Autowired
    private HospitalRepository hospitalRepository;

    public HospitalDTO insertHospital(HospitalDTO hospitalDTO) {
        return hospitalDAO.insertHospital(hospitalDTO);
    }

    public HospitalDTO updateHospital(HospitalDTO hospitalDTO) throws ResourceNotFoundException {
        HospitalDTO updatedHospital = hospitalDAO.updateHospital(hospitalDTO);
        if (updatedHospital == null) {
            throw new ResourceNotFoundException("Hospital not found with id: " + hospitalDTO.getIdHospital());
        }
        return updatedHospital;
    }

    public void deleteHospital(Long idHospital) {
        hospitalDAO.deleteHospital(idHospital);
    }

    public Optional<Hospital> findById(Long id) {
        return hospitalRepository.findById(id);
    }

    public HospitalDTO getHospitalById(Long idHospital) throws ResourceNotFoundException {
        HospitalDTO hospitalDTO = hospitalDAO.getHospitalById(idHospital);
        if (hospitalDTO == null) {
            throw new ResourceNotFoundException("Hospital not found with id: " + idHospital);
        }
        return hospitalDTO;
    }

    public List<HospitalDTO> getAllHospitals() {
        return hospitalDAO.getAllHospitals();
    }

    public List<HospitalDTO> getHospitalsBySede(Long idSede) throws ResourceNotFoundException {
        List<HospitalDTO> hospitalDTO = hospitalDAO.getHospitalsBySede(idSede);
        if (hospitalDTO == null) {
            throw new ResourceNotFoundException("Hospitals not found with sedeId: " + idSede);
        }

        return hospitalDAO.getHospitalsBySede(idSede);
    }
}
