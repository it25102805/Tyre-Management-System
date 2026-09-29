package lk.asityre.tyrerebuild.webapp.controller;

import lk.asityre.tyrerebuild.webapp.model.Appointment;
import lk.asityre.tyrerebuild.webapp.service.AppointmentService;
import lk.asityre.tyrerebuild.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AppointmentViewController {

    @Autowired
    private AppointmentService appointmentService;
    
    @Autowired
    private UserService userService; // To populate the dropdown of users

    @GetMapping("/appointments")
    public String viewAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        model.addAttribute("users", userService.getAllUsers()); // Populate user dropdown
        model.addAttribute("newAppointment", new Appointment());
        return "appointments"; // Expects an appointments.html template
    }

    @PostMapping("/appointments")
    public String saveAppointment(@ModelAttribute("newAppointment") Appointment appointment) {
        if (appointment.getAppointmentId() == null) {
            appointmentService.saveAppointment(appointment);
        } else {
            appointmentService.updateAppointment(appointment.getAppointmentId(), appointment);
        }
        return "redirect:/appointments";
    }

    @PostMapping("/appointments/delete/{id}")
    public String deleteAppointment(@PathVariable Integer id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments";
    }
}