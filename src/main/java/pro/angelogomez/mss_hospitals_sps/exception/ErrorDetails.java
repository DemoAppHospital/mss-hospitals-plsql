package pro.angelogomez.mss_hospitals_sps.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Schema(description = "Modelo de detalles del error")
public class ErrorDetails {
    @Schema(description = "Fecha y hora del error", example = "2021-09-01T12:00:00.000+00:00")
    private Date timestamp;
    @Schema(description = "Mensaje de error", example = "Recurso no encontrado, Error de tipo de dato, Error de validación")
    private String message;
    @Schema(description = "Detalles del error", example = "uri=/api/v2/hospitals/**")
    private String details;
}
