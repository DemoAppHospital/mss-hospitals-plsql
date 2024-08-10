package pro.angelogomez.mss_hospitals_sps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.angelogomez.mss_hospitals_sps.entity.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
