import java.util.Scanner;

//3-student ile ilgili metodlar
//4-DB e bağlanma, DB işlemleri için yeni bir repo class oluşturma
public class StudentService {

    Scanner inp=new Scanner(System.in);

    //repository classındaki metodları kullanabilmek için obje oluşturalım.
    private StudentRepository repo=new StudentRepository();

    //9-student için tablo oluşturma
    public void createStudentTable(){
        repo.createTable();
    }

    //11-öğrenciyi kaydetme
    public void saveStudent(){
        System.out.println("AD: ");//length(" asd   ")=7 trim("     ")->""
        String name=inp.nextLine().trim();//asd
        System.out.println("SOYAD: ");
        String lastname=inp.nextLine().trim();
        System.out.println("ŞEHİR: ");
        String city=inp.nextLine().trim();
        System.out.println("YAŞ: ");
        int age= inp.nextInt();
        inp.nextLine();
        Student newStudent=new Student(name,lastname,city,age);
        repo.save(newStudent);
    }

    //13-tüm öğrencileri listeleme
    public void getAllStudent() {
        repo.findAll();
    }

    public void deleteStudent(int id){
        repo.delete(id);
    }

    //17-id si verilen ogrenciyi bulma
    public Student getStudentByid(int id){
       Student student = repo.findById(id);
       return student;
    }



    //19-id si verilen ogrenciyi guncelleme

    public void updateStudent(int id){
        //bu id ile kayitli ogrenci mevcut mu
        Student foundStudent = getStudentByid(id);
        if (foundStudent==null){
            System.out.println("Student does not exists by id:"+id);

        }else {
            System.out.println("AD: ");//length(" asd   ")=7 trim("     ")->""
            String name=inp.nextLine().trim();//asd
            System.out.println("SOYAD: ");
            String lastname=inp.nextLine().trim();
            System.out.println("ŞEHİR: ");
            String city=inp.nextLine().trim();
            System.out.println("YAŞ: ");
            int age= inp.nextInt();
            inp.nextLine();
            //bulunan ogrencinin ozelliklerini guncelle
            foundStudent.setName(name);
            foundStudent.setLastname(lastname);
            foundStudent.setCity(city);
            foundStudent.setAge(age);
            //id si ayni kalacak

            repo.update(foundStudent);


        }
    }

    //21-id si verilen öğrenciyi görüntüleme
    public void displayStudent(int id) {
        Student student=getStudentByid(id);
        if (student==null){
            System.out.println("Student does not exist by id:"+id);
        }else{
            System.out.println(student);
        }

    }
}
