package lk.asityre.tyrerebuild.webapp.service;   // was com.tirerebuild.service

import lk.asityre.tyrerebuild.webapp.model.Appointment;                 // was com.tirerebuild.model.Appointment
import lk.asityre.tyrerebuild.webapp.repository.AppointmentRepository;  // was com.tirerebuild.repository.AppointmentRepository
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment book(Integer userId, LocalDate date, LocalTime time, String purpose) {
        Appointment a = new Appointment();
        a.setUserId(userId);
        a.setAppointmentDate(date);
        a.setAppointmentTime(time);
        a.setPurpose(purpose);
        a.setStatus("BOOKED");
        return appointmentRepository.save(a);
    }

    public List<Appointment> getAll() {
        return appointmentRepository.findAllByOrderByAppointmentDateAscAppointmentTimeAsc();
    }

    public List<Appointment> getForUser(Integer userId) {
        return appointmentRepository.findByUserIdOrderByAppointmentDateAscAppointmentTimeAsc(userId);
    }

    public void updateStatus(Integer appointmentId, String status) {
        Appointment a = appointmentRepository.findById(appointmentId).orElseThrow();
        a.setStatus(status);
        appointmentRepository.save(a);
    }
}
