package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.ConfigurationException;
import java.util.List;

@Service    //Service katmanı olduğunu söylüyorum.
public class StudentService {

    //StudentRepository Class'ını StudentService Class'ına enjekte ettim.
    //field injection
    @Autowired  //Di
    private StudentRepository studentRepository;


    //Burası benim Business logic tarafım.Kontrol yapmam gerekiyorsa kontrol yapıcam.
    //Senaryomu düşünüyorum;
    //Tabloya gidicem data varsa getir, yoksa zaten boş gelecek.O zaman bir kontrole gerek yok.
    //Kontrole gerek yoksa Repository tarafına gitmem lazım.
    public List<Student> getAll() {
        //studentRepository bir interface ve JpaRepository interace'ini extend ediyor.
        //studentRepository. dediğimde JpaRepository'nin bütün methodlarına ulaşabilirim.
        //İhtiyacım olan Student'ları listelemek bu yüzden findAll() methodunu kullanıyorum.
//        List<Student> students = studentRepository.findAll();  //Bana dönen ne List data type'ında Student'lar.
//        return students;
        return studentRepository.findAll(); //Kısaca bu şekilde yazıyorum.
        //Repository'ye gitmedim bile.Eğer bumethodlara ulaşamasaydım StudentRepository'ye gidip kendim method yazacaktım.
        //Ama buna gerek kalmadı çünkü springframework.data.jpa'dan extend edildi  StudentRepository'de.

        //findAll  ==> Method içine girdiğimde  "List<T> findAll();" Dönen değer List data type'ında Jenerik yapı.
        //Ben T'nin Student olacağını setledim.

        //Artık listeleme methodumu Postman'den test edebilirim.
    }


    //Bu methodun dönen değeri yok(void)
    public void createStudent(Student student) {
        //Burası Business Logic katı.Herhangi bir kontrol yapmam lazım mı bakıyorum.
        //Controller'da valid yaptı.Email geçerli mi diye kontrol etti.
        //Ama şimdi burada Email DB'de var mı diye kontrol etmem lazım.Email unique'dir.
        //Email DB'de varsa exception fırlatacak.
        //studentRepository'de "exists" diye bir method var ve bu method türetilebilir.
        //Biz Email için kullanıcaz.Gireceğim email DB'de kayıtlı olarak var mı diye kontrol yapmak istiyorum.
        //studentRepository.exists dedik ve methodu "existsByEmail" buna çevirdik.
        //Parametre olarak kullanıcının kayıt esnasında yazmış olduğu email bilgisini getirmem lazım.
        if(studentRepository.existsByEmail(student.getEmail())){
            //Eğer girilen email DB'de varsa exception fırlatmam lazım
            //Hata mesajını kendim türetmem lazım.
            //Kaydetmeye çalıştığınız Email zaten kullanılıyor diye bir mesaj göndermem lazım.
            //throw new diyerek yeni bir exception ürettim.
            //Java'nın exception'ları var ama ben kendim ConflictException adında yeni bir exception ürettim.
            //Bu Class exception package'ının içinde yer alıyor.(Detaylı açıklama class içinde)
            //İçine parametre olarak mesajımı yazdım.
            throw new ConflictException("Email is already exist!!");
        }
        //Else ise burası çalışacak.
        studentRepository.save(student);

        //Artık create methodumu Postman'den test edebilirim.

    }


    public Student getStudentById(Long id) {
        //Kontrol etmem gereken bir şey var mı?
        //Evet.Acaba bu id'li öğrenci var mı?
        //Varsa gönder yoksa exception fırlat

        // return studentRepository.findById(id);
        //findById() methodu Long data tipinde id alıyor.Dönen değer Optional<Student>
        //Optional yapılar Java'ya null pointer exception almamak için eklendi.
        //Optional--> Eğer student varsa gönderirim ama yoksa senin bunu handle edebilmen için
        //optional(içi boş) bir obje göndercem bunla sen handle et diyor.
        //Bu yüzden şu şekil yazcaz:
        return studentRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Student not found with id: " + id));
        //orElseThrow() -> Yoksa exception fırlat
        //()->new ResourceNotFoundException("Student not found with id: " + id) --> Oluşturduğum exception classını kullanarak hata mesajı gönderdim.

        //Artık methodumu Postman'den test edebilirim.

    }

    //Silmek istediğin id'li öğrenci var mı kontrol et.
    public void deleteStudent(Long id) {
        //Yukardaki öğrenci bulan methodu kullan.
        //Zaten yukardaki "getStudentById(Long id)" methodu istenilen id'li öğrenci var mı diye kontrol ediyor.
        //Varsa getiriyor yoksa hata mesajı fırlatıyor.
        //Varsa öğrenciyi alıyoruz ve delete uyguluyoruz.
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }

}
