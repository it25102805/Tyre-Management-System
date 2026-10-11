<<<<<<< HEAD
package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.Appointment;
import lk.asityre.tyrerebuild.webapp.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

=======
package lk.asityre.tyrerebuild.webapp.service;   // was com.tirerebuild.service

import lk.asityre.tyrerebuild.webapp.model.Appointment;                 // was com.tirerebuild.model.Appointment
import lk.asityre.tyrerebuild.webapp.repository.AppointmentRepository;  // was com.tirerebuild.repository.AppointmentRepository
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
>>>>>>> origin/main
import java.util.List;

@Service
public class AppointmentService {

<<<<<<< HEAD
    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Integer id) {
        return appointmentRepository.findById(id).orElse(null);
    }
    
    public List<Appointment> getAppointmentsByUserId(Integer userId) {
        return appointmentRepository.findByUserId(userId);
    }

    public Appointment saveAppointment(Appointment appointment) {
        if (appointment.getStatus() == null) {
            appointment.setStatus("BOOKED");
        }
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Integer id) {
        appointmentRepository.deleteById(id);
    }

    public Appointment updateAppointment(Integer id, Appointment updatedDetails) {
        Appointment existingAppointment = appointmentRepository.findById(id).orElse(null);
        if (existingAppointment != null) {
            existingAppointment.setAppointmentDate(updatedDetails.getAppointmentDate());
            existingAppointment.setAppointmentTime(updatedDetails.getAppointmentTime());
            existingAppointment.setPurpose(updatedDetails.getPurpose());
            existingAppointment.setStatus(updatedDetails.getStatus());
            return appointmentRepository.save(existingAppointment);
        }
        return null;
    }
}
=======
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
>>>>>>> origin/main
