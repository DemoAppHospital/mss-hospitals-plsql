package pro.angelogomez.mss_hospitals_sps.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class HospitalDTO {

    private Long idHospital;

    @NotNull(message = "El ID del distrito no puede ser nulo")
    private Long idDistrito;

    @NotNull(message = "El nombre del hospital no puede ser nulo")
    private String nombreHospital;

    @NotNull(message = "La antigüedad no puede ser nula")
    private Integer antiguedad;

    @NotNull(message = "El área no puede ser nula")
    private BigDecimal area;

    @NotNull(message = "El ID de la sede no puede ser nulo")
    private Long idSede;

    @NotNull(message = "El ID del gerente no puede ser nulo")
    private Long idGerente;

    @NotNull(message = "El ID de la condición no puede ser nulo")
    private Long idCondicion;

    private Date fechaRegistro;
}
