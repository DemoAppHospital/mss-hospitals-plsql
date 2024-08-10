package pro.angelogomez.mss_hospitals_sps.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pro.angelogomez.mss_hospitals_sps.dto.HospitalDTO;
import pro.angelogomez.mss_hospitals_sps.entity.Hospital;
import pro.angelogomez.mss_hospitals_sps.exception.ResourceNotFoundException;
import pro.angelogomez.mss_hospitals_sps.service.HospitalService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/hospitals")
@Validated
@CrossOrigin(origins = "http://localhost:4200")
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;


    @GetMapping
    public ResponseEntity<List<HospitalDTO>> getAllHospitals() {
        List<HospitalDTO> hospitals = hospitalService.getAllHospitals();
        return ResponseEntity.ok(hospitals);
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<HospitalDTO> getHospitalById(@PathVariable Long id) throws ResourceNotFoundException {
        HospitalDTO hospitalDTO = hospitalService.getHospitalById(id);

        return ResponseEntity.ok(hospitalDTO);
    }

    @GetMapping("/by-sedeId/{idSede}")
    public ResponseEntity<List<HospitalDTO>> getHospitalsBySede(@PathVariable Long idSede) throws ResourceNotFoundException {
        List<HospitalDTO> hospitals = hospitalService.getHospitalsBySede(idSede);
        return ResponseEntity.ok(hospitals);
    }

    @PostMapping("/create")
    public ResponseEntity<HospitalDTO> createHospital(@Valid @RequestBody HospitalDTO hospitalDTO) {
        HospitalDTO createdHospital = hospitalService.insertHospital(hospitalDTO);
        return ResponseEntity.ok(createdHospital);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HospitalDTO> updateHospital(@PathVariable Long id, @Valid @RequestBody HospitalDTO hospitalDTO) throws ResourceNotFoundException {
        hospitalDTO.setIdHospital(id);
        HospitalDTO updatedHospital = hospitalService.updateHospital(hospitalDTO);
        return ResponseEntity.ok(updatedHospital);
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Boolean> deleteHospital(@PathVariable Long id) throws ResourceNotFoundException {
        Hospital hospital = hospitalService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hospital not found with id: " + id));
        hospitalService.deleteHospital(hospital.getIdHospital());
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
