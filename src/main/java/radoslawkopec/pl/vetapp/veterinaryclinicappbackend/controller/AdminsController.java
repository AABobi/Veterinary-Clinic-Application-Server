package radoslawkopec.pl.vetapp.veterinaryclinicappbackend.controller;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model.DateOfTheVisit;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model.Doctors;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model.Passwords;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model.Users;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.repository.*;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.service.MailService;

@CrossOrigin(
        origins = {"http://localhost:4200"}
)
@RequestMapping({"/"})
@RestController
public class AdminsController {
    @Autowired
    public PasswordsRepository passwordsRepository;
    @Autowired
    public UsersRepository usersRepository;
    @Autowired
    private MailService notificationService;
    @Autowired
    public AdminsRepository adminsRepository;
    @Autowired
    public DoctorsRepository doctorsRepository;
    @Autowired
    public DateOfTheVisitRepository dateOfTheVisitRepository;

    public AdminsController() {
    }

    private static <T> Stream<T> convertListToStream(List<T> list) {
        return list.stream();
    }

    @GetMapping({"/appointments"})
    public DateOfTheVisit[] appointments() {
        Stream<DateOfTheVisit> streamDatesList = convertListToStream(this.dateOfTheVisitRepository.findAll());
        return (DateOfTheVisit[])streamDatesList.toArray((x$0) -> {
            return new DateOfTheVisit[x$0];
        });
    }

    @PostMapping({"/delete"})
    public void delete(){

    }
    @PostMapping({"/changeDate"})
    public void changeDate(@RequestBody DateOfTheVisit dateOfTheVisit) {
        System.out.println(dateOfTheVisit);
    }

    @PostMapping({"/deleteAnAppointment"})
    public DateOfTheVisit[] deleteAnAppointment(@RequestBody DateOfTheVisit dateOfTheVisit) {
        try {
            List<Doctors> doctor = this.doctorsRepository.findById(dateOfTheVisit.getDoctors().getId());
            List<DateOfTheVisit> listOfDates = (List)this.dateOfTheVisitRepository.findByDateof(dateOfTheVisit.getDateof()).stream().filter((n) -> {
                return n.getDoctors().equals(doctor.get(0));
            }).collect(Collectors.toList());
            List<Users> user = this.usersRepository.findById(((DateOfTheVisit)listOfDates.get(0)).getUsers().getId());
            ((DateOfTheVisit)listOfDates.get(0)).setUsers((Users)null);
            ((DateOfTheVisit)listOfDates.get(0)).setDoctors((Doctors)null);
            if (((Users)user.get(0)).getEmail() == "") {
                this.usersRepository.delete((Users)user.get(0));
            }

            this.dateOfTheVisitRepository.delete((DateOfTheVisit)listOfDates.get(0));
            return (DateOfTheVisit[])this.dateOfTheVisitRepository.findAll().stream().toArray((x$0) -> {
                return new DateOfTheVisit[x$0];
            });
        } catch (IndexOutOfBoundsException var5) {
            DateOfTheVisit[] d404 = new DateOfTheVisit[1];
            d404[0].setDateof("404");
            return d404;
        }
    }

    @GetMapping({"/usersForAppointmentsManagment"})
    public void usersForAppointmentsManagment(@RequestBody Users[] users) {
    }
}
