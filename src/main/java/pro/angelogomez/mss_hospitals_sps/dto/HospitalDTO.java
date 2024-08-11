package pro.angelogomez.mss_hospitals_sps.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Schema(description = "Modelo de datos de Hospital")
public class HospitalDTO {

    @Schema(description = "ID del hospital", example = "999999")
    private Long idHospital;

    @Schema(description = "ID del distrito", example = "1")
    @NotNull(message = "El ID del distrito no puede ser nulo")
    private Long idDistrito;

    @Schema(description = "Nombre del hospital", example = "Hospital de la Solidaridad")
    @NotNull(message = "El nombre del hospital no puede ser nulo")
    private String nombreHospital;

    @Schema(description = "Antigüedad del hospital", example = "10")
    @NotNull(message = "La antigüedad no puede ser nula")
    private Integer antiguedad;

    @Schema(description = "Área del hospital", example = "1000.00")
    @NotNull(message = "El área no puede ser nula")
    private BigDecimal area;

    @Schema(description = "ID de la sede", example = "1")
    @NotNull(message = "El ID de la sede no puede ser nulo")
    private Long idSede;

    @Schema(description = "ID del gerente", example = "1")
    @NotNull(message = "El ID del gerente no puede ser nulo")
    private Long idGerente;

    @Schema(description = "ID de la condición", example = "1")
    @NotNull(message = "El ID de la condición no puede ser nulo")
    private Long idCondicion;

    @Schema(description = "Fecha de registro", example = "2021-09-01")
    private Date fechaRegistro;
}
