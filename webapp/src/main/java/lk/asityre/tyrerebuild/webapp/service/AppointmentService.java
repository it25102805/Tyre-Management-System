package lk.asityre.tyrerebuild.webapp.service;

import lk.asityre.tyrerebuild.webapp.model.Appointment;
import lk.asityre.tyrerebuild.webapp.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

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