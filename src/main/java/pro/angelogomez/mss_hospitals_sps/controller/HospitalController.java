package pro.angelogomez.mss_hospitals_sps.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pro.angelogomez.mss_hospitals_sps.dto.HospitalDTO;
import pro.angelogomez.mss_hospitals_sps.entity.Hospital;
import pro.angelogomez.mss_hospitals_sps.exception.ErrorDetails;
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

    @Operation(summary = "Listar todos los hospitales", description = "Obtener todos los hospitales registrados en la base de datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, Hospitales encontrados", content = @Content(schema = @Schema(implementation = HospitalDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not Found, No se encontraron hospitales", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, Error del sistema", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @GetMapping
    public ResponseEntity<List<HospitalDTO>> getAllHospitals() {
        List<HospitalDTO> hospitals = hospitalService.getAllHospitals();
        return ResponseEntity.ok(hospitals);
    }

    @Operation(summary = "Obtener hospital por ID", description = "Obtener un hospital por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, Hospital encontrado", content = @Content(schema = @Schema(implementation = HospitalDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not Found, Hospital no encontrado", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, Error del sistema", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @GetMapping("/by-id/{id}")
    public ResponseEntity<HospitalDTO> getHospitalById(@PathVariable Long id) throws ResourceNotFoundException {
        HospitalDTO hospitalDTO = hospitalService.getHospitalById(id);

        return ResponseEntity.ok(hospitalDTO);
    }

    @Operation(summary = "Obtener hospitales por ID de sede", description = "Obtener todos los hospitales registrados en la base de datos por ID de sede.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, Hospitales encontrados", content = @Content(schema = @Schema(implementation = HospitalDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not Found, No se encontraron hospitales", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, Error del sistema", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @GetMapping("/by-sedeId/{idSede}")
    public ResponseEntity<List<HospitalDTO>> getHospitalsBySede(@PathVariable Long idSede) throws ResourceNotFoundException {
        List<HospitalDTO> hospitals = hospitalService.getHospitalsBySede(idSede);
        return ResponseEntity.ok(hospitals);
    }

    @Operation(summary = "Crear hospital", description = "Crear un nuevo hospital.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, Hospital creado", content = @Content(schema = @Schema(implementation = HospitalDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, Error en los datos de entrada", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, Error del sistema", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @PostMapping("/create")
    public ResponseEntity<HospitalDTO> createHospital(@Valid @RequestBody HospitalDTO hospitalDTO) {
        HospitalDTO createdHospital = hospitalService.insertHospital(hospitalDTO);
        return ResponseEntity.ok(createdHospital);
    }

    @Operation(summary = "Actualizar hospital", description = "Actualizar un hospital existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, Hospital actualizado", content = @Content(schema = @Schema(implementation = HospitalDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request, Error en los datos de entrada", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "Not Found, Hospital no encontrado", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, Error del sistema", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<HospitalDTO> updateHospital(@PathVariable Long id, @Valid @RequestBody HospitalDTO hospitalDTO) throws ResourceNotFoundException {
        hospitalDTO.setIdHospital(id);
        HospitalDTO updatedHospital = hospitalService.updateHospital(hospitalDTO);
        return ResponseEntity.ok(updatedHospital);
    }

    @Operation(summary = "Eliminar hospital", description = "Eliminar un hospital existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, MensajeJson => Deleted: true" ),
            @ApiResponse(responseCode = "404", description = "Not Found, Hospital no encontrado", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error, Error del sistema", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
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
