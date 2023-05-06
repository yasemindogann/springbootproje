package com.tpe.controller;

import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //Biz View kullanmıyoruz.RestAPI yapıyoruz.Status code(http) + response döneceği için böyle yaptık. Rest mimarisine uygun özelleştirilmiş bir controller demek istedim
//Rest mimarisinin bize sağladığı imkanları kullanabileceğimiz bir Controller Classı olduğunu işaret ettik.
@RequestMapping("/students") //Burası StudentController diyorum.Studentla ilgili bir endpoint gelirse StudentController'a dallan.Handler Mapping'in yardımcılığını yapıyor.
//  http://localhost:8080/students   => default bu oldu. Bana gelen endpoint böyle başlıyorsa bu Controller'ın içine gir diyorum.
//request = RestfullAPI li bir endpoint'tir //Gelen request'in karşılığı mutlaka buradaki methodlarda var.
public class StudentController {


    //StudentService Class'ını StudentController Class'ına enjekte etmem lazım.
    //Yani new'lemeden kullanmam lazım.
    //new'lemekten kurtardık.
    //field Injection           //Constructor Injection da kullanabilirdik.
    @Autowired  //Di    //Loosely coupling(düşük bağımlı) bir yapı oldu.
    private StudentService studentService;

    //Endpoint'leri oluşturacağız, Postman'den yazdığımız methodlarımız çalışıyor mu diye test edicez.

    //Bütün öğrencileri getirelim.
//    @GetMapping()    //endpoint + http methodları gidecek
//    public ResponseEntity<Student>

    //Githupa ekledim


}
