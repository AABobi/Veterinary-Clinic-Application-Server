package radoslawkopec.pl.vetapp.veterinaryclinicappbackend.controller;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model.DateOfTheVisit;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model.Doctors;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model.Passwords;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.model.Users;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.repository.DateOfTheVisitRepository;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.repository.DoctorsRepository;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.repository.PasswordsRepository;
import radoslawkopec.pl.vetapp.veterinaryclinicappbackend.repository.UsersRepository;


@CrossOrigin(
        origins = {"http://localhost:4200"}
)
@RequestMapping({"/"})
@RestController
public class UsersController {
    @Autowired
    public UsersRepository usersRepository;
    @Autowired
    public PasswordsRepository passwordsRepository;
    @Autowired
    public DateOfTheVisitRepository dateOfTheVisitRepository;
    @Autowired
    public DoctorsRepository doctorsRepository;

    public UsersController() {
    }

    @GetMapping({"/hoursTest"})
    public void getDates(Passwords passwords) {
        System.out.println(passwords);
    }

    @GetMapping({"/findAllP"})
    public List<Passwords> getAllPasswords() {
        List<Passwords> passportList = new ArrayList();
        Iterable<Passwords> passport = this.passwordsRepository.findAll();
        Iterator var3 = passport.iterator();

        while(var3.hasNext()) {
            Passwords p = (Passwords)var3.next();
            passportList.add(p);
        }

        return passportList;
    }

    @GetMapping({"/findAllMain"})
    public List<Users> getAllUsers() {
        List<Users> passportList = new ArrayList();
        Iterable<Users> passport = this.usersRepository.findAll();
        Iterator var3 = passport.iterator();

        while(var3.hasNext()) {
            Users p = (Users)var3.next();
            passportList.add(p);
        }

        return passportList;
    }

    @PutMapping({"/addHours"})
    public void addHours(@RequestBody DateOfTheVisit dateOfTheVisit) {
        List<Users> usersList = new ArrayList(this.usersRepository.findByNickname(dateOfTheVisit.getUsers().getNickname()));
        if (usersList.size() != 0) {
            dateOfTheVisit.setUsers((Users)usersList.get(0));
            this.dateOfTheVisitRepository.save(dateOfTheVisit);
        } else {
            this.dateOfTheVisitRepository.save(dateOfTheVisit);
        }

    }

    @GetMapping({"/getDoctors"})
    public List<Doctors> getDoctors() {
        List<Doctors> listOfDoctors = new ArrayList(this.doctorsRepository.findAll());
        return listOfDoctors;
    }

    @PutMapping({"/addHoursWithoutAccount"})
    public void addHoursWithoutAccount(@RequestBody DateOfTheVisit dateOfTheVisit) {
        String randomNickForUnregisterUser;
        do {
            randomNickForUnregisterUser = RandomStringUtils.randomAlphanumeric(10);
        } while(this.usersRepository.findByNickname(randomNickForUnregisterUser).size() != 0);

        dateOfTheVisit.getUsers().setNickname(randomNickForUnregisterUser);
        this.usersRepository.save(dateOfTheVisit.getUsers());
        dateOfTheVisit.setUsers((Users)this.usersRepository.findByNickname(randomNickForUnregisterUser).get(0));
        dateOfTheVisit.setDoctors((Doctors)this.doctorsRepository.findById(dateOfTheVisit.getDoctors().getId()).get(0));
        this.dateOfTheVisitRepository.save(dateOfTheVisit);
    }

    @PutMapping({"/addHoursWithoutAccountTest"})
    public void addHoursWithoutAccount(@RequestBody Doctors doctors) {
        System.out.println(doctors);
    }

    @PostMapping({"/getVisitHoursWithFutureDate"})
    public String[] getVisitHoursWithFutureDate(@RequestBody DateOfTheVisit dateOfTheVisit) {
        DateOfTheVisit dateOfInstance = new DateOfTheVisit();
        Calendar calendar = Calendar.getInstance();
        String newStartDate = dateOfTheVisit.getDateof();
        int year = Integer.parseInt(newStartDate.substring(0, 4));
        int month = Integer.parseInt(newStartDate.substring(5, 7));
        int day = Integer.parseInt(newStartDate.substring(8, 10));
        String[] dates = new String[7];
        boolean nextMouth = false;
        calendar.set(year, month - 1, day);

        for(int i = 0; i < dates.length; ++i) {
            if (calendar.getActualMaximum(5) != 5 && !nextMouth) {
                calendar.set(calendar.get(1), calendar.get(2), calendar.get(5) + 1);
                dates = dateOfInstance.isWeekend(calendar, dates, i);
            } else if (calendar.getActualMaximum(5) == 5 && calendar.getActualMaximum(2) == 11) {
                calendar.set(calendar.get(1) + 1, calendar.get(2) + 1, 1);
                calendar.set(calendar.get(1), calendar.get(2), calendar.get(5));
                dates = dateOfInstance.isWeekend(calendar, dates, i);
            } else if (calendar.getActualMaximum(5) == 5 && calendar.getActualMaximum(2) != 11) {
                calendar.set(calendar.get(1), calendar.get(2) + 1, 1);
                calendar.set(calendar.get(1), calendar.get(2), calendar.get(5));
                dates = dateOfInstance.isWeekend(calendar, dates, i);
            }
        }

        String[] hoursTimes = new String[]{"8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30"};
        String[] rewrittenDates = dateOfInstance.rewriteArray(dates, hoursTimes);
        int reservedHours = 0;

        for(int i = 0; i < rewrittenDates.length; ++i) {
            if (this.dateOfTheVisitRepository.findByDateof(rewrittenDates[i]).size() != 0) {
                ++reservedHours;
            }
        }

        String[] rewrittenDatesV2 = new String[rewrittenDates.length - reservedHours];
        reservedHours = 0;

        for(int i = 0; i < rewrittenDates.length; ++i) {
            if (this.dateOfTheVisitRepository.findByDateof(rewrittenDates[i]).size() != 0) {
                ++reservedHours;
            } else {
                rewrittenDatesV2[i - reservedHours] = rewrittenDates[i];
            }
        }

        return rewrittenDatesV2;
    }

    @GetMapping({"/getHours"})
    public String[] getVisitHours() {
        DateOfTheVisit dateOfInstance = new DateOfTheVisit();
        Calendar calendar = Calendar.getInstance();
        new SimpleDateFormat("yyyy-MM-dd/HHmm");
        Date date = calendar.getTime();
        String[] dates = new String[7];
        boolean nextMouth = false;

        for(int i = 0; i < dates.length; ++i) {
            if (calendar.getActualMaximum(5) != 5 && !nextMouth) {
                calendar.set(calendar.get(1), calendar.get(2), calendar.get(5) + 1);
                dates = dateOfInstance.isWeekend(calendar, dates, i);
            } else if (calendar.getActualMaximum(5) == 5 && calendar.getActualMaximum(2) == 11) {
                calendar.set(calendar.get(1) + 1, calendar.get(2) + 1, 1);
                calendar.set(calendar.get(1), calendar.get(2), calendar.get(5));
                dates = dateOfInstance.isWeekend(calendar, dates, i);
            } else if (calendar.getActualMaximum(5) == 5 && calendar.getActualMaximum(2) != 11) {
                calendar.set(calendar.get(1), calendar.get(2) + 1, 1);
                calendar.set(calendar.get(1), calendar.get(2), calendar.get(5));
                dates = dateOfInstance.isWeekend(calendar, dates, i);
            }
        }

        String[] hoursTimes = new String[]{"8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30"};
        String[] rewrittenDates = dateOfInstance.rewriteArray(dates, hoursTimes);
        int reservedHours = 0;

        for(int i = 0; i < rewrittenDates.length; ++i) {
            if (this.dateOfTheVisitRepository.findByDateof(rewrittenDates[i]).size() != 0) {
                ++reservedHours;
            }
        }

        String[] rewrittenDatesV2 = new String[rewrittenDates.length - reservedHours];
        reservedHours = 0;
        List<Doctors> doctorsList = this.doctorsRepository.findAll();

        for(int i = 0; i < rewrittenDates.length; ++i) {
            if (this.dateOfTheVisitRepository.findByDateof(rewrittenDates[i]).size() != 0) {
                ++reservedHours;
            } else {
                rewrittenDatesV2[i - reservedHours] = rewrittenDates[i];
            }
        }

        return rewrittenDatesV2;
    }

    @PostMapping({"/findHoursForSelectedDoctor"})
    public String[] getVisitHoursV2(@RequestBody Doctors doctors) throws IOException {
        DateOfTheVisit dateOfInstance = new DateOfTheVisit();
        Calendar calendar = Calendar.getInstance();
        String[] dates = new String[7];
        boolean nextMouth = false;

        for(int i = 0; i < dates.length; ++i) {
            if (calendar.getActualMaximum(5) != 5 && !nextMouth) {
                calendar.set(calendar.get(1), calendar.get(2), calendar.get(5) + 1);
                dates = dateOfInstance.isWeekend(calendar, dates, i);
            } else if (calendar.getActualMaximum(5) == 5 && calendar.getActualMaximum(2) == 11) {
                calendar.set(calendar.get(1) + 1, calendar.get(2) + 1, 1);
                calendar.set(calendar.get(1), calendar.get(2), calendar.get(5));
                dates = dateOfInstance.isWeekend(calendar, dates, i);
            } else if (calendar.getActualMaximum(5) == 5 && calendar.getActualMaximum(2) != 11) {
                calendar.set(calendar.get(1), calendar.get(2) + 1, 1);
                calendar.set(calendar.get(1), calendar.get(2), calendar.get(5));
                dates = dateOfInstance.isWeekend(calendar, dates, i);
            }
        }

        String[] hoursTimes = new String[]{"8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30"};
        String[] rewrittenDates = dateOfInstance.rewriteArray(dates, hoursTimes);
        List<DateOfTheVisit> dateList = this.dateOfTheVisitRepository.findAll();
        String[] arrayWithAvailableHours = this.looksForDoctor(dateList, doctors, rewrittenDates);
        return arrayWithAvailableHours;
    }

    public String[] looksForDoctor(List<DateOfTheVisit> dateList, Doctors doctors, String[] rewrittenDates) throws IOException {
        List findDoc = this.doctorsRepository.findById(doctors.getId());

        String[] exceptionArray;
        try {
            List<String> arrayWithNotAvailableHours = (List)dateList.stream().filter((e) -> {
                return e.getDoctors().equals(findDoc.get(0));
            }).map(DateOfTheVisit::getDateof).collect(Collectors.toList());
            exceptionArray = (String[])Arrays.stream(rewrittenDates).filter((aDayWithEveryHour) -> {
                return !arrayWithNotAvailableHours.contains(aDayWithEveryHour);
            }).toArray((x$0) -> {
                return new String[x$0];
            });
            return exceptionArray;
        } catch (IndexOutOfBoundsException var7) {
            System.out.println("Probably program didn't found the doctor in DB  " + var7);
            exceptionArray = new String[]{"Index Exception"};
            return exceptionArray;
        }
    }

    @GetMapping({"/contest/{id}"})
    public void conTest(@PathVariable("id") int id) {
        List<Users> findUser = new ArrayList(this.usersRepository.findById(id));
        if (findUser.size() > 0) {
            ((Users)findUser.get(0)).setPermissions("Users");
            this.usersRepository.save((Users)findUser.get(0));
        }

    }

    @GetMapping({"/findUser/{userName}"})
    public Users findUser(@PathVariable("userName") String userName) {
        List<Users> findUser = new ArrayList(this.usersRepository.findByNickname(userName));
        if (findUser.size() == 1) {
            return (Users)findUser.get(0);
        } else {
            Users tmpUser = new Users("Not found", "Not found");
            return tmpUser;
        }
    }

    @PostMapping({"/findUserForAdmin"})
    public Users[] findUserForAdmin(@RequestBody Users users) {
        Stream<Users> usersStream = this.usersRepository.findByNameAndLastname(users.getName(), users.getLastname()).stream();
        Users[] arrayOfUsers = (Users[])usersStream.toArray((x$0) -> {
            return new Users[x$0];
        });
        return arrayOfUsers;
    }

    @GetMapping({"/findAllUsersForAdmin"})
    public Users[] findAllUsersForAdmin() {
        List<Users> usersList = new ArrayList(this.usersRepository.findAll());
        Stream<Users> usersStream = usersList.stream();
        Users[] usersArray = (Users[])usersStream.toArray((x$0) -> {
            return new Users[x$0];
        });
        return usersArray;
    }

    @DeleteMapping({"/deleteObj/{id}"})
    public void deleteUser(@PathVariable("id") int id) {
        System.out.println(id);
        this.passwordsRepository.deleteById(id);
    }
}
