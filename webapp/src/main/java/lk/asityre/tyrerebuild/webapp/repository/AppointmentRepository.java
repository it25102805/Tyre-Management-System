package lk.asityre.tyrerebuild.webapp.repository;

import lk.asityre.tyrerebuild.webapp.model.Appointment;   // was com.tirerebuild.model.Appointment
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> findByUserIdOrderByAppointmentDateAscAppointmentTimeAsc(Integer userId);

    List<Appointment> findAllByOrderByAppointmentDateAscAppointmentTimeAsc();
}