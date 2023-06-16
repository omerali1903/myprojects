package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller//@RestController
@RequestMapping("/students")//http://localhost:8080/SpringMvc/students
//class level:tüm metodlar için geçerli
//method level:sadece o method için geçerli
public class StudentController {

    @Autowired
    private StudentService service;

    //controller requeste göre modelandview(data+view name) ya da String olarak sadece view name döner.
    @GetMapping("/hi")//http://localhost:8080/SpringMvc/students/hi
    public ModelAndView sayHi(){
        ModelAndView mav=new ModelAndView();
        mav.addObject("message","Hi;");
        mav.addObject("messagebody","I'm a Student Management System");
        mav.setViewName("hi");//hi.jsp
        return mav;
    }
    //view resolver mav içindeki model ı view name verilen dosyayı bulup içine bind eder.

    //1-Student Creation
    //http://localhost:8080/SpringMvc/students/new
    //kullanıcıdan bilgileri almak için form gösterelim
    @GetMapping("/new")
    public String sendStudentForm(@ModelAttribute("student")Student student){
        return "studentForm";
    }

    //@ModelAttribute view katmanı ile controller arasında data transferini sağlar.



    //student ı DB ye kaydedince tüm öğrencileri listeleyelim.
    //http://localhost:8080/SpringMvc/students/saveStudent+POST
//    @PostMapping("/saveStudent")
//    public String saveStudent(@ModelAttribute Student student){
//
//        //service de student ı kaydet
//        service.saveStudent(student);
//        return "redirect:/students";//http://localhost:8080/SpringMvc/students/ bu isteğe yönlendirelim
//    }

    @PostMapping("/saveStudent")
    public String saveStudent(@Valid @ModelAttribute Student student,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "studentForm";
        }
        //service de student ı kaydet
        service.saveStudent(student);
        return "redirect:/students";//http://localhost:8080/SpringMvc/students/ bu isteğe yönlendirelim
    }

    //http://localhost:8080/SpringMvc/students/+GET
    //2-list all student
    @GetMapping
    public ModelAndView listAllStudents(){
        List<Student> studentList=service.getAllStudent();
        ModelAndView mav=new ModelAndView();
        mav.addObject("students",studentList);
        mav.setViewName("students");
        return mav;
    }

    //3-update student
    //http://localhost:8080/SpringMvc/students/update?id=1
    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("id")Long id, Model model){
        Student foundStudent=service.getStudentById(id);
        model.addAttribute("student",foundStudent);
        return "studentForm";
    }

    //2.yöntem
//    @GetMapping("/update")
//    public ModelAndView showUpdateForm(@RequestParam("id")Long id){
//        Student foundStudent=service.getStudentById(id);
//        ModelAndView mav=new ModelAndView();
//        mav.addObject("student",foundStudent);
//        mav.setViewName("studentForm");
//        return mav;
//    }

    //4-delete Student
    //http://localhost:8080/SpringMvc/students/delete/4
    //silme işleminden sonra tüm kayıtları listeleyelim
    @GetMapping("/delete/{id}")
    public String deleteStudents(@PathVariable("id") Long id){
        service.deleteStudent(id);
        return "redirect:/students";
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleException(Exception ex){
        ModelAndView mav=new ModelAndView();
        mav.addObject("message",ex.getMessage());
        mav.setViewName("notFound");
        return mav;
    }

    //ExceptionHandler: belirtilen exception sınıfı için bir metod belirlememizi sağlar
    // bu metod yakalanan exception için özel bir işlem içerir(notFound u içinde mesaj ile gösterme)











}
