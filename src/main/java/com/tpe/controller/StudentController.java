package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    //public Student getAll()               ==> Birden fazla öğrenci gelecek, bu olmaz
    //public Student[] getAll()             ==> Kaç tane öğrenci gelecek bilmiyorum, esnek bir yapı lazım
    //public List<Student> getAll()         ==> StatusCode + Students gelecek List yeterli değil
    //public Map<List<Studet>,...> getAll() ==> Burayı Core Java ile(mesela Map) yapabilirim ama ...
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



    //Create new Student
    //Öğrenci create edicem bu yüzden POST kullanıyorum.
    //@PostMapping() -> İçini boş bıraktım ama  @GetMapping() ile karışmaz çünkü ikisinden de 1 tane var ve ikisi de farklı mehodlar.
    @PostMapping()   //http://localhost:8080/students + POST + JSON
    //public ResponseEntity<> createStudent()   ==> ResponseEntity dönecek
    //Kayıt yaptıktan sonra bize bir bilgi mesajı gelecek kayıt başarılı diye.
    //Ben Restful API yapıyorum bu yüzden status code da dönecek.
    //public ResponseEntity<String,String> createStudent()   ==> Bu yüzden String,String yazdım.
    //key-value olduğu için Map kullanabilirim.
    //Map yapmak zorunda değilim ama Map benim işimi kolaylaştırıyor.
    //1-message değeri var -> Öğrenci Kaydı Başarılı..
    //Mesajımı key-value şeklinde göndercem   --> "message" -> "Öğrenci Kaydı Başarıyla Gerçekleşmiştir"
    //2-StatusCode gelecek
    //Status Code da key-value şeklinde gidecek  --> "status" -> "true"
    //public ResponseEntity<Map<String,String>> createStudent()
    //Ön tarafa bu iki bilgi gidecek
    //Öğrencilerin bütün bilgileri gidecek kaydolacak... JSON
    //Restful Request'i JSON olarak alacak Response'a JSON olarak gidecek.
    //JSON'ı nasıl alırım
    //Java JSON anlamaz, JSON'ı Student Class'ına map'lemem lazım --> Jackson library yapıyor bunu
    //JSON dataları Student objesine map'lemek için --> @RequestBody
    //JSON dosyası Request'in body'sinde geliyor. @RequestBody yazarak bu JSON'ı benim Student POJO Class'ımla map'le diyorum.
    //Student POJO Class'ımda @NotNull() gibi kontrollerim var bunları kontrol etmem lazım.
    //Yani bana gelen JSON'ı Valide etmem lazım  --> @Valid
    //Boş mu, uzunluğu yeterli mi,geçerli değer girilmiş mi bunları kontrol etmem lazım Controller'da.Yoksa Repository'e kadar gider hatalıysa kod patlar.
    //Eğer @Valid yazmazsam Student'la map'lemeye çalışır, ordaki name'i map'leyemediği için null gelir,
    //Repository'ye gidince oradaki @Column annotation'u ile kontrol yapar, request Repository'den geri döner.
    //Ben öncelikli kontrolü Controller'da @Valid ile yapmam lazım. Yoksa boş yere Service ve Repository'ye gider orada işlem yapar geri döner.
    //Gelen JSON'ı da kontrol etmem lazım.Bunu @Valid ile sağladım.
    //Bütün bunlar performans kaybına, zaman kaybına yol açar.
    //Student POJO Classıma student adında bir değişken(instance) oluşturuyorum bunla map,le diyorum.
    //Bunu @RequestBody ile sağladım.
    //@RequestBody => Gelen Request'in içerisinde body kısmında bir JSON dosya gelecek bana.
    //Bu gelen JSON dosyayı bu Student POJO Class'ına map'le.
    //Map'lemeden kastım ne ?
    //--> Gelen JSON dosyada name = key, value = "Yasemin" --> Student POJO Class'ının name field'ına Yasemin yazıyorum.
    //Her bir field'ı JSON'dan alıyor benim POJO Class'ıma bu şekilde map'liyor.
    //Gelen JSON'daki key-value değerleri @Valid ile validasyona tabi tutmuş oluyorum.Kontrollerimi yapıyorum.
    public ResponseEntity<Map<String,String>> createStudent(@Valid @RequestBody Student student){
        //Controller'ın tek görevi request'i karşılayıp, ilgili Service'e göndermek.
        //@Valid işlemlerini burada yapmam lazım ama.(@NotNull(), @Size(), @Email() kontrolü gibi)
        //Şu anda bu method için service'e gönderecek kadar data'm elimde mi?
        //Bakıyorum öğrenci kaydedebilmek için Entity bilgilerim geldi mi? --> Evet (Student student) burda
        //O zaman başka bir şeye gerek yok.
        //studentService'e git oradaki create methodunu çağır.
        //Dönen değerim olmayacak, student save işlemi gerçekleşecek.
        //Tabi eğer validasyona takılmazsa kayıt gerçekleşecek.
        //Diğer kontroller Business Logic katında yani Service'de.
        studentService.createStudent(student);
        //Kullanıcıya bir mesaj göndermem lazım, kayıt başarılı diye.
        //Map yapıda key ve value'su String olan bir yapı oluşturuyorum.
        Map<String,String> map = new HashMap<>();
        map.put("message","Student is created successfuly");
        map.put("status","true");

        return new ResponseEntity<>(map, HttpStatus.CREATED); //status code = 201
        //Peki neden new'ledim.Neden "return ResponseEntity.ok(students);" böyle yapmadım.
        //Çünkü 1 defa kullancam.Başka yerde kullanmicam.Başka yerde kullanmicaksam, sadece 1-2 yerde kullanacaksam new'leyebilirim.
        //Çok sık kullanacaksam new'leme yapmam.Ama 1 defa kullanacaksam  neden objem boş yere beklesin oluşturulup.
        //Burayı sadece bu methoda has kullancam.

        //Öğrenci kaydedilince sadece mesaj ve status code gönderiyorum.

        //Sonrası StudentService'de.
    }



    //Get a student by ID
    //@PathVariable ve @
    //Siz bir data'yı @PathVariable ile almaya kalkarsanız şöyle bir yapıyla karşılaşacaksınız:
    //http://localhost:8080/students/1
    //@PathVariable ile 1 ne bilemem
    //@RequestParam ile yaparsanız:
    //http://localhost:8080/students/query?id=1
    //@RequestParam ile 1'in id olduğunu bilirim
    //Best practice için eğer tek data istiyorsam @PathVariable ile yaz
    //Ama birden fazla data varsa kod okunurluğunu arttırmak için @RequestParam
    //Normalde ikisini de kullanabilirim.

    //Get a student by ID RequestParam
    @GetMapping("/query")  //http://localhost:8080/students/query?id=1
    //id ile geleceği için 1 tane öğrenci geliyor --> ResponseEntity<Student>
    //Gelen id bilgisini Long data type'ında id isimli değişkene map'le
    public ResponseEntity<Student> getStudentById(@RequestParam("id") Long id){
        //Ben bu request'i service tarafına göndermek için bütün datalar elimde mi?
        //Evet.Bana id gerekiyordu, gereken id yukarda parametre olarak geldi.
        //O zaman service'e gidebilirim.
        //Bana 1 tane student objesi gelecek.
        Student student = studentService.getStudentById(id);

        //student'ı ve status code'u göndermem lazım
        return ResponseEntity.ok(student);
        //return new ResponseEntity<>(map, HttpStatus.OK);
        //Bu iki kod aynı işi yapıyor.Tek fark yukardaki daha kısa hali.

        //Sonrası StudentService'de.
    }


/*
    //Get a student by ID PathVariable
    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentByIdWithPath(@PathVariable("id") Long id){
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }
*/


    //Delete Student with id
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String >> deleteStudent(@PathVariable("id") Long id){
        studentService.deleteStudent(id);
        Map<String,String> map = new HashMap<>();
        map.put("message","Student is deleted successfuly");
        map.put("status","true");

        return new ResponseEntity<>(map, HttpStatus.OK);    // return ResponseEntity.ok(map);
    }


}
