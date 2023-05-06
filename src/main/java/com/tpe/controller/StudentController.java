package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    //http://localhost:8080/students + GET  ==> Bu endpoint ile bana bir request gelirse ve bu da @GetMapping() ise bu methoda dallan demiş oluyorum.
    @GetMapping()   //http://localhost:8080/students + GET    //endpoint + http methodları gidecek
    //public Student getAll()           ==> Birden fazla öğrenci gelecek.
    //public Student[] getAll()         ==> Kaç tane öğrenci gelecek bilmiyorum, esnek bir yapı lazım.
    //public List<Student> getAll()     ==> StatusCode + Students gelecek List yeterli değil
    //public Map<Student, ...> getAll() ==> Burayı Core Java ile(mesela Map) yapabilirim ama ...
    //Spring Boot bizi düşünür.Madem sen Restfull API yapıyorsun Entity Class'ların dışında sen StatusCode da göndereceksin
    //o zaman bu işi kolay bir şekilde yap diye ResponseEntity isminde bir Class yapısı gönderiyorum bana bu yapı ile bu kodları gönderebilirsin diyor.
    public ResponseEntity<List<Student>> getAll(){ //ResponseEntity : Entity + StatusCode gönderiyor client'a.
        //Dönen değerim ResponseEntity olacak ve Student'ları göndercem.
        //Benim tek görevim bu gelen request'i service'e göndermek.
        //1-Yukarda enjekte ettiğim servise'i çağırıyorum.
        //2-StudentService'deki Listeleme methodunu çağır.
        //Listeleme olduğu için bana student'lar gelmeli.Bu yüzden List kullanıyorum.
        //return olarak bana dönen değer yukarda belirttiğim gibi ResponseEntity olacak.
        //Bu yüzden ResponseEntity'nin "ok()" methodunu kullanıyorum ve içine bana gelen "students"'ları veriyorum.
        //ok() methodu ne yapıyor? Bize yukardaki List yapıdaki(List<Student>) Student'ları gönderiyor + HTTP StatusCode'lardan  200 gönderiyor.
        //ok() karşılığı 200 dür.  List<Student> + HTTP.Status code = 200
        List<Student> students = studentService.getAll();
        return ResponseEntity.ok(students); //Entity(List<Student>) + HTTP.Status code
        //Öğrenci yoksa içi boş JSON gelir.  ==> []

        //Burdan sonrası StudentService'de
    }



}
