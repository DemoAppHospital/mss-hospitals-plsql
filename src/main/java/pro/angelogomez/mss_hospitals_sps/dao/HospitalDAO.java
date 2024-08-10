package pro.angelogomez.mss_hospitals_sps.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;
import pro.angelogomez.mss_hospitals_sps.dto.HospitalDTO;
import pro.angelogomez.mss_hospitals_sps.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class HospitalDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public HospitalDTO insertHospital(HospitalDTO hospitalDTO) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("hospital_pkg.insert_hospital");

        storedProcedure.registerStoredProcedureParameter("p_idHospital", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_idDistrito", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_nombreHospital", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_antiguedad", Integer.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_area", BigDecimal.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_idSede", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_idGerente", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_idCondicion", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);
        storedProcedure.registerStoredProcedureParameter("p_message", String.class, ParameterMode.OUT);

        storedProcedure.setParameter("p_idHospital", hospitalDTO.getIdHospital());
        storedProcedure.setParameter("p_idDistrito", hospitalDTO.getIdDistrito());
        storedProcedure.setParameter("p_nombreHospital", hospitalDTO.getNombreHospital());
        storedProcedure.setParameter("p_antiguedad", hospitalDTO.getAntiguedad());
        storedProcedure.setParameter("p_area", hospitalDTO.getArea());
        storedProcedure.setParameter("p_idSede", hospitalDTO.getIdSede());
        storedProcedure.setParameter("p_idGerente", hospitalDTO.getIdGerente());
        storedProcedure.setParameter("p_idCondicion", hospitalDTO.getIdCondicion());

        storedProcedure.execute();

        String message = (String) storedProcedure.getOutputParameterValue("p_message");

        if (message.contains("successfully")) {
            return hospitalDTO;
        } else {
            throw new RuntimeException(message);
        }
    }

    public HospitalDTO updateHospital(HospitalDTO hospitalDTO) throws ResourceNotFoundException {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("hospital_pkg.update_hospital");

        storedProcedure.registerStoredProcedureParameter("p_idHospital", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_idDistrito", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_nombreHospital", String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_antiguedad", Integer.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_area", BigDecimal.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_idSede", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_idGerente", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_idCondicion", Long.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);
        storedProcedure.registerStoredProcedureParameter("p_message", String.class, ParameterMode.OUT);

        storedProcedure.setParameter("p_idHospital", hospitalDTO.getIdHospital());
        storedProcedure.setParameter("p_idDistrito", hospitalDTO.getIdDistrito());
        storedProcedure.setParameter("p_nombreHospital", hospitalDTO.getNombreHospital());
        storedProcedure.setParameter("p_antiguedad", hospitalDTO.getAntiguedad());
        storedProcedure.setParameter("p_area", hospitalDTO.getArea());
        storedProcedure.setParameter("p_idSede", hospitalDTO.getIdSede());
        storedProcedure.setParameter("p_idGerente", hospitalDTO.getIdGerente());
        storedProcedure.setParameter("p_idCondicion", hospitalDTO.getIdCondicion());

        storedProcedure.execute();

        String message = (String) storedProcedure.getOutputParameterValue("p_message");

        List<Object[]> resultList = storedProcedure.getResultList();
        if (resultList.isEmpty()) {
            throw new ResourceNotFoundException("No se pudo actualizar el hospital: " + message);
        }

        Object[] result = resultList.get(0);
        HospitalDTO updatedHospitalDTO = new HospitalDTO();
        updatedHospitalDTO.setIdHospital(((Number) result[0]).longValue());
        updatedHospitalDTO.setIdDistrito(((Number) result[1]).longValue());
        updatedHospitalDTO.setNombreHospital((String) result[2]);
        updatedHospitalDTO.setAntiguedad(((Number) result[3]).intValue());
        updatedHospitalDTO.setArea((BigDecimal) result[4]);
        updatedHospitalDTO.setIdSede(((Number) result[5]).longValue());
        updatedHospitalDTO.setIdGerente(((Number) result[6]).longValue());
        updatedHospitalDTO.setIdCondicion(((Number) result[7]).longValue());
        updatedHospitalDTO.setFechaRegistro((Date) result[8]);

        return updatedHospitalDTO;
    }

    public void deleteHospital(Long idHospital) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("hospital_pkg.delete_hospital");

        query.registerStoredProcedureParameter("p_idHospital", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_message", String.class, ParameterMode.OUT);

        query.setParameter("p_idHospital", idHospital);

        query.execute();

        String message = (String) query.getOutputParameterValue("p_message");

        if (!message.contains("successfully")) {
            throw new RuntimeException(message);
        }
    }

    public HospitalDTO getHospitalById(Long idHospital) throws ResourceNotFoundException {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("hospital_pkg.list_hospital_by_id");

        query.registerStoredProcedureParameter("p_idHospital", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);
        query.registerStoredProcedureParameter("p_message", String.class, ParameterMode.OUT);

        query.setParameter("p_idHospital", idHospital);

        query.execute();

        List<Object[]> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            throw new ResourceNotFoundException("Hospital not found with id: " + idHospital);
        }

        Object[] result = resultList.get(0);
        HospitalDTO hospitalDTO = new HospitalDTO();
        hospitalDTO.setIdHospital(((Number) result[0]).longValue());
        hospitalDTO.setIdDistrito(((Number) result[1]).longValue());
        hospitalDTO.setNombreHospital((String) result[2]);
        hospitalDTO.setAntiguedad(((Number) result[3]).intValue());
        hospitalDTO.setArea((BigDecimal) result[4]);
        hospitalDTO.setIdSede(((Number) result[5]).longValue());
        hospitalDTO.setIdGerente(((Number) result[6]).longValue());
        hospitalDTO.setIdCondicion(((Number) result[7]).longValue());
        hospitalDTO.setFechaRegistro((Date) result[8]);

        return hospitalDTO;
    }

    public List<HospitalDTO> getAllHospitals() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("hospital_pkg.list_all_hospitals");

        query.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);
        query.registerStoredProcedureParameter("p_message", String.class, ParameterMode.OUT);

        query.execute();

        List<Object[]> resultList = query.getResultList();
        List<HospitalDTO> hospitals = new ArrayList<>();
        for (Object[] result : resultList) {
            HospitalDTO hospitalDTO = new HospitalDTO();
            hospitalDTO.setIdHospital(((Number) result[0]).longValue());
            hospitalDTO.setIdDistrito(((Number) result[1]).longValue());
            hospitalDTO.setNombreHospital((String) result[2]);
            hospitalDTO.setAntiguedad(((Number) result[3]).intValue());
            hospitalDTO.setArea((BigDecimal) result[4]);
            hospitalDTO.setIdSede(((Number) result[5]).longValue());
            hospitalDTO.setIdGerente(((Number) result[6]).longValue());
            hospitalDTO.setIdCondicion(((Number) result[7]).longValue());
            hospitalDTO.setFechaRegistro((Date) result[8]);
            hospitals.add(hospitalDTO);
        }

        return hospitals;
    }

    public List<HospitalDTO> getHospitalsBySede(Long idSede) throws ResourceNotFoundException {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("hospital_pkg.list_hospitals_by_sede");

        query.registerStoredProcedureParameter("p_idSede", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);
        query.registerStoredProcedureParameter("p_message", String.class, ParameterMode.OUT);

        query.setParameter("p_idSede", idSede);

        query.execute();

        List<Object[]> resultList = query.getResultList();

        if (resultList.isEmpty()) {
            throw new ResourceNotFoundException("Hospitals not found with sedeId: " + idSede);
        }

        List<HospitalDTO> hospitals = new ArrayList<>();
        for (Object[] result : resultList) {
            HospitalDTO hospitalDTO = new HospitalDTO();
            hospitalDTO.setIdHospital(((Number) result[0]).longValue());
            hospitalDTO.setIdDistrito(((Number) result[1]).longValue());
            hospitalDTO.setNombreHospital((String) result[2]);
            hospitalDTO.setAntiguedad(((Number) result[3]).intValue());
            hospitalDTO.setArea((BigDecimal) result[4]);
            hospitalDTO.setIdSede(((Number) result[5]).longValue());
            hospitalDTO.setIdGerente(((Number) result[6]).longValue());
            hospitalDTO.setIdCondicion(((Number) result[7]).longValue());
            hospitalDTO.setFechaRegistro((Date) result[8]);
            hospitals.add(hospitalDTO);
        }

        return hospitals;
    }
}
