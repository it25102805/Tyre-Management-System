package lk.asityre.tyrerebuild.webapp.repository;

<<<<<<< HEAD
import lk.asityre.tyrerebuild.webapp.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByUserId(Integer userId);
=======
import lk.asityre.tyrerebuild.webapp.model.Appointment;   // was com.tirerebuild.model.Appointment
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> findByUserIdOrderByAppointmentDateAscAppointmentTimeAsc(Integer userId);

    List<Appointment> findAllByOrderByAppointmentDateAscAppointmentTimeAsc();
>>>>>>> origin/main
}